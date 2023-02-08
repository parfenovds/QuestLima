package com.javarush.parfenov.controller;

import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "AllQuestsServlet", value = "/menu_of_quests")
public class AllQuestsServlet extends HttpServlet {
    private static final QuestService QUEST_SERVICE = QuestService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Quest> allQuests = QUEST_SERVICE.getAll();
        request.getSession().setAttribute("allQuests", allQuests);
        JSP.forward(request, response, "menu_of_quests");
    }
}
