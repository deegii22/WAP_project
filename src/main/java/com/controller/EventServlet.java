package com.controller;

import com.model.Event;
import com.service.DBService;
import com.service.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    Result res = addEvent(request);
                    out.print(res.getDesc());
                    break;
            }
        }
        catch (Exception ex) {
            out.print("Sdarara");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private Result addEvent(HttpServletRequest request)
    {
        try {
            String eventName = request.getParameter("eventName");
            int ownerID = 1;    //session-s awna.
            String name = request.getParameter("eventName");
            String route = request.getParameter("route");
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("start"));
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("end"));
            Event event = new Event();
            event.setName(name);
            event.setStartDate(start);
            event.setEndDate(end);
            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(route);
            for(int i=0;i< json.size();i++){
                String s1 = (String) ((JSONObject) json.get(0)).get("startPosition");
                String s2 = (String)((JSONObject) json.get(0)).get("endPosition");
                String s3 = (String)((JSONObject) json.get(0)).get("duration");
            }

            DBService db = new DBService();
            return db.AddEvent(event);
        }
        catch (Exception ex){
            return new Result(ex.getMessage(), null);
        }
    }
}
