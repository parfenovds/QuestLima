<%@ include file="parts/header.jsp" %>
<script>
    async function myFunction(questId) {
        let quest = {
            questId
        };

        let response = await fetch('/returnJson', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(quest)
        });

        let result = response.status;
        alert(result);
    }
</script>
<%--${requestScope.}--%>
<p>Привет, ${sessionScope.user}!</p>
<p>Твои квесты:</p>
<c:forEach var="quest" items="${sessionScope.quests}">
    <button onclick="myFunction(${quest.id})"></button>
    <p>${quest.name} - ${quest.text}</p>
</c:forEach>


<%@ include file="parts/footer.jsp" %>
