<%@ include file="parts/header.jsp"%>


<p>Привет, ${sessionScope.user.login == null ? "друг" : sessionScope.user.login}!</p>
<p>У нас есть следующие квесты:</p>
<c:forEach var="quest" items="${sessionScope.allQuests}">
    <p><a href="playQuest?id=${quest.id}">${quest.name}</a></p>
<%--    <a href="javascript:questPost(${quest.id}, '/quest_creator')">${quest.name}</a>--%>
</c:forEach>
<p><a href="newQuest">создать новый</a></p>

<%@ include file="parts/footer.jsp"%>
