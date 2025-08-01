package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int expTime;

    private final String secret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UsuarioService usuarioService;

    public AuthSuccessHandler(@Value("${jwt.expiration}") int expTime, @Value("${jwt.secret}") String secret, UsuarioService usuarioService) {
        this.expTime = expTime;
        this.secret = secret;
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException{
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(usuarioService.findByUserName(principal.getUsername()).get().getUsername())
                .withClaim("role", usuarioService.findByUserName(principal.getUsername()).get().getAuthorities().toString())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli()+ expTime))
                .sign(Algorithm.HMAC256(secret));

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-type", "application/json");
        response.getWriter().write("{\"token\":\""+token+"\"}");
    }
}
