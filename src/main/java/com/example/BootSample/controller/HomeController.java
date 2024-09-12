package com.example.BootSample.controller;

import com.example.BootSample.GenderEnum;
import com.example.BootSample.PrefectureEnum;
import com.example.BootSample.config.UserDetails;
import com.example.BootSample.display.StudentsDisplay;
import com.example.BootSample.entity.StudentsEntity;
import com.example.BootSample.entity.UserInfoEntity;
import com.example.BootSample.form.RegisterForm;
import com.example.BootSample.form.SearchFrom;
import com.example.BootSample.form.UpdateForm;
import com.example.BootSample.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
//User情報が保持されているオブジェクトをSessionに格納します
@SessionAttributes(types = {UserInfoEntity.class})
@RequiredArgsConstructor
public class HomeController {
    private final Service service;
    private final Map<Integer, PrefectureEnum> prefectureMap = PrefectureEnum.getMap();
    private final Map<Integer, GenderEnum> genderMap = GenderEnum.getMap();

    /**
     * ハンドラメソッドにModelAttributeをつけると、戻り値に設定した値をModelに格納します
     *
     * @return 出身地を格納したMap
     */
    @ModelAttribute("prefectureMap")
    public Map<Integer, PrefectureEnum> getPrefectureMapToMode() {
        return this.prefectureMap;
    }

    /**
     * ハンドラメソッドにModelAttributeをつけると、戻り値に設定した値をModelに格納します
     *
     * @return 性別を格納したMap
     */
    @ModelAttribute("genderMap")
    public Map<Integer, GenderEnum> getGenderMapToMode() {
        return this.genderMap;
    }

    /**
     * ログイン画面を表示します
     *
     * @return ログイン画面
     */
    @GetMapping("/")
    public String showIndex() {
        return "login";
    }

    /**
     * ログイン画面を表示します
     *
     * @return ログイン画面
     */
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    /**
     * トップ画面を表示します
     * 戻り値をString型に、画面遷移するHTMLの拡張子抜きのファイル名を指定します
     *
     * @param model     Model
     * @param principal ログインユーザ情報を保持する
     * @return トップ画面
     */
    @GetMapping("/top")
    //Principalで認証済みユーザーの取得をします
    public String showTop(Model model, Principal principal) {
        //Authenticationにキャストして、ユーザーの詳細を取得します
        Authentication authentication = (Authentication) principal;
        //UserDetailsオブジェクトを取得します
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userInfo", userDetails);
        return "top";
    }

    /**
     * ハンドラメソッドの引数にフォームを指定すると、Springの機能で自動でModelに格納されます
     *
     * @param searchFrom 検索条件フォーム
     * @param model      Model
     * @return 検索画面
     */
    @GetMapping("/search")
    public String showSearch(SearchFrom searchFrom, Model model) {
        List<StudentsDisplay> searchList = new ArrayList<>();
        //表示用のDisneyクラスに詰め替え
        for (StudentsEntity entity : service.findStudent(searchFrom)) {
            searchList.add(convertToDisplay(entity));
        }

        model.addAttribute("searchList", searchList);
        return "search";
    }

    /**
     * 登録画面を表示します
     *
     * @param registerForm 登録フォーム
     * @return 登録画面
     */
    @GetMapping("/register")
    public String showRegisterForm(RegisterForm registerForm) {
        return "register";
    }

    /**
     * 登録完了へリダイレクトします
     *
     * @param registerForm       登録フォーム
     * @param result             バリデーションエラー
     * @param redirectAttributes リダイレクト属性
     * @param model              Model
     * @return 登録完了へリダイレクト
     */
    @PostMapping("/register")
    public String register(@Validated RegisterForm registerForm, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        //Entityクラスに詰め替え
        StudentsEntity studentsEntity = new StudentsEntity();
        studentsEntity.setName(registerForm.getName());
        studentsEntity.setPrefecture(registerForm.getPrefecture().getCode());
        studentsEntity.setGender(registerForm.getGender().getCode());

        try {
            this.service.insert(studentsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("hasError", true);
            return "register";
        }

        //フラッシュ属性に追加
        redirectAttributes.addFlashAttribute("display", convertToDisplay(studentsEntity));

        return "redirect:complete-register";
    }

    /**
     * 登録完了画面を表示します
     *
     * @return 登録完了画面
     */
    @GetMapping("/complete-register")
    public String showCompleteRegister() {
        return "complete-register";
    }

    /**
     * 詳細画面を表示します
     *
     * @param id    id
     * @param model Model
     * @return 詳細画面
     */
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model model) {
        StudentsEntity entity = this.service.findStudentById(id);
        if (entity == null) {
            model.addAttribute("hasNoStudent", true);
            return "detail";
        }

        //表示用のDisneyクラスに詰め替えて、Modelに格納
        model.addAttribute("display", convertToDisplay(entity));

        return "detail";
    }

    /**
     * 変更画面を表示します
     *
     * @param id         id
     * @param updateForm 変更フォーム
     * @param model      Model
     * @return 変更画面
     */
    @GetMapping("/{id}/update")
    public String showUpdate(@PathVariable int id, UpdateForm updateForm, Model model) {
        StudentsEntity entity = this.service.findStudentById(id);
        if (entity == null) {
            model.addAttribute("hasNoStudent", true);
            return "update";
        }

        //表示用のDisneyクラスに詰め替え
        updateForm.setId(entity.getId());
        updateForm.setName(entity.getName());
        updateForm.setPrefecture(this.prefectureMap.get(entity.getPrefecture()));
        updateForm.setGender(this.genderMap.get(entity.getGender()));

        return "update";
    }

    /**
     * 登録完了へリダイレクトします
     *
     * @param updateForm         変更フォーム
     * @param result             バリデーションエラー
     * @param redirectAttributes リダイレクト属性
     * @param model              Model
     * @return 変更完了へリダイレクト
     */
    @PostMapping("/{id}/update")
    public String update(@Validated UpdateForm updateForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "update";
        }
        //Entityクラスに詰め替え
        StudentsEntity entity = new StudentsEntity();
        entity.setId(updateForm.getId());
        entity.setName(updateForm.getName());
        entity.setPrefecture(updateForm.getPrefecture().getCode());
        entity.setGender(updateForm.getGender().getCode());

        //更新処理
        try {
            this.service.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("hasError", true);
            return "update";
        }

        //Displayクラスに詰め替えて、フラッシュ属性に追加
        redirectAttributes.addFlashAttribute("display", convertToDisplay(entity));
        return "redirect:complete-update";
    }

    /**
     * 変更完了画面を表示します
     *
     * @return 変更完了画面
     */
    @GetMapping("/{id}/complete-update")
    public String showCompleteUpdate() {
        return "complete-update";
    }

    /**
     * EntityクラスをDisneyクラスに詰め替えます
     *
     * @param entity Entityクラス
     * @return Displayクラス
     */
    private StudentsDisplay convertToDisplay(StudentsEntity entity) {
        StudentsDisplay display = new StudentsDisplay();
        display.setId(entity.getId());
        display.setName(entity.getName());
        display.setPrefecture(this.prefectureMap.get(entity.getPrefecture()).getDescription());
        display.setGender(this.genderMap.get(entity.getGender()).getDescription());
        return display;
    }

}
