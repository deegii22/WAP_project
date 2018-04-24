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
import java.util.Arrays;
import java.util.Date;

@WebServlet("/Event")
@MultipartConfig
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
        long userId = 1;
        switch (action) {
            case "upcomingList":
                out.print(eventList(0, userId));
                break;
            case "ongoingList":
                out.print(eventList(1, userId));
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
            Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();
            byte[] buffer = new byte[fileContent.available()];
            fileContent.read(buffer);
            File targetFile = new File("D:\\UploadFiles\\123"+fileName);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            Event event = new Event();
            event.setName(name);
            event.setRoute("D:\\UploadFiles\\123"+fileName);
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

            DBService db = new DBService();
            return db.AddEvent(event);
        } catch (Exception ex) {
            return new Result(ex.getMessage(), null);
        }
    }

    private String eventList(int type, long userId) {
        DBService db = new DBService();
        return Arrays.toString(db.eventList(type));
    }

    private void startEvent(int eventId) {

    }
}
