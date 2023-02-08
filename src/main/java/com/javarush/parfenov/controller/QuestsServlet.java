package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "QuestsServlet", value = "/quests")
public class QuestsServlet extends HttpServlet {
    private static final QuestService QUEST_SERVICE = QuestService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        session.setAttribute("quests", QUEST_SERVICE.getQuestDtoByUserLogin(userDto.getLogin()));
        JSP.forwardToPathIfLoggedIn(request, response, userDto, "quests");
    }
}
