package com.javarush.parfenov.controller;

import com.javarush.parfenov.service.QuestObjectsService;
import com.javarush.parfenov.util.JSP;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GetJsonServlet", value = "/getJson")
public class GetJsonServlet extends HttpServlet {
    private QuestObjectsService questObjectsService;
    @Override
    public void init() throws ServletException {
        questObjectsService = QuestObjectsService.INSTANCE;
        super.init();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        questObjectsService.jsonParse(jsonString);
        JSP.forward(request, response, "quests");
    }
}
