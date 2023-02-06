package com.javarush.parfenov.util;

import com.javarush.parfenov.dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class JSP {
    public static void forward(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/%s.jsp".formatted(url));
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void forwardToPathIfLoggedIn(HttpServletRequest request, HttpServletResponse response, UserDto user, String path) {
        if(user != null) {
            forward(request, response, path);
        } else {
            forward(request, response, "login");
        }
    }
}
