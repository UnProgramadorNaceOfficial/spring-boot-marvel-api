package com.api.marvel.controller.interceptor;

import com.api.marvel.exception.ApiErrorException;
import com.api.marvel.persistence.entity.HistoryEntity;
import com.api.marvel.persistence.repository.HistoryRepository;
import com.api.marvel.service.impl.UserDetailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class UserInteractionInterceptor implements HandlerInterceptor {

    @Autowired
    private HistoryRepository historyRepository;

    @Lazy
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/comic") || requestURI.startsWith("/character")) {
            HistoryEntity userLog = new HistoryEntity();
            userLog.setHttpMethod(request.getMethod());
            userLog.setUrl(request.getRequestURL().toString());

            String user = userDetailService.getUserLoggedIn();
            userLog.setUsername(user);
            userLog.setRemoteAddress(request.getRemoteAddr());
            userLog.setTimestamp(LocalDateTime.now());

            try {
                historyRepository.save(userLog);
                return true;
            } catch (Exception exception) {
                throw new ApiErrorException("No se logró guardar el registro de interacción correctamente");
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
