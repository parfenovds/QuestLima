package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.entity.Role;
import com.javarush.parfenov.entity.User;
import com.javarush.parfenov.exception.ValidationException;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.service.UserService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private static final UserService userService = UserService.INSTANCE;
    private static final QuestService QUEST_SERVICE = QuestService.INSTANCE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("roles", Role.values());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .build();
        Optional<UserDto> userDto = userService.login(createUserDto.getLogin(), createUserDto.getPassword());
        if (userDto.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto.get());
            session.setAttribute("quests", QUEST_SERVICE.getQuestDtoByUserLogin(userDto.get().getLogin()));
            JSP.forward(request, response, "quests");
        } else {
            request.setAttribute("loginError", "login or password is incorrect");
            JSP.forward(request, response, "login");
        }


        try {
            userService.getByLogin(createUserDto.getLogin());
            JSP.forward(request, response, "login");
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getValidationErrors());
            JSP.forward(request, response, "registration");
            throw new RuntimeException(e);
        }
    }
}
