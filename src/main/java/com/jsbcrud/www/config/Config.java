package com.jsbcrud.www.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.LocalDate;

/**
 * Classe de configuração que carrega as propriedades definidas no arquivo de configuração da aplicação.
 * A anotação {@link ConfigurationProperties} permite a leitura de propriedades com prefixo "app".
 * A anotação {@link EnableConfigurationProperties} garante que a configuração seja habilitada e utilizada.
 */
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties(Config.class) // Habilita as propriedades de configuração para esta classe
@RequiredArgsConstructor
@Getter
public class Config {

    private final String name;
    private final String headerName;
    private final String shortName;
    private final int year;
    private final String copyright;
    private final String logo;
    private final int cookieHourslife = 48;

    public String getCopyright() {
        int currentYear = LocalDate.now().getYear();
        return currentYear > year
                ? copyright.replace("[YEAR]", year + " - " + currentYear) // Substitui o marcador por faixa de anos
                : copyright.replace("[YEAR]", String.valueOf(currentYear)); // Substitui o marcador com o ano atual
    }
}
