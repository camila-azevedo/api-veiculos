package br.com.treinamento.apiveiculos.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";


        return new OpenAPI()
                // 1. Configura os metadados, títulos e contatos exibidos no cabeçalho da página
                .info(new Info()
                        .title("API de Gerenciamento de Veículos")
                        .description("API REST desenvolvida para o workshop técnico. " +
                                "Inclui operações de CRUD para Montadoras e Modelos com persistência no PostgreSQL.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Equipe de Arquitetura e Treinamento")
                                .email("treinamento@empresa.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))


                // 2. Adiciona o requisito de segurança global para os testes dos endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))


                // 3. Define o padrão Bearer JWT que o Swagger usará para enviar o token nos cabeçalhos HTTP
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Insira o token JWT gerado no endpoint de login para acessar as rotas protegidas.")));
    }
}
