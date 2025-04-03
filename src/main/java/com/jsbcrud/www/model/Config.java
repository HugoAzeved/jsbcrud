package com.jsbcrud.www.model;

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

    /**
     * Nome da aplicação.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final String name;

    /**
     * Nome do cabeçalho da aplicação.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final String headerName;

    /**
     * Nome curto da aplicação.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final String shortName;

    /**
     * Ano base da aplicação, geralmente o ano de início.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final int year;

    /**
     * Texto de copyright, com um marcador de ano que será substituído pelo ano atual.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final String copyright;

    /**
     * URL ou caminho do logo da aplicação.
     * Este valor é carregado a partir das propriedades de configuração.
     */
    private final String logo;

    /**
     * Método responsável por calcular e retornar o texto de copyright, substituindo o marcador de ano
     * pela data correta. Se o ano atual for maior que o ano base, ele substituirá o marcador "[YEAR]"
     * com a faixa de anos (exemplo: "2023 - 2025"), caso contrário, ele substituirá com o ano atual.
     *
     * @return O texto de copyright com o ano substituído.
     */
    public String getCopyright() {
        int currentYear = LocalDate.now().getYear();
        return currentYear > year
                ? copyright.replace("[YEAR]", year + " - " + currentYear) // Substitui o marcador por faixa de anos
                : copyright.replace("[YEAR]", String.valueOf(currentYear)); // Substitui o marcador com o ano atual
    }
}
