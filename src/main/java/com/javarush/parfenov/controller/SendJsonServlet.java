package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.service.JsonPrepareService;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SendJsonServlet", value = "/returnJson")
public class SendJsonServlet extends HttpServlet {

    private JsonPrepareService jsonPrepareService;

    @Override
    public void init() throws ServletException {
        jsonPrepareService = JsonPrepareService.INSTANCE;
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        long questId = root.get("questId").asLong();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonPrepareService.getJson(questId));
        out.flush();
    }
}
