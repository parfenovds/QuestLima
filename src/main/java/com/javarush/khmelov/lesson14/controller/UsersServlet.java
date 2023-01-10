package com.javarush.khmelov.lesson14.controller;

import com.javarush.khmelov.lesson14.entity.User;
import com.javarush.khmelov.lesson14.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("serial")
@WebServlet(name = "UsersServlet", value = "/users")
public class UsersServlet extends HttpServlet {

    private final UserService userService = UserService.USER_SERVICE;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection<User> users = userService.getAll();
        request.setAttribute("users",users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/users.jsp");
        requestDispatcher.forward(request,response);
    }

    public void destroy() {
    }
}