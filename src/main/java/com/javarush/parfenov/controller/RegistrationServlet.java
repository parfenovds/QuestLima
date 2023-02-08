package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.entity.Role;
import com.javarush.parfenov.exception.ValidationException;
import com.javarush.parfenov.service.UserService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration", loadOnStartup = 0)
public class RegistrationServlet extends HttpServlet {

    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = UserService.INSTANCE;
        config.getServletContext().setAttribute("roles", Role.values());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "registration");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        CreateUserDto userDto = CreateUserDto.builder()
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .role(request.getParameter("role"))
                .build();
        try {
            userService.create(userDto);
            JSP.forward(request, response, "login");
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getValidationErrors());
            JSP.forward(request, response, "registration");
            throw new RuntimeException(e);
        }
    }
}
