<%@ include file="parts/header.jsp" %>

<form action="registration" method="post">
    <div class="elementToLeft">
        <p class="elementToLeft">Login</p>
        <label for="loginId">
            <input class="elementToLeft" type="text" name="login" id="loginId" required>
        </label>
        <p class="elementToLeft">Password</p>
        <label for="passwordId" >
            <input class="elementToLeft" type="password" name="password" id="passwordId" required>
        </label>
        <p class="elementToLeft">Role</p>
        <label >
            <select class="elementToLeft" name="role" id="roleId">
                <c:forEach var="role" items="${applicationScope.roles}">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </label>
        <label>
            <button type="submit" class="elementToLeft">Register</button>
        </label>
        <c:if test="${requestScope.errors != null}">
            <c:forEach var="error" items="${requestScope.errors}">
                <p class="warningRed">${error}</p>
            </c:forEach>
        </c:if>
    </div>

</form>

<%@ include file="parts/footer.jsp" %>
