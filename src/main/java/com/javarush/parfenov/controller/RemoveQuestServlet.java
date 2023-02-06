package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "RemoveQuestServlet", value = "/removeQuest")
public class RemoveQuestServlet extends HttpServlet {
    private static final QuestService questService = QuestService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            questService.delete(questId);

            JSP.forward(request, response, "quests");
//            response.sendRedirect(request.getContextPath() + "quest_creator.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
