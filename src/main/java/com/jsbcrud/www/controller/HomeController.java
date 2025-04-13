package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável por lidar com as páginas principais da aplicação, como
 * a página inicial e a página "Sobre".
 *
 * <p>Este controlador fornece dois endpoints:
 * <ul>
 *     <li>{@code /} - Página inicial (home)</li>
 *     <li>{@code /about} - Página "Sobre" com informações da aplicação</li>
 * </ul>
 *
 * <p>Utiliza {@link Config} para configurar dinamicamente o título das páginas.
 *
 * <p>Anotado com {@link Controller} para ser reconhecido pelo Spring como um componente de MVC
 * e com {@link RequiredArgsConstructor} para injeção automática do {@code Config} via construtor.
 *
 * @author SeuNome
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    /**
     * Instância de {@link Config} usada para acessar informações da aplicação,
     * como o nome exibido no título da página.
     */
    private final Config config;

    /**
     * Manipula requisições GET para a página inicial da aplicação.
     *
     * @param model objeto {@link Model} usado para passar dados dinâmicos para a view
     * @return o nome da view correspondente à página inicial ({@code home})
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", config.getName());
        return "home";
    }

    /**
     * Manipula requisições GET para a página "Sobre".
     * Exibe informações institucionais ou de contexto sobre a aplicação.
     *
     * @param model objeto {@link Model} usado para passar dados dinâmicos para a view
     * @return o nome da view correspondente à página "Sobre" ({@code about})
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", config.getName() + " - Sobre");
        return "about";
    }
}
