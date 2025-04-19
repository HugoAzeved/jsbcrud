package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Thing;
import com.jsbcrud.www.repository.ThingRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/thing")
public class ThingController {

    private final Config config;
    private final ThingRepository thingRepository;


    @GetMapping("/view/{id}")
    public String viewThing(@PathVariable Long id, Model model, HttpServletRequest request) {
        // Buscar o Thing pelo ID e garantir que o status é ON
        Optional<Thing> thingOpt = thingRepository.findById(id);
        if (thingOpt.isEmpty() || thingOpt.get().getStatus() != Thing.Status.ON) {
            return "redirect:/"; // Redireciona se não existir ou estiver desativado
        }

        Thing thing = thingOpt.get();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            model.addAttribute("loggedUser", session.getAttribute("user"));
        }

        model.addAttribute("thing", thing);
        model.addAttribute("owner", thing.getAccount()); // Passa o 'Account' relacionado
        model.addAttribute("categories", thing.getCategories());
        model.addAttribute("title", config.getShortName() + " - " + thing.getName());

        return "thing/view";
    }

    @GetMapping("/new")
    public String newThing(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova coisa");
        return "thing/new";
    }
}
