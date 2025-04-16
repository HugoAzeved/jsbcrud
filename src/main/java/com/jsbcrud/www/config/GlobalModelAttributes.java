package com.jsbcrud.www.config;

import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

/**
 * Classe responsável por adicionar atributos globais ao modelo de todas as views.
 *
 * <p>Utiliza {@link ControllerAdvice} para aplicar os atributos em todos os controllers
 * automaticamente. Essa abordagem é útil para definir informações que devem estar presentes
 * em todas as páginas, como nome do site, logotipo e direitos autorais.</p>
 *
 * <p>Os dados são fornecidos pela classe {@link Config}.</p>
 *
 * @author [Seu Nome]
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    /**
     * Instância de {@link Config} injetada automaticamente via construtor.
     * Fornece os dados configuráveis usados nas views.
     */
    private final Config config;
    private final AccountRepository accountRepository;

    /**
     * Adiciona atributos globais ao {@link Model} acessível em todas as views.
     *
     * <ul>
     *     <li><b>copyright:</b> Texto de direitos autorais configurado</li>
     *     <li><b>sitename:</b> Nome do cabeçalho ou título do site</li>
     *     <li><b>logo:</b> Caminho ou URL para o logotipo</li>
     * </ul>
     *
     * @param model o modelo usado nas views para renderizar os atributos
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("copyright", config.getCopyright());
        model.addAttribute("sitename", config.getHeaderName());
        model.addAttribute("logo", config.getLogo());

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("account".equals(cookie.getName())) {
                    Integer userId = Integer.parseInt(cookie.getValue());
                    Optional<Account> userOpt = accountRepository.findById(userId);
                    if (userOpt.isPresent() && userOpt.get().getStatus() == Account.Status.ON) {
                        System.out.println("----------\n\n\n" + userOpt.get().getName() + "\n\n\n----------");
                        model.addAttribute("loggedUser", userOpt.get());
                    } else {
                        Cookie removeCookie = new Cookie("account", "");
                        removeCookie.setMaxAge(0);
                        removeCookie.setPath("/");
                        removeCookie.setHttpOnly(true);
                        response.addCookie(removeCookie);
                    }
                    break;
                }
            }
        }

    }
}
