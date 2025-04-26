package com.jsbcrud.www.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração responsável por registrar interceptadores (interceptors)
 * no ciclo de vida das requisições da aplicação Spring MVC.
 *
 * <p>Implementa {@link WebMvcConfigurer} para customizar o comportamento do Spring MVC,
 * permitindo adicionar interceptadores como o {@link AuthInterceptor}, que será
 * executado antes de qualquer requisição chegar aos controllers.
 *
 * <p>O uso de {@link Configuration} indica que esta classe define configurações
 * da aplicação e será gerenciada pelo Spring. Já a anotação {@link RequiredArgsConstructor}
 * permite injeção automática do {@code AuthInterceptor} através do construtor.
 *
 * @author SeuNome
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /**
     * Interceptor responsável por verificar se o usuário está autenticado
     * antes de permitir acesso às rotas protegidas.
     */
    private final AuthInterceptor authInterceptor;

    /**
     * Registra o {@link AuthInterceptor} para que ele seja aplicado em todas as requisições,
     * exceto em rotas específicas que não exigem autenticação (como a página de login e arquivos estáticos).
     *
     * @param registry o registro de interceptadores fornecido pelo Spring
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(
                        "/login",       // Permite acesso sem login à página de login
                        "/css/**",      // Libera arquivos de estilo (CSS)
                        "/js/**",       // Libera scripts JavaScript
                        "/img/**",       // Libera imagens públicas
                        "/api/account/login"
                );
    }
}
