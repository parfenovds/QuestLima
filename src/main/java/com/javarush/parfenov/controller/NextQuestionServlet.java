package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.ParentToChild;
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

    @Override
    public void init() throws ServletException {
        nodeService = NodeService.INSTANCE;
        parentToChildService = ParentToChildService.INSTANCE;
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "play_quest");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = JsonStringExtractor.getJsonParameter(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        long questId = root.get("questId").asLong();
        long answerNodeId = root.get("nodeId").asLong();
        Node answerNode = nodeService.get(answerNodeId, questId).get();
        Long nodeId;
        if (answerNode.getNextLonelyId() != 0L) {
            nodeId = answerNode.getNextLonelyId();
        } else {
            List<ParentToChild> appropriate = parentToChildService.getAppropriate(questId, answerNodeId);
            nodeId = appropriate.get(0).getChildNodeId();
        }
        HttpSession session = request.getSession();
        Node node = nodeService.get(nodeId, questId).get();
        List<Node> answers = nodeService.getByParentId(node.getNodeId(), questId);
        List<ParentToChild> appropriate = parentToChildService.getAppropriate(questId, nodeId);
        for (ParentToChild parentToChild : appropriate) {
            Long childNodeId = parentToChild.getChildNodeId();
            Node node1 = nodeService.get(childNodeId, questId).get();
            answers.add(node1);
        }
        session.setAttribute("currentQuestion", node);
        session.setAttribute("currentAnswers", answers);
        JSP.forward(request, response, "play_quest");
    }
}
