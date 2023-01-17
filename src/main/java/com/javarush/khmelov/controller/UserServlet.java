package com.javarush.khmelov.controller;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.service.ImageService;
import com.javarush.khmelov.service.UserService;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Jsp;
import com.javarush.khmelov.util.Key;
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

@WebServlet(name = "UserServlet", value = Go.USER)
@MultipartConfig(fileSizeThreshold = 1 << 20)
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.USER_SERVICE;
    private final ImageService imageService = ImageService.IMAGE_SERVICE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameterId = request.getParameter(Key.ID);
        request.setAttribute(Key.ID, parameterId);
        if (Objects.nonNull(parameterId)) {
            long id = Long.parseLong(parameterId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.setAttribute(Key.USER, user);
            }
            Jsp.forward(request, response, Key.USER);
        }
        response.sendRedirect(Key.USERS);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = User.builder()
                .id(Long.valueOf(request.getParameter(Key.ID)))
                .login(request.getParameter(Key.LOGIN))
                .password(request.getParameter(Key.PASSWORD))
                .role(Role.valueOf(request.getParameter(Key.ROLE)))
                .build();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("create")) {
            userService.create(user);
        } else if (parameterMap.containsKey("update")) {
            userService.update(user);
        } else if (parameterMap.containsKey("delete")) {
            userService.delete(user);
        } else throw new IllegalStateException("unknown command");
        imageService.uploadImage(request, user.getImage());
        response.sendRedirect(Key.USERS);
    }
}
