<%--
  Created by IntelliJ IDEA.
  User: deegi
  Date: 4/21/2018
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<body>
    <div class="container">
        <div class="col-md-6 offset-md-3" style="margin-top: 30px">
            <h1>Sign Up</h1>
            <%
                String errorMessage = (String)request.getAttribute("signupError");
                if(errorMessage != null){
            %>
            <div class="alert alert-danger" role="alert" style="margin-top: 10px">
                <%out.print(errorMessage);%>
            </div>
            <% }  %>
            <form action="Signup" method="post">
                <div class="form-group">
                    <label for="name">Name: </label>
                    <input type="text" name="name" id="name" placeholder="First Name" pattern="[a-zA-Z]{1,30}" title="Please input your name!" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="sex">Sex: </label>
                    <select name="sex" id="sex" class="form-control" required>
                        <option value="Male" selected>Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="email">Email: </label>
                    <input type="email" name="email" id="email" placeholder="Email" pattern="[\w]{3,15}@[\w]{2,10}.[a-zA-Z]{2,8}" title="Please input correct format email!" class="form-control" required /></br>
                </div>
                <div class="form-group">
                    <label for="pwd1">Password: </label>
                    <input type="password" name="pwd1" id="pwd1" placeholder="Password" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="pwd2">Repeat Password: </label>
                    <input type="password" name="pwd2" id="pwd2" placeholder="Renter Password" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="phone">Phone: </label>
                    <input type="text" name="phone" id="phone" placeholder="Phone" pattern="[\d]{10}" title="The phone number must be 10 digits number." class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="bday">Birthday: </label>
                    <input type="date" name="bday" id="bday" placeholder="Please input birthday!" class="form-control" required/>
                </div>
                <a href="login.jsp" class="btn btn-primary">Login</a>
                <button type="submit" id="signup" class="btn btn-primary">Sign Up</button>
            </form>
        </div>
</div>
</body>
</html>
