package br.com.laboratorioce.termometro.config;


import br.com.laboratorioce.termometro.admin.AdminUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AdminUserRepository repository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/conteudos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/checkins").permitAll()
                        .requestMatchers("/api/auth/eu").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/admin/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginProcessingUrl("/api/auth/login")
                    .successHandler((req, res, auth) -> {
                        repository.findByEmail(auth.getName()).ifPresent(admin -> {
                            admin.registrarLogin();
                            repository.save(admin);
                        });
                        res.setStatus(HttpServletResponse.SC_OK);
                    })
                    .failureHandler((req, res, exception) -> {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.setCharacterEncoding("UTF-8");
                        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        res.getWriter().write("{\"mensagem\":\"E-mail ou senha inválidos\"}");
                    }))
                    .logout(out -> out
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)
                    ))
                    .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                    .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                    .addFilterBefore(new LimitLoginConfig(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(AdminUserRepository repository) {
        return email -> repository.findByEmail(email)
            .map(admin -> User.withUsername(admin.getEmail())
                .password(admin.getSenhaHash())
                .disabled(!admin.isAtivo())
                .authorities("ADMIN")
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));    
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
