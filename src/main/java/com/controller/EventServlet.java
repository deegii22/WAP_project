package com.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
                out.print(db.eventRoutes(id, false));
                break;
            case "getActiveRoutes":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(db.eventRoutes(id, true));
                break;
            case "getMembers":
                id = Integer.parseInt(request.getParameter("id"));
                out.print(db.eventMembers(id));
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
            String urlImg = getImageUrl(request);
            Event event = new Event();
            event.setName(name);
            event.setRoute(urlImg);
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

    public String getImageUrl(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("file");
        final String fileName = filePart.getSubmittedFileName();
        // Check extension of file
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
            for (String s : allowedExt) {
                if (extension.equals(s)) {
                    return this.uploadFile(filePart);
                }
            }
            throw new ServletException("file must be an image");
        }
        return "";
    }
    public String uploadFile(Part filePart) throws IOException {
        LocalDateTime dt = LocalDateTime.now();
        String dtString = dt.toString();
        final String fileName =  dtString + filePart.getSubmittedFileName();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJTPJNGONK2H3N74Q", "skofv8UdMWcxm7PvyDsJztrtCXxUpjqGrKs2wFuR");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        InputStream inputStream = filePart.getInputStream();
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(getServletContext().getRealPath("/")
                    + fileName));
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {

        } finally {
            if (out != null) {
                out.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(getServletContext().getRealPath("/")
                    + fileName);
            s3Client.putObject(new PutObjectRequest(
                    "wapbucket", fileName , file));
            return s3Client.getUrl("wapbucket",fileName).toString();

        } catch (AmazonServiceException ase) {
            return ase.getMessage();
        }
    }
}
