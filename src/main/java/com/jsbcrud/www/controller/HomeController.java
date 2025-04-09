package com.jsbcrud.www.controller;


import com.jsbcrud.www.config.Config;
//import com.jsbcrud.www.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

//import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Config config;
    //private final EmployeRepository employeRepository;

    @ModelAttribute
    public void addGlobalAttributs(Model model){
        model.addAttribute("copyright", config.getCopyright());
        model.addAttribute("sitename", config.getHeaderName());
        model.addAttribute("logo", config.getLogo());
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