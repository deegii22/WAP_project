package com.controller;

import com.model.Event;
import com.service.DBService;
import com.service.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "EventServlet")
public class EventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String eventName = request.getParameter("eventName");
            int ownerID = 1;    //session-s awna.
            String route = request.getParameter("route");
            Date start = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("start"));
            Date end = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("end"));
            Event event = new Event(1,eventName, ownerID, route, start, end);
            DBService db = new DBService();
            Result res = db.AddEvent(event);
            //String jsonData = readFile("properties.json");
            //JSONObject jobj = new JSONObject(jsonData);
            //JSONArray jarr = new JSONArray(jobj.getJSONArray("keywords").toString());
            //System.out.println("Name: " + jobj.getString("name"));
            //for(int i = 0; i < jarr.length(); i++) {
            //    System.out.println("Keyword: " + jarr.getString(i));
            //}
        }
        catch (Exception ex) {
            out.print("Sdarara");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
