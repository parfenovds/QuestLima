package com.javarush.khmelov.lesson14;

import com.javarush.khmelov.lesson14.entity.Role;
import com.javarush.khmelov.lesson14.entity.User;
import com.javarush.khmelov.lesson14.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("serial")
@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("roles", Role.values());
        super.init(config);
    }

    UserService userService = UserService.USER_SERVICE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameterId = request.getParameter("id");
        if(Objects.nonNull(parameterId)){
            long id = Long.parseLong(parameterId);
            Optional<User> optionalUser = userService.get(id);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                request.setAttribute("user",user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user.jsp");
                dispatcher.forward(request,response);
            }
        }

        response.sendRedirect("users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ok");
        doGet(request,response);
    }
}
