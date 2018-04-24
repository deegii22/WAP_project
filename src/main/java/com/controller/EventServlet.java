package com.controller;

import com.model.Event;
import com.model.EventRoute;
import com.service.DBService;
import com.service.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

@WebServlet("/Event")
@MultipartConfig
public class EventServlet extends HttpServlet {
    DBService db = new DBService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action");
            int id = 0;
            switch (action) {
                case "add":
                    Result res = addEvent(request);
                    out.print(res.getDesc());
                    break;
                case "startEvent":
                    id = Integer.parseInt(request.getParameter("id"));
                    db.startEvent(id);
                    break;
                case "joinEvent":
                    id = Integer.parseInt(request.getParameter("id"));
                    int user = (int) request.getSession().getAttribute("user");
                    db.joinEvent(id, user);
                    break;
                case "finishRoute":
                    id = Integer.parseInt(request.getParameter("id"));
                    int priority = Integer.parseInt(request.getParameter("priority"));
                    db.finishRoute(id, priority);
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
        int id = 0;
        switch (action) {
            case "upcomingList":
                out.print(Arrays.toString(db.eventList(0)));
                break;
            case "ongoingList":
                out.print(Arrays.toString(db.eventList(1)));
                break;
            case "get":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(db.getEvent(id));
                break;
            case "getAllRoutes":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(Arrays.toString(db.eventRoutes(id, false)));
                break;
            case "getActiveRoutes":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(Arrays.toString(db.eventRoutes(id, true)));
                break;
            case "getMembers":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(Arrays.toString(db.eventMembers(id)));
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start"), formatter);
            LocalDateTime end = LocalDateTime.parse(request.getParameter("end"), formatter);
            Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();
            byte[] buffer = new byte[fileContent.available()];
            fileContent.read(buffer);
            File targetFile = new File("D:\\UploadFiles\\123" + fileName);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            Event event = new Event();
            event.setName(name);
            event.setRoute("D:\\UploadFiles\\123" + fileName);
            event.setStartDate(start);
            event.setEndDate(end);
            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(route);
            for (int i = 0; i < json.size(); i++) {
                EventRoute eRoute = new EventRoute();
                eRoute.setPriority(i);
                eRoute.setStartPosition((String) ((JSONObject) json.get(0)).get("startPosition"));
                eRoute.setEndPosition((String) ((JSONObject) json.get(0)).get("endPosition"));
                eRoute.setDuration(Integer.parseInt((String) ((JSONObject) json.get(0)).get("duration")));
                event.addRoute(eRoute);
            }

            return db.AddEvent(event);
        } catch (Exception ex) {
            return new Result(ex.getMessage(), null);
        }
    }
}
