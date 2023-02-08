package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.dto.UserDto;
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

    private UserService userService;
    private QuestService questService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = UserService.INSTANCE;
        questService = QuestService.INSTANCE;
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .build();
        Optional<UserDto> userDto = userService.login(createUserDto.getLogin(), createUserDto.getPassword());
        if (userDto.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto.get());
            session.setAttribute("quests", questService.getQuestDtoByUserLogin(userDto.get().getLogin()));
            response.sendRedirect("quests");
        } else {
            request.setAttribute("loginError", "login or password is incorrect");
            JSP.forward(request, response, "login");
        }
    }
}
