package com.project.instagram.config.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html><head></head><body><script>");
        stringBuilder.append("alert(\'로그인 실패\\n정보가 올바르지 않습니다.\');");
        stringBuilder.append("location.href=\'/sign-in\';");
        stringBuilder.append("</script></body></html>");

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(stringBuilder.toString());
    }
}