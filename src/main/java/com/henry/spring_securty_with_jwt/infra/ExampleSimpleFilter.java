package com.henry.spring_securty_with_jwt.infra;

import com.henry.spring_securty_with_jwt.modules.user.JpaUserRepository;
import com.henry.spring_securty_with_jwt.modules.user.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class ExampleSimpleFilter extends OncePerRequestFilter {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String email = request.getHeader("email");

        if (email != null) {
            Optional<UserEntity> user = jpaUserRepository.findByEmail(email);

            if (!user.isPresent()) {
                response.setStatus(401);
                filterChain.doFilter(request, response);
                return;
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }


}
