package br.com.treinamento.apiveiculos.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {


    // Injeta a chave secreta definida no nosso application.yml
    @Value("${api.security.token.secret}")
    private String secret;


    // Método para gerar o token no Login
    public String gerarToken(String login) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-veiculos") // Identificador do emissor do token
                    .withSubject(login)         // Dono do token (o usuário)
                    .withExpiresAt(gerarDataExpiracao()) // Tempo limite de validade
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }


    // Método para validar o token que vem do cabeçalho das requisições
    public String validarToken(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("api-veiculos")
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); // Retorna o login do usuário se o token estiver válido
        } catch (JWTVerificationException exception) {
            return null; // Retorna null se o token estiver violado, expirado ou inválido
        }
    }


    // Define que o token vai expirar em 2 horas (Horário de Brasília)
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
