<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User: ${requestScope.user.login!=null?requestScope.user.login:"none"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous"
    >

</head>
<body>
<form class="form-horizontal" action="user?id=${requestScope.user.id}" method="post">
    <fieldset>

        <!-- Form Name -->
        <legend>User Form</legend>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="userLogin">Login</label>
            <div class="col-md-4">
                <input id="userLogin" name="login" type="text" placeholder=""
                       class="form-control input-md" required="" value="${requestScope.user.login}">

            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="password">Password</label>
            <div class="col-md-4">
                <input id="password" name="password" type="password" placeholder=""
                       class="form-control input-md" value="${requestScope.user.password}"
                       required="">

            </div>
        </div>

        <!-- Select Basic -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="role">Role</label>
            <div class="col-md-4">
                <select id="role" name="role" class="form-control">
                    <c:forEach items="${applicationScope.roles}" var="role">
                        <option value="${role}" ${requestScope.user.role==role?"selected":""}>${role}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <!-- Button (Double) -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="updateOrCreate"></label>
            <div class="col-md-8">
                <button id="updateOrCreate" name="updateOrCreate"
                        class="btn btn-success">updateOrCreate
                </button>

                <button id="delete" name="delete"
                        class="btn btn-danger">Delete
                </button>
            </div>
        </div>

    </fieldset>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous">
</script>

</body>
</html>
