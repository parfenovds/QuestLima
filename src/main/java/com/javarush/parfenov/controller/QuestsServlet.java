package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "QuestsServlet", value = "/quests")
public class QuestsServlet extends HttpServlet {
    private static final QuestService QUEST_SERVICE = QuestService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        if(user != null) {
//            session.setAttribute("quests", QUEST_SERVICE.getQuestDtoByUserLogin(user.getLogin()));
            JSP.forward(request, response, "quests");
        } else {
            JSP.forward(request, response, "login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
