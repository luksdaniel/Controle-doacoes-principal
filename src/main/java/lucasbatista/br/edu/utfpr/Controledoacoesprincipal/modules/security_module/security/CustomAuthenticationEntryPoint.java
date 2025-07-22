package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.entity.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);

        List<String> erro = new ArrayList<>();
        erro.add("Acesso negado");

        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(erro));
    }
}