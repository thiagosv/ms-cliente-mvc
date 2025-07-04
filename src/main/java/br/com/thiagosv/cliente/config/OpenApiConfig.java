package br.com.thiagosv.cliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Clientes")
                        .description("API para gerenciamento de clientes. Esta API foi criada como desafio do Bootcampo de arquitetura de software.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Thiago SV")
                                .email("contato@thiagosv.com.br")));
    }
}