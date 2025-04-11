package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thing")
public class ThingController {

    private final Config config;

    @GetMapping("/view/{id}")
    public String viewThing(@PathVariable Long id, Model model) {
        model.addAttribute("title", config.getShortName() + " - Nome da coisa aqui");
        return "thing/view";
    }

    @GetMapping("/new")
    public String newThing(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova coisa");
        return "thing/new";
    }
}