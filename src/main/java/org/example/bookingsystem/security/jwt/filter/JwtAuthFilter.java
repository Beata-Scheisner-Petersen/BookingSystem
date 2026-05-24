package org.example.bookingsystem.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.bookingsystem.security.jwt.service.JwtService;
import org.example.bookingsystem.security.jwt.service.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.*;
import java.io.IOException;

/**
 * JwtAuthFilter = the authentication controller
    * Sees a token → checks it
    * Sees no token → lets the person proceed as anonymous
    * Sees a broken token → sends the person to the bouncer (EntryPoint)
 * @Component -> Spring automatically creates the filter as a bean.
 * OncePerRequestFilter -> Spring guarantees that the filter runs exactly once per request.
    * It is important, because some filters can run multiple times — but JWT filters should only run once.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /*
     * The client calls a protected endpoint, example
        GET /api/customers/me
        Authorization: Bearer <JWT_HERE>
     * The request enters Spring Security's filter chain.
     * Your JwtAuthFilter runs before the controller.
     * The filter tries to authenticate the user based on the token.
     * If error goes to JwtAuthEntryPoint else goes to SecurityConfig.
     * If none or incorrect header → pass through as anonymous. It is SecurityConfigs job to catch unauthorized login.
     * All errors trigger AuthenticationException and sends to JwtAuthEntryPoint.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // None or incorrect header → pass through as anonymous. It is SecurityConfigs job to catch unauthorized login.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        try {
            token = authHeader.substring(7);
        } catch (StringIndexOutOfBoundsException eOutOfBound) {
            // AuthenticationException → goes to JwtAuthEntryPoint
            throw new AuthenticationException("Invalid Authorization header format", eOutOfBound) {};
        }

        try {
            String email = jwtService.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

            //All JWT errors → trigger AuthenticationEntryPoint
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException eIllegalArgument) {
            throw new org.springframework.security.core.AuthenticationException("Invalid JWT", eIllegalArgument) {};
        }
    }
}
