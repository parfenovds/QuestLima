<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/style.css"/>'>
</head>
<body>
<ul id="nav">
  <li><a href="/">Home</a></li>
  <li><a href="quests">Quests</a></li>
  <li><a href="login">Login</a></li>
  <li><a href="registration">Registration</a></li>
  <c:if test="${sessionScope.user != null}">
    <li><a href="logout">Logout</a></li>
  </c:if>
</ul>