package com.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.model.Event;
import com.model.EventRoute;
import org.json.simple.JSONObject;
import com.model.User;

import java.sql.*;
import java.util.*;


public class DBService {

    static Connection conn = null;
    static Statement stmt = null;
    String sql;

    public static Connection connectDB() {
        try {
            if (conn == null) conn = DBSingleton.getInstance().getConnection();
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
                for (EventRoute e : event.getRoutes()) {
                    sql = "insert into Event_route (event_id, start_position, end_position, status, priority, duration)\n" +
                            "    values (LAST_INSERT_ID(), '" + e.getStartPosition() + "', '" + e.getEndPosition() + "', 0, " + e.getPriority() + ", " + e.getDuration() + ");";
                    int rsSub = stmt.executeUpdate(sql);
                    if(rsSub!=1) {
                        return new Result("Error occured on add Event Route.",null);
                    }
                }
                return new Result("",null);
            }
            else
                return new Result("Error occured on add Event.",null);
        } catch (Exception e) {
            return new Result(e.getMessage(), null);
        }
    }

    public JSONObject[] eventList(int type) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ret = "[]";
        JSONObject[] objectToReturn = new JSONObject[1];
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
                event.setOwnerId(resultSet.getInt("owner_id"));
                event.setImg(resultSet.getString("route_img"));
                event.seteFlag(resultSet.getInt("emergency_flag"));
                event.seteInfo(resultSet.getString("emergency_info"));
                event.setCreated(resultSet.getDate("created"));
                events.add(event);
            }

            Event[] data = events.stream().toArray(Event[]::new);

            if(data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                for(int i = 0; i< data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("eventId", data[i].getEventID());
                    res.put("name", data[i].getName());
                    res.put("status", data[i].getStatus());
                    res.put("startDate", "");
                    res.put("endDate", "");
                    res.put("ownerId", data[i].getOwnerId());
                    res.put("img", data[i].getImg());
                    res.put("emergencyFlag", data[i].geteFlag());
                    res.put("emergencyInfo", data[i].geteInfo());
                    res.put("created", "");
                    results[i] = res;
                }
                return results;
            }

            JSONObject res = new JSONObject();
            res.put("error", "No results found");
            objectToReturn[0] = res;
            return objectToReturn;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectToReturn;
    }

    public String routList(int eventId, Boolean isActive) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ret = "[]";
        try {
            if (!isActive) {
                preparedStatement = conn.prepareStatement("select * from Event_route where event_id = ? order by priority");
            } else {
                preparedStatement = conn.prepareStatement("select * from Event_route where event_id = ? and status = 0  order by priority");
            }
            preparedStatement.setInt(1, eventId);
            resultSet = preparedStatement.executeQuery();
            List<EventRoute> routes = new ArrayList();
            while (resultSet.next()) {
                EventRoute route = new EventRoute();
                route.setEventID(resultSet.getInt("event_id"));
                route.setStartPosition(resultSet.getString("start_position"));
                route.setEndPosition(resultSet.getString("end_position"));
                route.setStatus(resultSet.getInt("status"));
                route.setPriority(resultSet.getInt("priority"));
                route.setDuration(resultSet.getInt("duration"));
                routes.add(route);
            }
            //https://github.com/google/gson
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(routes, new TypeToken<List<Event>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            ret = jsonArray.toString();
        } catch (Exception e) {
            ret = "[]";
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public String startEvent(int eventId) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ret = "[]";
        try {
            preparedStatement = conn.prepareStatement("select * from Event_route where event_id = ? and status = 0  order by priority");
            preparedStatement.setInt(1, eventId);
            resultSet = preparedStatement.executeQuery();
            List<EventRoute> routes = new ArrayList();
            while (resultSet.next()) {
                EventRoute route = new EventRoute();
                route.setEventID(resultSet.getInt("event_id"));
                route.setStartPosition(resultSet.getString("start_position"));
                route.setEndPosition(resultSet.getString("end_position"));
                route.setStatus(resultSet.getInt("status"));
                route.setPriority(resultSet.getInt("priority"));
                route.setDuration(resultSet.getInt("duration"));
                routes.add(route);
            }
            //https://github.com/google/gson
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(routes, new TypeToken<List<Event>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            ret = jsonArray.toString();
        } catch (Exception e) {
            ret = "[]";
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public Result login(User user) {
        sql = "select * from user where email= ? ";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, user.getEmail());
            ResultSet rs = st.executeQuery();

            User res = new User();
            while (rs.next()) {
                res.setId(rs.getLong("user_id"));
                res.setName(rs.getString("name"));
                res.setEmail(rs.getString("email"));
                res.setPassword(rs.getString("password"));
                res.setPhone(rs.getString("phone"));
                res.setSex(rs.getString("sex"));
                res.setBirthday(rs.getString("birth_date"));

                String pwd = rs.getString("password");

                if (pwd.equals(user.getPassword())) {
                    return new Result("", res);
                } else
                    return new Result("Invalid username or password", null);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Result("Invalid username or password", null);
        }
        return new Result("Invalid username or password", null);
    }


    public Result addUser(User user) {
        String existCheckSQL = "select name from user where email= ?";

        sql = "insert into user (name, email, password, phone, sex, birth_date, created)" +
                " values ("
                + "'" + user.getName() + "'"
                + ", '" + user.getEmail() + "'"
                + ", '" + user.getPassword() + "'"
                + ", '" + user.getPhone() + "'"
                + ", '" + user.getSex() + "'"
                + ", '" + user.getBirthday() + "'"
                + ", now())";

        try {
            PreparedStatement st = conn.prepareStatement(existCheckSQL);
            st.setString(1, user.getEmail());
            ResultSet result = st.executeQuery();

            if (result.next()) {
                return new Result("This email address already exists!", null);
            }

            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                return new Result("Succeed sign up!", new String("Succeed sign up!"));
            }
        } catch (Exception e) {
            return new Result(e.getMessage(), null);
        }
        return null;
    }

}
