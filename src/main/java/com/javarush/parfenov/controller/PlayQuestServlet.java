package com.javarush.parfenov.controller;

import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.service.NodeService;
import com.javarush.parfenov.service.QuestService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@WebServlet(name = "PlayQuestServlet", value = "/playQuest")
public class PlayQuestServlet extends HttpServlet {
    private static final QuestService QUEST_SERVICE = QuestService.INSTANCE;
    private static final NodeService NODE_SERVICE = NodeService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long questId = Long.parseLong(request.getParameter("id")); //TODO: check if exists
        HttpSession session = request.getSession();
        Node node = NODE_SERVICE.getByType(NodeType.INIT, questId).get();
        Collection<Node> answers = NODE_SERVICE.getByParentId(node.getNodeId(), questId);
        session.setAttribute("currentQuestion", node);
        session.setAttribute("currentAnswers", answers);
        JSP.forward(request, response, "play_quest");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
