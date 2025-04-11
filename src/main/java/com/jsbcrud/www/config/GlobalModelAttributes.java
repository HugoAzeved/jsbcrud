package com.jsbcrud.www.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {
    private final Config config;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("copyright", config.getCopyright());
        model.addAttribute("sitename", config.getHeaderName());
        model.addAttribute("logo", config.getLogo());
    }
}
