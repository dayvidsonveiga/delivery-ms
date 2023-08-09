package br.com.grupoacert.deliveryms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery API - Documentação")
                        .description("Desafio desenvolvido para o gerenciamento de pedidos de delivery.")
                        .version("v1.0.0")
                        .license(new License()
                                .name("Dayvidson Veiga")
                                .url("https://github.com/dayvidsonveiga"))
                        .contact(new Contact()
                                .name("Github da API")
                                .url("https://github.com/dayvidsonveiga/delivery-ms")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Clique aqui para ver o README")
                        .url("https://github.com/dayvidsonveiga/delivery-ms/blob/main/README.md"));
    }

}
