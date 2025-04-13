package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador responsável por lidar com as operações relacionadas à entidade {@code Thing}.
 *
 * <p>Este controller está mapeado sob o caminho base {@code /thing} e fornece endpoints
 * para visualização e criação de novos registros da entidade {@code Thing}.
 *
 * <p>Utiliza a configuração definida em {@link Config} para definir, por exemplo,
 * títulos dinâmicos das páginas com base no nome curto da aplicação.
 *
 * <p>Anotado com {@link Controller} para indicar que é um componente Spring MVC e
 * com {@link RequiredArgsConstructor} para permitir injeção automática de dependências
 * através de construtor.
 *
 * @author SeuNome
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/thing")
public class ThingController {

    /**
     * Instância de {@link Config} usada para obter configurações compartilhadas,
     * como o nome curto da aplicação.
     */
    private final Config config;

    /**
     * Manipula requisições GET para visualizar os detalhes de uma entidade {@code Thing}
     * com base no seu ID.
     *
     * @param id    o identificador único da entidade {@code Thing} a ser visualizada
     * @param model o modelo Spring MVC usado para adicionar atributos à view
     * @return o nome da view a ser renderizada (neste caso, {@code thing/view})
     */
    @GetMapping("/view/{id}")
    public String viewThing(@PathVariable Long id, Model model) {
        model.addAttribute("title", config.getShortName() + " - Nome da coisa aqui");
        return "thing/view";
    }

    /**
     * Manipula requisições GET para exibir o formulário de criação de uma nova entidade {@code Thing}.
     *
     * @param model o modelo Spring MVC usado para adicionar atributos à view
     * @return o nome da view a ser renderizada (neste caso, {@code thing/new})
     */
    @GetMapping("/new")
    public String newThing(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova coisa");
        return "thing/new";
    }
}
