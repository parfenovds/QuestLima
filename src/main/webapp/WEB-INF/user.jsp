<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <jsp:useBean id="user" scope="request"
                 type="com.javarush.khmelov.entity.User"/>
    <form class="form-horizontal" action="user?id=${user.id==null?0:user.id}" method="post"
          enctype="multipart/form-data">
        <fieldset>

            <!-- Form Name -->
            <legend>User Form</legend>

            <!-- File Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="image">Avatar</label>
                <div class="col-md-4">
                    <input id="image" name="image" class="input-file" type="file">
                </div>
            </div>

            <input type="hidden" name="id" value="${requestScope.id}">

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="userLogin">Login</label>
                <div class="col-md-4">
                    <input id="userLogin" name="login" type="text" placeholder=""
                           class="form-control input-md" required="" value="${user.login}">
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password" name="password" type="password" placeholder=""
                           class="form-control input-md" value="${user.password}"
                           required="">

                </div>
            </div>

            <!-- Select Basic -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <c:forEach items="${applicationScope.roles}" var="role">
                            <option value="${role}" ${user.role==role?"selected":""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="updateOrCreate"></label>
                <div class="col-md-8">
                    <button id="updateOrCreate" name="${requestScope.id>0?"update":"create"}"
                            class="btn btn-success">${requestScope.id>0?"Update":"Crete"}
                    </button>
                    <c:if test="${requestScope.id>0}">
                        <button id="delete" name="delete"
                                class="btn btn-danger">Delete
                        </button>
                    </c:if>
                </div>
            </div>

        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>