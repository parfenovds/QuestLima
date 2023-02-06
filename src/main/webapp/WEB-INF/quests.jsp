<%@ include file="parts/header.jsp" %>
<script>
    async function deletqQuest(questId) {
        let quest = {
            questId
        };

        let response = await fetch('/removeQuest', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(quest)
        });
        window.location.reload();
    }
</script>
<p>Привет, ${sessionScope.user.login}!</p>
<p>Твои квесты:</p>
<c:forEach var="quest" items="${sessionScope.quests}">
    <p>${quest.name}
        <a href="javascript:questPost(${quest.id}, '/quest_creator')">редактировать</a>
        <a href="javascript:deletqQuest(${quest.id})">удалить</a> </p>
</c:forEach>
<a href="newQuest">создать новый</a>
<%--<a href="javascript:myFunction(-1)">создать новый</a>--%>

<%@ include file="parts/footer.jsp" %>
