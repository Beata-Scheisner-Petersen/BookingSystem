package org.example.bookingsystem.security.jwt.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtAuthEntryPoint = the one who says “you shall not pass”
 * Returns 401 Unauthorized
 * Always in JSON
 * Always consistent
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    /*
     * All JWT errors go here.
     * returns a nice JSON error.
     * GlobalExceptionHandler is not affected.
     * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); -> Sets HTTP status code 401 Unauthorized
     * response.setContentType("application/json"); -> Tells the client that it is a JSON file, otherwise some clients may interpret the response as
        text or HTML.
     * response.getWriter().write(body); -> Sends the JSON response to the client and ends the request.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String body = """
                {
                  "status": 401,
                  "error": "Unauthorized",
                  "message": "Invalid or expired token",
                  "path": "%s"
                }
                """.formatted(request.getRequestURI());

        response.getWriter().write(body);
    }
}
