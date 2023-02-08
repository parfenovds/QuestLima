package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.util.JSP;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "QuestCreatorServlet", value = "/quest_creator")
public class QuestCreatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "quest_creator");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        long questId = root.get("questId").asLong();
        request.getSession().setAttribute("newQuestId", null);
        request.getSession().setAttribute("current_quest", questId);
        JSP.forward(request, response, "quest_creator");
    }
}
