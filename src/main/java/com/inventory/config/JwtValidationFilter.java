package com.inventory.config;

import com.inventory.feign.AuthService;
import com.inventory.model.AuthResponse;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtValidationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public JwtValidationFilter(AuthService authService) {
        this.authService = authService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {



        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

//        String token = header.substring(7);

//        if (header == null) {
//            sendError(response, "Missing JWT token");
//            return;
//        }

        // 2. Call Auth Service to validate the token (Feign client)
        try {
            boolean isValid = authService.helloAdmin(header);
            if (!isValid) {
                sendError(response, "Invalid JWT token");
                return;
            }
        } catch (FeignException e) {
            sendError(response, e.getMessage());
            return;
        }

        String token = header.substring(7);
        DecodedJWT decodedJWT = JWT.decode(token); // Use a JWT library to parse claims

        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        // 4. Create an Authentication object with roles
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        // 5. Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Proceed if token is valid
        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }


}
