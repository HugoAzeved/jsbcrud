package com.jsbcrud.www.api.controller.account;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import com.jsbcrud.www.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class Login {

    private final AccountRepository accountRepository;
    private final Config config;

    @PostMapping("/login")
    public Map<String, String> doLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        Map<String, String> result = new HashMap<>();
        Optional<Account> userOpt = accountRepository.findByEmailAndStatus(email, Account.Status.ON);

        if (userOpt.isPresent()) {
            String hashedPassword = HashUtil.sha256(password);
            if (userOpt.get().getPassword().equals(hashedPassword)) {
                // Criar cookie
                Cookie loginCookie = new Cookie("user", userOpt.get().getId().toString());
                loginCookie.setMaxAge(config.getCookieHoursLive() * 60 * 60);
                loginCookie.setHttpOnly(true);
                loginCookie.setPath("/");
                response.addCookie(loginCookie);

                result.put("code", "200");
                result.put("status", "success");
                result.put("message", "Login efetuado com sucesso.");
                return result;
            }
        }

        result.put("code", "401");
        result.put("status", "error");
        result.put("message", "E-mail ou senha inválidos.");
        return result;
    }
}
