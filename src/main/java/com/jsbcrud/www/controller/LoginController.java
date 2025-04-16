package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import com.jsbcrud.www.util.HashUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Controlador responsável por gerenciar o fluxo de autenticação de usuários (login e logout).
 *
 * <p>Este controlador lida com a exibição da página de login, a validação de credenciais fornecidas
 * pelos usuários e o encerramento da sessão (logout), por meio da manipulação de cookies.
 *
 * <p>Utiliza {@link AccountRepository} para buscar os dados dos usuários e {@link Config}
 * para acessar as configurações da aplicação, como o tempo de vida dos cookies.
 *
 * <p>Controlador anotado com {@link Controller} e {@link RequiredArgsConstructor}
 * para injeção automática de dependências via construtor.
 *
 * @author SeuNome
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    /**
     * Instância de configuração da aplicação.
     * Usada para definir parâmetros como título da página e tempo de vida do cookie.
     */
    private final Config config;

    /**
     * Repositório de contas que permite consultar usuários pelo e-mail.
     */
    private final AccountRepository accountRepository;

    /**
     * Exibe a página de login para o usuário.
     *
     * @param model objeto {@link Model} usado para passar atributos à view
     * @return o nome da view correspondente à página de login ({@code login})
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", config.getShortName() + " - Faça login");
        model.addAttribute("disable_nav", true); // Oculta o menu de navegação na página de login
        return "login";
    }

    /**
     * Processa a submissão do formulário de login.
     * Verifica se o e-mail e a senha estão corretos e, se estiverem, cria um cookie de autenticação.
     *
     * @param email    o e-mail fornecido pelo usuário
     * @param password a senha fornecida pelo usuário (será criptografada)
     * @param response objeto da resposta HTTP, usado para adicionar cookies
     * @param model    modelo utilizado para passar dados à view em caso de erro
     * @return redireciona para a página principal se o login for bem-sucedido;
     *         caso contrário, retorna novamente para a view de login com mensagem de erro
     */
    @PostMapping("/login")
    public String doLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response,
            Model model
    ) {
        Optional<Account> userOpt = accountRepository.findByEmailAndStatus(email, Account.Status.ON);

        if (userOpt.isPresent()) {
            // Criptografa a senha informada para comparar com a armazenada
            String hashedPassword = HashUtil.sha256(password);

            if (userOpt.get().getPassword().equals(hashedPassword)) {
                // Cria um cookie persistente para identificar o usuário logado
                Cookie loginCookie = new Cookie("user", userOpt.get().getId().toString());
                loginCookie.setMaxAge(config.getCookieHoursLive() * 60 * 60); // Tempo de vida do cookie
                loginCookie.setHttpOnly(true); // Impede acesso ao cookie via JavaScript
                loginCookie.setPath("/");      // Disponível em toda a aplicação
                response.addCookie(loginCookie);

                return "redirect:/";
            }
        }

        // Se chegou aqui, o login falhou
        model.addAttribute("title", config.getShortName() + " - Faça login");
        model.addAttribute("error", "E-mail ou senha inválidos!");
        model.addAttribute("disable_nav", true);
        return "login";
    }

    /**
     * Realiza o logout do usuário ao remover o cookie de autenticação.
     *
     * @param response objeto da resposta HTTP usado para limpar o cookie
     * @return redireciona o usuário para a página de login
     */
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie loginCookie = new Cookie("user", "");
        loginCookie.setMaxAge(0); // Define o cookie como expirado imediatamente
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
        return "redirect:/login";
    }
}
