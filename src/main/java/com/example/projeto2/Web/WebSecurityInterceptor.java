package com.example.projeto2.Web;

import com.example.projeto2.Tables.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to handle web authentication
 */
@Component
public class WebSecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Permitir acesso a HTML, JS, CSS, imagens e chamadas REST de login
        if (
                uri.startsWith("/login.html") ||
                        uri.startsWith("/js/") ||
                        uri.startsWith("/css/") ||
                        uri.startsWith("/images/") ||
                        uri.startsWith("/api/login") ||
                        uri.startsWith("/api/register") || // se existir
                        uri.startsWith("/api/public") ||   // se tiveres rotas públicas
                        uri.equals("/") // homepage
        ) {
            return true;
        }

        // Permitir acesso a APIs apenas se autenticado
        if (uri.startsWith("/api/")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("cliente") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                return false;
            }
            return true;
        }

        // Permitir acesso direto a ficheiros estáticos ou HTML
        if (uri.endsWith(".html")) {
            return true;
        }

        // Redirecionar tudo o resto se não autenticado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cliente") == null) {
            response.sendRedirect("/login.html");
            return false;
        }

        return true;
    }
}