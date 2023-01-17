package com.javarush.khmelov.controller;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.util.Go;
import com.javarush.khmelov.util.Jsp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ProfileServlet", value = Go.PROFILE)
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Jsp.forward(request, response, Go.PROFILE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Jsp.redirect(response, Go.USER + "?id=" + user.getId());
    }
}
