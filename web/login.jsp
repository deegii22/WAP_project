<%--
  Created by IntelliJ IDEA.
  User: deegi
  Date: 4/21/2018
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<body>
    <div class="container">
        <div class="col-md-4 offset-md-4" style="margin-top: 100px">
            <div class="text-center" >
                <img src="resources/images/logo.png" width="180px" alt="logo"/>
            </div>

            <%
                String errorMessage = (String)request.getAttribute("loginError");
                if(errorMessage != null){
            %>
                <div class="alert alert-danger" role="alert" style="margin-top: 10px">
                    <%out.print(errorMessage);%>
                </div>
            <% }  %>
            <%--<h2>Cycling Club Application</h2>--%>
            <form action="Login" method="post">
                <div class="form-group">
                    <label for="user">Username: </label>
                    <input type="email" class="form-control" id="exampleInputEmail1" name="user" id="user" aria-describedby="emailHelp" placeholder="Enter email" pattern="[\w]{3,15}@[\w]{2,10}.[a-zA-Z]{2,8}" title="Please input correct format email!" required >
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="pwd">Password: </label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="pwd" id="pwd" required placeholder="Password"/>
                </div>
                <button type="submit" id="login" class="btn btn-primary">Login</button>
                <a href="signup.jsp" class="btn btn-warning">Sign Up</a>
            </form>
        </div>
    </div>

</body>
</html>
