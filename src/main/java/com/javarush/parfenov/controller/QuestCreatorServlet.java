package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

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
        try (BufferedReader reader = request.getReader()) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(sb.toString());
            long questId = root.get("questId").asLong();
            request.getSession().setAttribute("newQuestId", null);
            request.getSession().setAttribute("current_quest", questId);
            JSP.forward(request, response, "quest_creator");
//            response.sendRedirect(request.getContextPath() + "quest_creator.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
