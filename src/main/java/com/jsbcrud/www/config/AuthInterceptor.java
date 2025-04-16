package com.jsbcrud.www.config;

import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;


/**
 * Interceptador responsável por aplicar uma camada de autenticação em todas as requisições.
 *
 * <p>Este interceptor verifica se o usuário está autenticado com base em um cookie chamado {@code user}.
 * Caso o cookie esteja presente e corresponda a um ID de usuário válido no banco de dados,
 * a requisição é permitida e o usuário é adicionado como atributo na requisição.
 * Caso contrário, o usuário é redirecionado para a página de login.
 *
 * <p>Este componente é aplicado antes que o controller correspondente à requisição seja executado.
 * Utiliza o {@link AccountRepository} para consultar a existência de um usuário autenticado.
 *
 * <p>Anotado com {@link Component} para ser gerenciado automaticamente pelo Spring,
 * e com {@link RequiredArgsConstructor} para injeção automática do repositório via construtor.
 *
 * @author SeuNome
 */

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {


    /**
     * Repositório de contas usado para validar se o ID do cookie pertence a um usuário existente.
     */
    private final AccountRepository accountRepository;

    /**
     * Método executado antes do controller, responsável por validar a autenticação do usuário.
     *
     * @param request  a requisição HTTP recebida
     * @param response a resposta HTTP a ser enviada
     * @param handler  o handler (normalmente o método do controller) que será chamado se o interceptor permitir
     * @return {@code true} se a requisição for autorizada a continuar; {@code false} se o usuário for redirecionado
     * @throws Exception em caso de erro inesperado durante o processo
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Permite acesso direto à página de login sem autenticação

        if (request.getRequestURI().equals("/login")) {
            return true;
        }


        String userId = getUserIdFromCookies(request);
        if (userId != null) {
            Optional<Account> user = accountRepository.findById(Long.parseLong(userId));
            if (user.isPresent()) {

                request.setAttribute("loggedUser", user.get());
                return true;
            }
        }


        response.sendRedirect("/login");
        return false;
    }



    private String getUserIdFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "user".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

}

