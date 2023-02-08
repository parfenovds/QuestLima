package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveQuestServlet", value = "/removeQuest")
public class RemoveQuestServlet extends HttpServlet {
    private static final QuestService questService = QuestService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        long questId = root.get("questId").asLong();
        questService.delete(questId);
        JSP.forward(request, response, "quests");
    }
}
