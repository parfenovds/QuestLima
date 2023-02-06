package com.javarush.parfenov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.entity.ParentToChild;
import com.javarush.parfenov.service.NodeService;
import com.javarush.parfenov.service.ParentToChildService;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "NextQuestionServlet", value = "/nextQuestion")
public class NextQuestionServlet extends HttpServlet {
    private static final NodeService NODE_SERVICE = NodeService.INSTANCE;
    private static final ParentToChildService PARENT_TO_CHILD_SERVICE = ParentToChildService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "play_quest");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (BufferedReader reader = request.getReader()) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(sb.toString());
            long questId = root.get("questId").asLong();
            long answerNodeId = root.get("nodeId").asLong();
            Node answerNode = NODE_SERVICE.get(answerNodeId, questId).get();
            Long nodeId;
            if(answerNode.getNextLonelyId() != 0L) {
                nodeId = answerNode.getNextLonelyId();
            } else {
                List<ParentToChild> appropriate = PARENT_TO_CHILD_SERVICE.getAppropriate(questId, answerNodeId);
                nodeId = appropriate.get(0).getChildNodeId();
            }
            HttpSession session = request.getSession();
            Node node = NODE_SERVICE.get(nodeId, questId).get();
            List<Node> answers = NODE_SERVICE.getByParentId(node.getNodeId(), questId);
            List<ParentToChild> appropriate = PARENT_TO_CHILD_SERVICE.getAppropriate(questId, nodeId);
            for (ParentToChild parentToChild : appropriate) {
                Long childNodeId = parentToChild.getChildNodeId();
                Node node1 = NODE_SERVICE.get(childNodeId, questId).get();
                answers.add(node1);
            }
            session.setAttribute("currentQuestion", node);
            session.setAttribute("currentAnswers", answers);
//            response.sendRedirect(request.getContextPath() + "/quests");
//            doGet(request, response);
            JSP.forward(request, response, "play_quest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
