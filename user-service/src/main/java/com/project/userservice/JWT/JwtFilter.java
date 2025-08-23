package com.project.userservice.JWT;

import com.project.userservice.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        // ⚠️ Small fix: "Bearer " MUST have a space after it
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // cut off "Bearer "
            email = jwtUtil.extractEmail(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                // ✅ Extract role from JWT claim
                String role = jwtUtil.extractRole(jwt);

                // ✅ Convert role to Spring authority
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                UsernamePasswordAuthenticationToken upat =
                        new UsernamePasswordAuthenticationToken(
                                email, null, List.of(authority));

                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        filterChain.doFilter(request, response);
    }
}
