package com.chrisnor.koutye.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chrisnor.koutye.model.ActiveToken;
import com.chrisnor.koutye.repository.ActiveTokenRepository;
import com.chrisnor.koutye.service.JwtService;
import com.chrisnor.koutye.service.serviceimpl.JwtServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInactivityFilter extends OncePerRequestFilter{
	
	@Value("${expiration_token}")
	private int expire;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private ActiveTokenRepository activeTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String jti = jwtService.extractClaim(token, jwt -> jwt.getId()); // attention : bien implémenter cette méthode

            Optional<ActiveToken> opt = activeTokenRepository.findById(jti);

            if (opt.isEmpty()) {
            	System.out.println("non authorise");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            ActiveToken activeToken = opt.get();
            LocalDateTime lastActivity = activeToken.getLastActivity();
            if (lastActivity.plusMinutes(expire).isBefore(LocalDateTime.now())) {
            	System.out.println("5 mn arrivee");
                activeTokenRepository.deleteById(jti); // optionnel : nettoyer
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Mise à jour de l'activité
            activeToken.setLastActivity(LocalDateTime.now());
            activeTokenRepository.save(activeToken);
        }

        filterChain.doFilter(request, response);
    }

}
