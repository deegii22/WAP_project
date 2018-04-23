package com.controller;

import com.model.LoginCheck;
import com.model.User;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/Login")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String[] usernamePassword = {request.getParameter("user"), request.getParameter("pwd")};
        User userInput = new User();
        userInput.setEmail(request.getParameter("user"));
        userInput.setPassword(request.getParameter("pwd"));

        LoginCheck check = new LoginCheck();
        RequestDispatcher dispatcher;

        if(check.checkUser(userInput)){
            //Create session object if it is already not created.
            HttpSession session = request.getSession(true);
            session.setAttribute("loginInfo", userInput);
            dispatcher = request.getRequestDispatcher("home.jsp");
        }else{
            request.setAttribute("loginError", new String("User not found!"));
            dispatcher = request.getRequestDispatcher("login.jsp");
        }

        dispatcher.forward(request, response);

    }
/*
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }*/
}
