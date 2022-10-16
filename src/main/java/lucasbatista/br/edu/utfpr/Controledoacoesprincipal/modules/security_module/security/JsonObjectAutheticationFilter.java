package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.LoginCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonObjectAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try{
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                sb.append(line);

            LoginCredentials authRequest = objectMapper.readValue(sb.toString(), LoginCredentials.class);
            System.out.println(authRequest);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            );
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
