<%@ include file="parts/header.jsp" %>
<%--<form action="nextQuestion" method="post">--%>
<%--    <p>${sessionScope.currentQuestion}</p>--%>
<%--    <c:forEach var="answer" items="${sessionScope.currentAnswers}">--%>
<%--        <input type="radio" id="node_id_${answer.nodeId}" name="answer" value="{${answer.nodeId}}"/>--%>
<%--        <label for="node_id_${answer.nodeId}">${answer.text}</label>--%>
<%--    </c:forEach>--%>
<%--    <button type="submit">GO</button>--%>
<%--</form>--%>
<script>
    async function nextQuestion(questId, nodeId, url) {
        let quest = {
            questId,
            nodeId
        };

        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(quest)
        });
         window.location = '/nextQuestion';
    }
</script>

<p>${sessionScope.currentQuestion.text}</p>
<c:forEach var="answer" items="${sessionScope.currentAnswers}">
    <p><a href="javascript:nextQuestion(${answer.questId}, ${answer.nodeId}, '/nextQuestion')">${answer.text}</a></p>
<%--    <input type="radio" id="node_id_${answer.nodeId}" name="answer" value="{${answer.nodeId}}"/>--%>
<%--    <label for="node_id_${answer.nodeId}">${answer.text}</label>--%>
</c:forEach>


<%@ include file="parts/footer.jsp" %>
