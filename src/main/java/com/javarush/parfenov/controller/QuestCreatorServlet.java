package com.javarush.parfenov.controller;

import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "QuestCreatorServlet", value = "/quest_creator")
public class QuestCreatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String parameter = request.getParameter("id");
//        if(parameter != null) {
//            request.getSession().setAttribute();
//        }
        JSP.forward(request, response, "quest_creator");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
