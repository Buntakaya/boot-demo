package com.example.BootSample.controller;

import com.example.BootSample.GenderEnum;
import com.example.BootSample.PrefectureEnum;
import com.example.BootSample.display.StudentsDisplay;
import com.example.BootSample.entity.StudentsEntity;
import com.example.BootSample.form.RegisterForm;
import com.example.BootSample.form.SearchFrom;
import com.example.BootSample.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
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
     * トップ画面を表示します
     * 戻り値をString型に、画面遷移するHTMLの拡張子抜きのファイル名を指定します
     *
     * @return トップ画面
     */
    @GetMapping("/")
    public String showTop() {
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
            StudentsDisplay display = new StudentsDisplay();
            display.setId(entity.getId());
            display.setName(entity.getName());
            display.setPrefecture(this.prefectureMap.get(entity.getPrefecture()).getDescription());
            display.setGender(this.genderMap.get(entity.getGender()).getDescription());
            searchList.add(display);
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

        //表示用のDisneyクラスに詰め替え
        StudentsDisplay display = new StudentsDisplay();
        display.setName(studentsEntity.getName());
        display.setPrefecture(this.prefectureMap.get(studentsEntity.getPrefecture()).getDescription());
        display.setGender(this.genderMap.get(studentsEntity.getGender()).getDescription());

        //フラッシュ属性に追加
        redirectAttributes.addFlashAttribute("display", display);

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
}
