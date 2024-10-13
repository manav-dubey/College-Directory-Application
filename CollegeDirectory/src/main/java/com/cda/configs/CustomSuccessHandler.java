package com.cda.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Redirect to the dashboard after successful login
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("------->>>>------->>>>"+roles);
        if(roles.contains("STUDENT")){
            response.sendRedirect("/students/home");
        }else if(roles.contains("FACULTY_MEMBER")){
            response.sendRedirect("/faculty/home");
        }else{
            response.sendRedirect("/admin/home");
        }
    }
}
