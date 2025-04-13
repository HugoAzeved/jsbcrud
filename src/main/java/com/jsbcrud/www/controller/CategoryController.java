package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador responsável por lidar com as operações relacionadas à entidade "Categoria".
 *
 * <p>Este controller está mapeado sob o caminho base {@code /cat} e fornece
 * endpoints para listagem e criação de categorias dentro da aplicação.
 *
 * <p>Utiliza {@link Config} para definir dinamicamente o título das páginas HTML.
 *
 * <p>Anotado com {@link Controller} para indicar que é um componente Spring MVC
 * e com {@link RequiredArgsConstructor} para permitir injeção automática de dependências
 * via construtor.
 *
 * @author SeuNome
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cat")
public class CategoryController {

    /**
     * Instância de {@link Config}, usada para acessar configurações da aplicação,
     * como o nome curto da aplicação, que será usado nos títulos das páginas.
     */
    private final Config config;

    /**
     * Manipula requisições GET para a listagem de categorias.
     *
     * <p>Adiciona ao modelo um título dinâmico usando o nome curto da aplicação
     * e retorna o nome da view responsável por exibir a lista de categorias.
     *
     * @param model o modelo Spring usado para passar dados para a view
     * @return o nome da view {@code cat/list}, onde as categorias serão exibidas
     */
    @GetMapping("/list")
    public String listCat(Model model) {
        model.addAttribute("title", config.getShortName() + " - Categorias");
        return "cat/list";
    }

    /**
     * Manipula requisições GET para exibir o formulário de criação de uma nova categoria.
     *
     * <p>Define o título da página usando o nome curto da aplicação e retorna
     * o nome da view correspondente ao formulário de cadastro de categoria.
     *
     * @param model o modelo Spring usado para passar dados para a view
     * @return o nome da view {@code cat/new}, onde será exibido o formulário
     */
    @GetMapping("/new")
    public String newCat(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova Categoria");
        return "cat/new";
    }
}
