package com.example.BootSample.controller;

import com.example.BootSample.form.SearchFrom;
import com.example.BootSample.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final Service service;

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
        model.addAttribute("searchList", service.findStudent(searchFrom));
        return "search";
    }
}
