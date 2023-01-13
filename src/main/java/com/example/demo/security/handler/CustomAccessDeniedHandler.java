package com.example.demo.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    private String errorPage;

    public CustomAccessDeniedHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 현재 사용자가 자원에 접근할 수 없다는 메시지를 뿌려주자.
        String deniedUrl = errorPage + "?exception="+accessDeniedException.getMessage();
        response.sendRedirect(deniedUrl);
    }

    public CustomAccessDeniedHandler() {
    }
}
