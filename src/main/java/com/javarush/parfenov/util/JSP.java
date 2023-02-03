package com.javarush.parfenov.util;

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
}
