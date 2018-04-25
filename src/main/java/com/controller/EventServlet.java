package com.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.model.Event;
import com.model.EventRoute;
import com.model.User;
import com.service.DBService;
import com.service.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.ServletContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
        ServletContext sc = getServletContext();
        Integer userId = Integer.parseInt(String.valueOf(sc.getAttribute("userid")));
        try {
            String action = request.getParameter("action");
            int id = 0;
            int priority = 0;
            switch (action) {
                case "add":
                    Result res = addEvent(request, userId);
                    if(res.getDesc().equals(""))
                        out.print("Event added successfully.");
                    else
                        out.print(res.getDesc());
                    break;
                case "startEvent":
                    id = Integer.parseInt(request.getParameter("id"));
                    db.startEvent(id);
                    break;
                case "joinEvent":
                    id = Integer.parseInt(request.getParameter("id"));
                    db.joinEvent(id, userId);
                    break;
                case "finishRoute":
                    id = Integer.parseInt(request.getParameter("id"));
                    priority = Integer.parseInt(request.getParameter("priority"));
                    db.finishRoute(id, priority);
                    break;
                case "setEFlag":
                    id = Integer.parseInt(request.getParameter("id"));
                    priority = Integer.parseInt(request.getParameter("priority"));
                    String info = request.getParameter("info");
                    db.updateEFlag(id, priority, info);
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
        ServletContext sc = getServletContext();
        Integer userId = Integer.parseInt(String.valueOf(sc.getAttribute("userid")));
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
                out.print(db.getEvent(id, userId));
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
            case "getEFlags":
                out.print(Arrays.toString(db.getEFlags()));
                break;
            default:
                break;
        }
        out.flush();
    }

    //This is addEvent function that receive HTTPServletRequest and instantiate Event model and
    //save route image to AWS and save to the DB.
    private Result addEvent(HttpServletRequest request, int userid) {
        try {
            String name = request.getParameter("name");
            String route = request.getParameter("route");
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm");
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start"), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime end = LocalDateTime.parse(request.getParameter("end"), DateTimeFormatter.ISO_DATE_TIME);
            Result res1 = getImageUrl(request);
            if(!res1.getDesc().equals(""))
                return res1;
            String urlImg = String.valueOf(res1.getObj());
            Event event = new Event();
            event.setOwnerId(userid);
            event.setName(name);
            event.setRoute(urlImg);
            event.setStartDate(start);
            event.setEndDate(end);
            //event route is the JSON array and we convert to EventRoute model and add Event model.
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
    //This is main function to check file extensions and call upload.
    public Result getImageUrl(HttpServletRequest req) throws IOException, ServletException {
        try {
            Part filePart = req.getPart("file");
            final String fileName = filePart.getSubmittedFileName();
            //Security check we must upload only images.
            if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
                final String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
                String[] allowedExt = {"jpg", "jpeg", "png", "gif"};
                for (String s : allowedExt) {
                    if (extension.equals(s)) {
                        return this.uploadFile(filePart);
                    }
                }
                return new Result("File must be an image.", null);
            }
            else
                return new Result("Invalid file name.", null);
        }
        catch (Exception ex){
            return new Result(ex.getMessage(), null);
        }
    }
    //This function upload to AWS server
    public Result uploadFile(Part filePart) throws IOException {
        try {
            //Creates file name. It's possible that files with same names are uploaded.
            //We  should create unique names. /Unique enough/
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd-HHmmssSSS");
            LocalDateTime dt = LocalDateTime.now();
            String dtString = dt.format(formatter);
            final String fileName = dtString + filePart.getSubmittedFileName();
            //Creates aws credential via accesskey and secretKey that needs upload to AWS server.
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIJ2BJZJN37KOCTNQ", "2uyv4jlIQaFcSwiM7+4dCp7GQ70vtuJmmdYTOA/r");
            //S3 client with us-east-2 region that connect to AWS server as a client.
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
            //Ajax sent to servlet file as a FilePart and converted to File.
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
                return new Result(fne.getMessage(),"");
            } finally {
                if (out != null) {
                    out.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            try {
                //Here is uploading file to bucket and get url of the file on AWS.
                File file = new File(getServletContext().getRealPath("/")
                        + fileName);
                s3Client.putObject(new PutObjectRequest(
                        "wapbucket", fileName, file));
                return new Result("", s3Client.getUrl("wapbucket", fileName).toString());

            } catch (AmazonServiceException ase) {
                return new Result(ase.getMessage(),null);
            }
        }
        catch (Exception ex){
            return new Result(ex.getMessage(),null);
        }
    }
}
