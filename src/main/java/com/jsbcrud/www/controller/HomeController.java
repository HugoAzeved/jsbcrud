package com.jsbcrud.www.controller;

<<<<<<< HEAD
import com.jsbcrud.www.config.Config;
=======

import com.jsbcrud.www.config.Config;
//import com.jsbcrud.www.repository.EmployeRepository;
>>>>>>> 56631cd9dbe124b6cd0c587ee68c6832be2322e7
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

<<<<<<< HEAD
import java.util.Map;
=======
//import java.util.Map;
>>>>>>> 56631cd9dbe124b6cd0c587ee68c6832be2322e7

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Config config;
<<<<<<< HEAD

    @ModelAttribute
    public Map<String, String> addGlobalAttributes() {
        return Map.of(
                "copyright", config.getCopyright(),
                "sitename", config.getHeaderName(),
                "logo", config.getLogo()
        );
=======
    //private final EmployeRepository employeRepository;

    @ModelAttribute
    public void addGlobalAttributs(Model model){
        model.addAttribute("copyright", config.getCopyright());
        model.addAttribute("sitename", config.getHeaderName());
        model.addAttribute("logo", config.getLogo());
>>>>>>> 56631cd9dbe124b6cd0c587ee68c6832be2322e7
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