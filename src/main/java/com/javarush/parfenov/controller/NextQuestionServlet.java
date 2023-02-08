package com.javarush.parfenov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.ParentToChild;
import com.javarush.parfenov.service.NextDialogService;
import com.javarush.parfenov.service.NodeService;
import com.javarush.parfenov.service.ParentToChildService;
import com.javarush.parfenov.util.JSP;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NextQuestionServlet", value = "/nextQuestion")
public class NextQuestionServlet extends HttpServlet {
    private NodeService nodeService;
    private ParentToChildService parentToChildService;
    private NextDialogService nextDialogService;

    @Override
    public void init() throws ServletException {
        nodeService = NodeService.INSTANCE;
        parentToChildService = ParentToChildService.INSTANCE;
        nextDialogService = NextDialogService.INSTANCE;
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "play_quest");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        Node answerNode = nextDialogService.getAnswerNode(jsonString);
        long questId = answerNode.getQuestId();
        long answerNodeId = answerNode.getNodeId();
        Long nextQuestionId = nextDialogService.getNextQuestionId(answerNode, questId, answerNodeId);
        Node nextQuestion = nodeService.get(nextQuestionId, questId).get();
        List<Node> answers = nextDialogService.getAnswers(questId, nextQuestionId, nextQuestion);
        HttpSession session = request.getSession();
        session.setAttribute("currentQuestion", nextQuestion);
        session.setAttribute("currentAnswers", answers);
        JSP.forward(request, response, "play_quest");
    }
}
