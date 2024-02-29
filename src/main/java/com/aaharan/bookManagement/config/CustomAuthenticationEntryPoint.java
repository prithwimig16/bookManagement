package com.aaharan.bookManagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("status","false");
        data.put("message","Unauthorized");
        ObjectMapper mapper = new ObjectMapper();
       var response= mapper.writeValueAsString(data);
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(401);
        res.getWriter().write(response);
    }
}
