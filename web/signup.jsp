<%--
  Created by IntelliJ IDEA.
  User: deegi
  Date: 4/21/2018
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="header.jsp"%>
<body>

    <h1>Sign Up</h1>
    <br target="" method="post">
        <label for="name">Name: </label>
        <input type="text" name="name" id="name" placeholder="Firstname" pattern="[a-zA-Z]{1,30}" title="Please input your name!"/></br>

        <label for="sex">Sex: </label>
        <select name="sex" id="sex">
            <option value="Male" selected>Male</option>
            <option value="Female">Female</option>
        </select><br>

        <label for="mail">Email: </label>
        <input type="email" name="mail" id="mail" placeholder="Email" pattern="[\w]{3,15}@[\w]{2,10}.[a-zA-Z]{2,8}" title="Please input correct format email!" required /></br>

        <label for="pwd1">Password: </label>
        <input type="password" name="pwd1" id="pwd1" placeholder="Password" required/></br>

        <label for="pwd2">Repeat Password: </label>
        <input type="password" name="pwd2" id="pwd2" placeholder="Renter Password" required/></br>

        <label for="phone">Phone: </label>
        <input type="text" name="phone" id="phone" placeholder="Phone" required/></br>

        <label for="bday">Birthday: </label>
        <input type="date" name="phone" id="bday" placeholder="Please input birthday!" required/></br>

        <button id="login" onclick="showloginPage()">Login</button>
        <button type="submit" id="signup">Sign Up</button>
    </form>

</body>
</html>
