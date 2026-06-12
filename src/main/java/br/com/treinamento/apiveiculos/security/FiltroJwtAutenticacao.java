package br.com.treinamento.apiveiculos.security;


import br.com.treinamento.apiveiculos.context.UsuarioLogadoContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class FiltroJwtAutenticacao extends OncePerRequestFilter {


    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        // 1. Extrai o token do cabeçalho "Authorization"
        String tokenJWT = recuperarToken(request);


        if (tokenJWT != null) {
            // 2. Valida o token e recupera o login do usuário
            String login = tokenService.validarToken(tokenJWT);


            if (login != null) {
                // 3. Salva no nosso Contexto customizado para uso nos Services
                UsuarioLogadoContext.definirUsuario(login);


                // 4. Autentica e avisa ao Spring Security que este usuário está ativo e liberado
                var autenticacao = new UsernamePasswordAuthenticationToken(login, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }


        try {
            // Segue o fluxo normal da requisição para o Controller
            filterChain.doFilter(request, response);
        } finally {
            // Limpa o contexto ao finalizar a requisição (Evita vazamento de memória)
            UsuarioLogadoContext.limpar();
        }
    }


    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
