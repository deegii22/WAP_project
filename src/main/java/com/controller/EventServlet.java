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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
            String urlImg = getImageUrl(request);
            Event event = new Event();
            event.setName(name);
            event.setRoute(urlImg);
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
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        String dtString = dt.toString(dtf);
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

    private String eventList(int type, long userId) {
        DBService db = new DBService();
        return Arrays.toString(db.eventList(type));
    }

    private void startEvent(int eventId) {

    }
}
