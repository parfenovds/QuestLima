package com.javarush.parfenov.controller;

import com.javarush.parfenov.repository.NodeRepository;
import com.javarush.parfenov.service.QuestObjectsService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "GetJsonServlet", value = "/getJson")
public class GetJsonServlet extends HttpServlet {
    private static final QuestObjectsService questObjectsService = QuestObjectsService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        JSP.forward(request, response, "quests");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (BufferedReader reader = request.getReader()) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            request.getSession().getAttribute("user.login");

            questObjectsService.jsonParse(sb.toString());
            JSP.forward(request, response, "quests");



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
