package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Thing;
import com.jsbcrud.www.repository.ThingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final Config config;
    private final ThingRepository thingRepository;


    @GetMapping("/")
    public String home(Model model) {

        List<Thing> things = thingRepository.findByStatusOrderByDateDesc(Thing.Status.ON);
        model.addAttribute("title", config.getShortName());
        model.addAttribute("things", things);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", config.getName() + " - Sobre");
        return "about";
    }
}
