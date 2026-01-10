package com.example.School_System.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SoftDeleteFilter extends OncePerRequestFilter {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter")
                .setParameter("isDeleted", Boolean.FALSE);
        try {
            filterChain.doFilter(request, response);
        } finally {
            session.disableFilter("deletedFilter");
        }
    }
}
