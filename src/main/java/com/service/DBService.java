package com.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.model.Event;
import java.sql.*;
import java.util.*;


public class DBService {

    static Connection conn = null;
    static Statement stmt = null;
    String sql;

    public static Connection connectDB() {
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public Result AddEvent(Event event) {
        conn = connectDB();
        sql = "insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)\n" +
                "values ('" + event.getName() + "', 0, STR_TO_DATE('25-04-2018', '%d-%m-%Y'), STR_TO_DATE('26-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());";

        try {

            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                return new Result("Add Event Successful", null);
            }
        } catch (Exception e) {
            return new Result(e.getMessage(), null);
        }
        return null;
    }

    public String eventList(int type) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ret = "[]";
        try {
            preparedStatement = conn.prepareStatement("select * from Event where status = ?");
            preparedStatement.setInt(1, type);
            resultSet = preparedStatement.executeQuery();
            List<Event> events = new ArrayList();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventID(resultSet.getInt("event_id"));
                event.setName(resultSet.getString("name"));
                event.setStatus(resultSet.getInt("status"));
                event.setStartDate(resultSet.getDate("start_date"));
                event.setEndDate(resultSet.getDate("end_date"));
                event.setImg(resultSet.getString("route_img"));
                event.seteFlag(resultSet.getInt("emergency_flag"));
                event.seteInfo(resultSet.getString("emergency_info"));
                event.setCreated(resultSet.getDate("created"));
                events.add(event);
            }
            //https://github.com/google/gson
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(events, new TypeToken<List<Event>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            ret = jsonArray.toString();
        } catch (Exception e) {
            ret = "[]";
        }
        return ret;
    }
        /*
        public Result login(String userId, String password) {
            System.out.println("Login");
            sql = "select * from employee where empid='" + userId + "'";
            try {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String pwd = rs.getString("password");
                    String role = rs.getString("role");

                    if (password.equals(pwd)) {

                        return new Result("", role);
                    } else
                        return new Result("Invalid username or password", null);

                }

            } catch (SQLException e) {
                e.printStackTrace();
                return new Result("Invalid username or password", null);
            }
            return new Result("Invalid username or password", null);
        }


        */
}
