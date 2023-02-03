package com.javarush.parfenov.filter;

import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = "/quests", filterName = "UnsafeFilter")
public class UnsafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(((HttpServletRequest) request).getSession().getAttribute("user") != null) {
            JSP.forward((HttpServletRequest)request, (HttpServletResponse) response, "quests");
        } else {
            JSP.forward((HttpServletRequest)request, (HttpServletResponse) response, "login");
        }
        chain.doFilter(request, response);
    }
}
