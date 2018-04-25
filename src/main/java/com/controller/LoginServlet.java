package com.controller;

import com.model.User;
import com.service.DBService;
import com.service.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/Login")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setEmail(request.getParameter("user").trim());
        user.setPassword(request.getParameter("pwd").trim());

        DBService dbservice = new DBService();
        Connection con = dbservice.connectDB();

        Result result = dbservice.login(user);
        RequestDispatcher dispatcher;

        if(result.getObj() != null){
            //Create session object if it is already not created.
            User currentUser = (User) result.getObj();
            HttpSession session = request.getSession(true);
            session.setAttribute("user", currentUser);
            ServletContext sc =  getServletContext();
            sc.setAttribute("userid", currentUser.getId());
            dispatcher = request.getRequestDispatcher("home.jsp");
        }else{
            request.setAttribute("loginError", result.getDesc());
            dispatcher = request.getRequestDispatcher("login.jsp");
        }

        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
