package com.controller;

import com.model.Event;
import com.model.EventRoute;
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
import java.util.Arrays;
import java.util.Date;

@WebServlet("/Event")
public class EventServlet extends HttpServlet {
    DBService db = new DBService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    Result res = addEvent(request);
                    out.print(res.getDesc());
                    addEvent(request);
                    break;
                case "startEvent":
                    startEvent(1);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            out.print("Sdarara");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /*long userId = (long) request.getSession().getAttribute("user");*/

        switch (action) {
            case "upcomingList":
                out.print(Arrays.toString(db.eventList(0)));
                break;
            case "ongoingList":
                out.print(Arrays.toString(db.eventList(1)));
                break;
            case "get":
                int id = Integer.parseInt(request.getParameter("id"));
                out.print(db.getEvent(id));
                break;
            default:
                break;
        }
        out.flush();
    }

    private Result addEvent(HttpServletRequest request) {
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
                EventRoute eRoute = new EventRoute();
                eRoute.setPriority(i);
                eRoute.setStartPosition((String) ((JSONObject) json.get(0)).get("startPosition"));
                eRoute.setEndPosition((String)((JSONObject) json.get(0)).get("endPosition"));
                eRoute.setDuration(Integer.parseInt((String) ((JSONObject) json.get(0)).get("duration")));
                event.addRoute(eRoute);
            }

            return db.AddEvent(event);
        } catch (Exception ex) {
            return new Result(ex.getMessage(), null);
        }
    }

    private void startEvent(int eventId) {

    }
}
