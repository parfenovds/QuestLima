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
    private QuestService questService;

    @Override
    public void init() throws ServletException {
        questService = QuestService.INSTANCE;
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        session.setAttribute("quests", questService.getQuestDtoByUserLogin(userDto.getLogin()));
        JSP.forwardToPathIfLoggedIn(request, response, userDto, "quests");
    }
}
