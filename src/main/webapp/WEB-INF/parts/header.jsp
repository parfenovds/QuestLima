<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>JSP - Hello World</title>
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/style.css"/>'>
</head>
<body>
<ul id="nav">
  <li><a href="/">Home</a></li>
  <li><a href="quests" >Your quests</a></li>
  <li><a href="menu_of_quests" >All quests</a></li>

  <c:if test="${sessionScope.user != null}">
    <li><a href="logout">Logout for ${sessionScope.user.login}</a></li>
  </c:if>
  <c:if test="${sessionScope.user == null}">
    <li><a href="login">Login</a></li>
    <li><a href="registration">Registration</a></li>
  </c:if>
</ul>
<script>
  async function questPost(questId, url) {
    let quest = {
      questId
    };

    let response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(quest)
    });
    window.location='quest_creator';

    // let result = response.status;
    // alert(result);
  }
</script>
<script>
  function questsPlusReloadPage() {
    window.location='quests';
    window.location.reload();
  }
</script>