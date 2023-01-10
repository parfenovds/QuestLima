package com.javarush.khmelov.lesson14.controller;

import com.javarush.khmelov.lesson14.entity.Role;
import com.javarush.khmelov.lesson14.entity.User;
import com.javarush.khmelov.lesson14.service.ImageService;
import com.javarush.khmelov.lesson14.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("serial")
@WebServlet(name = "UserServlet", value = "/user")
@MultipartConfig(fileSizeThreshold = 1 << 20)
public class UserServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("roles", Role.values());
        super.init(config);
    }

    UserService userService = UserService.USER_SERVICE;
    ImageService imageService = ImageService.IMAGE_SERVICE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameterId = request.getParameter("id");
        request.setAttribute("id", parameterId);
        if (Objects.nonNull(parameterId)) {
            long id = Long.parseLong(parameterId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.setAttribute("user", user);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user.jsp");
            dispatcher.forward(request, response);
        }
        response.sendRedirect("users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = User.builder()
                .id(Long.valueOf(request.getParameter("id")))
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .role(Role.valueOf(request.getParameter("role")))
                .build();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("create")) {
            userService.create(user);
        } else if (parameterMap.containsKey("update")) {
            userService.update(user);
        } else if (parameterMap.containsKey("delete")) {
            userService.delete(user);
        } else throw new IllegalStateException("unknown command");
        imageService.uploadImage(request, user.getId());
        response.sendRedirect("users");
    }
}
