package org.pras.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final LmsUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            LmsUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }
        String token =
                authorizationHeader.substring(7);

        try {

            String username =
                    jwtService.extractUsername(token);

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (ExpiredJwtException e) {

            sendErrorResponse(
                    response,
                    "JWT token has expired"
            );
            return;

        } catch (MalformedJwtException e) {

            sendErrorResponse(
                    response,
                    "JWT token is malformed"
            );
            return;

        } catch (SignatureException e) {

            sendErrorResponse(
                    response,
                    "JWT signature is invalid"
            );
            return;

        } catch (JwtException e) {

            sendErrorResponse(
                    response,
                    "Invalid JWT token"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
    private void sendErrorResponse(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setContentType("application/json");

        response.getWriter().write("""
            {
                "status": 401,
                "message": "%s"
            }
            """.formatted(message));
    }
}