<%@ include file="parts/header.jsp" %>

<form action="login" method="post">
  <div class="elementToLeft">
    <p class="elementToLeft">Login</p>
    <label for="loginId">
      <input class="elementToLeft" type="text" name="login" id="loginId" required>
    </label>
    <p class="elementToLeft">Password</p>
    <label for="passwordId" >
      <input class="elementToLeft" type="password" name="password" id="passwordId" required>
    </label>
    <label>
      <button type="submit" class="elementToLeft">Log in</button>
    </label>
    <c:if test="${requestScope.loginError != null}">
        <p class="warningRed">${requestScope.loginError}</p>
    </c:if>
  </div>

</form>

<%@ include file="parts/footer.jsp" %>
