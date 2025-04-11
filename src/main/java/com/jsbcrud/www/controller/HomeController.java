package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Config config;

    @ModelAttribute
    public Map<String, String> addGlobalAttributes() {
        return Map.of(
                "copyright", config.getCopyright(),
                "sitename", config.getHeaderName(),
                "logo", config.getLogo()
        );
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", config.getName());
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", config.getName() + " - Sobre");
        return "about";
    }
}