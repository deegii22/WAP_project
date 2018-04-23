<%--
  Created by IntelliJ IDEA.
  User: deegi
  Date: 4/21/2018
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="header.jsp"%>
<body>

    <h1>Login</h1>
    <p id="errorMessage" style="color:red">
        <%
            String errorMessage = (String)request.getAttribute("loginError");
            if(errorMessage != null){
            out.print(errorMessage);
        } %>
    </p>
    <form action="Login" method="post">
        <label for="user">Username: </label>
        <input type="email" name="user" id="user" placeholder="User name"
               pattern="[\w]{3,15}@[\w]{2,10}.[a-zA-Z]{2,8}" title="Please input correct format email!" required /></br>
        <label for="pwd">Password: </label><input type="password" name="pwd" id="pwd" required placeholder="Password"/></br>
        <button type="submit" id="login">Login</button>
        <button id="signup" onclick="showSignUpPage()">Sign Up</button>
    </form>

</body>
</html>
