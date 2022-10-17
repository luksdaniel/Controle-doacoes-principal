package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.ApplicationExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailService userDetailService;
    private final String secret;

    ApplicationExceptionHandler applicationExceptionHandler = new ApplicationExceptionHandler();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailService userDetailService, String secret) {
        super(authenticationManager);
        this.userDetailService = userDetailService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        UsernamePasswordAuthenticationToken auth = getAuthentication(request);
        if (auth == null){
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(token == null || !token.startsWith(TOKEN_PREFIX)){
                return null;
            }

            String username = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if(username == null) return null;
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            return  new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        }catch (Exception e){
            ///applicationExceptionHandler.handleTokenExpiredException(e);
            return null;
        }

    }
}
