package com.controller;


import com.model.User;
import com.service.DBService;
import com.service.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/Signup")
public class SignupServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd1 = request.getParameter("pwd1").trim();
        String pwd2 = request.getParameter("pwd2").trim();

        RequestDispatcher dispatcher;

        if(!pwd1.equals(pwd2)){
            request.setAttribute("error", new String("Inputed passwords aren't match."));
            dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }

        User user = new User();
        user.setName(request.getParameter("name").trim());
        user.setEmail(request.getParameter("email").trim());
        user.setSex(request.getParameter("sex"));
        user.setPassword(pwd1);
        user.setPhone(request.getParameter("phone").trim());
        user.setBirthday(request.getParameter("bday"));

        DBService dbservice = new DBService();
        Connection con = dbservice.connectDB();

        Result result = dbservice.addUser(user);

        if(result.getObj() == null){
            request.setAttribute("signupError", result.getDesc());
            dispatcher = request.getRequestDispatcher("signup.jsp");
        }else{
            dispatcher = request.getRequestDispatcher("login.jsp");
        }
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
