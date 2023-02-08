package com.javarush.parfenov.controller;

import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.entity.User;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.service.UserService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "NewQuestServlet", value = "/newQuest")
public class NewQuestServlet extends HttpServlet {
    private static final QuestService questService = QuestService.INSTANCE;
    private static final UserService USER_SERVICE = UserService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "new_quest");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = ((UserDto) request.getSession().getAttribute("user")).getLogin();
        Optional<User> byLogin = USER_SERVICE.getByLogin(login);
        QuestDto questDto = QuestDto.builder()
                .userId(byLogin.get().getId())
                .text(request.getParameter("text"))
                .name(request.getParameter("name"))
                .build();
        Quest quest = questService.create(questDto);
        request.getSession().setAttribute("current_quest", -1L);
        request.getSession().setAttribute("newQuestId", quest.getId());
        JSP.forward(request, response, "quest_creator");
    }
}
