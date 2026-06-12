package br.com.treinamento.apiveiculos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Desabilita o CSRF (Cross-Site Request Forgery) pois nossa API é Stateless (via tokens)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Define que a aplicação não guardará estado de sessão no servidor (Padrão REST)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Regras de autorização de acessos
                .authorizeHttpRequests(authorize -> authorize
                        // Libera totalmente os endpoints do Swagger/OpenAPI para documentação visual
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // LIBERAÇÃO TEMPORÁRIA: Permite que qualquer requisição acesse nossos controllers do CRUD
                        .requestMatchers("/api/**").permitAll()

                        // Qualquer outra rota não mapeada acima exigirá autenticação por padrão
                        .anyRequest().authenticated()
                )
                .build();
    }
}

