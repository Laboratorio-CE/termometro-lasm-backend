package br.com.laboratorioce.termometro.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LimitLoginConfig extends OncePerRequestFilter {

    private static final int LIMITE = 5;
    private final int JANELA_EM_SEGUNDOS = 5 * 60; // 5 minutinho de castigo

    private Instant inicioDaJanela = Instant.now();
    private int tentativas = 0;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        boolean isLogin = "POST".equals(request.getMethod()) && "/api/auth/login".equals(request.getRequestURI());

        if (isLogin && excedeuOLimite()) {
            response.setStatus(429);
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter()
                    .write("{\"mensagem\":\"Muitas tentativas de login. Por favor, tente novamente mais tarde.\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private synchronized boolean excedeuOLimite() {
        Instant agora = Instant.now();
        
        if (inicioDaJanela.plusSeconds(JANELA_EM_SEGUNDOS).isBefore(agora)) {
            inicioDaJanela = agora;
            tentativas = 0;
        }
        tentativas++;
        return tentativas > LIMITE;
    }

}
