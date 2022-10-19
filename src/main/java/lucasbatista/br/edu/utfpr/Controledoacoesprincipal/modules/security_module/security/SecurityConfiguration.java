package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.security;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;
    private final UserDetailService userDetailService;
    private final String secret;

    public SecurityConfiguration(AuthSuccessHandler authSuccessHandler, UserDetailService userDetailService, @Value("${jwt.secret}") String secret) {
        this.authSuccessHandler = authSuccessHandler;
        this.userDetailService = userDetailService;
        this.secret = secret;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try{
                        auth
                                .antMatchers(HttpMethod.POST,"/login").permitAll()
                                .antMatchers(HttpMethod.POST, "/doador").permitAll()
                                .antMatchers(HttpMethod.POST, "/usuario").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFIlter())
                                .addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailService, secret))
                                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public JsonObjectAutheticationFilter authenticationFIlter() throws Exception{
        JsonObjectAutheticationFilter filter = new JsonObjectAutheticationFilter();
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }


}
