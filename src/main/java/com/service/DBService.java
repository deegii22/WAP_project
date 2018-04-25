package com.service;

import com.google.common.hash.Hashing;
import com.model.Event;
import com.model.EventMember;
import com.model.EventRoute;
import com.model.*;
import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBService {

    static Connection conn = null;
    static Statement stmt = null;
    String sql;

    //This is connection function to DB.
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
        //Add to DB event
        sql = "insert into Event (name, status, start_date, end_date, route_img, owner_id, emergency_flag, emergency_info, created)\n" +
                "values ('" + event.getName() + "', 0, STR_TO_DATE('25-04-2018', '%d-%m-%Y'), STR_TO_DATE('26-04-2018', '%d-%m-%Y'), '" + event.getRoute() + "', " + event.getOwnerId() + ", 0, '', sysdate());";

        try {
            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                for (EventRoute e : event.getRoutes()) {
                    sql = "insert into Event_route (event_id, start_position, end_position, status, priority, duration)\n" +
                            "    values ((select max(event_id) from event), '" + e.getStartPosition() + "', '" + e.getEndPosition() + "', 0, " + e.getPriority() + ", " + e.getDuration() + ");";
                    int rsSub = stmt.executeUpdate(sql);
                    if (rsSub != 1) {
                        return new Result("Error occured on add Event Route.", null);
                    }
                }
                sql = "insert into event_member(event_id, user_id) values((select max(event_id) from event),"+event.getOwnerId()+");";
                int rsSub = stmt.executeUpdate(sql);
                if (rsSub != 1) {
                    return new Result("Error occured on add Event Member.", null);
                }
                return new Result("", null);
            } else
                return new Result("Error occured on add Event.", null);
        } catch (Exception e) {
            return new Result(e.getMessage(), null);
        }
    }

    /*Added by Enkhee, get event list*/
    public JSONObject[] eventList(int type) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONObject[] objectToReturn = new JSONObject[1];
        try {
            preparedStatement = conn.prepareStatement("select * from Event where status = ? order by created desc");
            preparedStatement.setInt(1, type);
            resultSet = preparedStatement.executeQuery();
            List<Event> events = new ArrayList();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventID(resultSet.getInt("event_id"));
                event.setName(resultSet.getString("name"));
                event.setStatus(resultSet.getInt("status"));
                event.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
                event.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
                event.setOwnerId(resultSet.getInt("owner_id"));
                event.setImg(resultSet.getString("route_img"));
                event.seteFlag(resultSet.getInt("emergency_flag"));
                event.seteInfo(resultSet.getString("emergency_info"));
                event.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
                events.add(event);
            }

            Event[] data = events.stream().toArray(Event[]::new);

            if (data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
                for (int i = 0; i < data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("eventId", data[i].getEventID());
                    res.put("name", data[i].getName());
                    res.put("status", data[i].getStatus());
                    res.put("startDate", data[i].getStartDate().format(formatter));
                    res.put("endDate", data[i].getEndDate().format(formatter));
                    res.put("ownerId", data[i].getOwnerId());
                    res.put("img", data[i].getImg());
                    res.put("emergencyFlag", data[i].geteFlag());
                    res.put("emergencyInfo", data[i].geteInfo());
                    res.put("created", data[i].getCreated().format(formatter));
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

    /*Added by deegii, show event description*/
    public JSONObject getEvent(int id, int userId) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONObject obj = new JSONObject();

        try {
            preparedStatement = conn.prepareStatement("select e.*, case when m.event_id = null then 0 else 1 end is_Join from Event e " +
                    "left join (select event_id  from event_member where event_id=? and user_id=?) m " +
                    "on e.event_id = m.event_id " +
                    "where e.event_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, id);
            resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
            while (resultSet.next()) {
                obj.put("eventId", resultSet.getInt("event_id"));
                obj.put("name", resultSet.getString("name"));
                obj.put("status", resultSet.getInt("status"));
                obj.put("startDate", resultSet.getTimestamp("start_date").toLocalDateTime().format(formatter));
                obj.put("endDate", resultSet.getTimestamp("end_date").toLocalDateTime().format(formatter));
                obj.put("ownerId", resultSet.getInt("owner_id"));
                obj.put("img", resultSet.getString("route_img"));
                obj.put("emergencyFlag", resultSet.getString("emergency_Flag"));
                obj.put("emergencyInfo", resultSet.getString("emergency_info"));
                obj.put("created", resultSet.getTimestamp("created").toLocalDateTime().format(formatter));
                obj.put("myEvent", userId == resultSet.getInt("owner_id") ? 1 : 0);
                obj.put("isJoin", resultSet.getInt("is_Join"));
            }

            return obj;

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
        return obj;
    }

    //Added by Delgersaikhan - Start
    public JSONObject[] memberList() {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        JSONObject[] objectToReturn = new JSONObject[1];
        try {
            sql = "select * from cycling_club.user";
            rs = stmt.executeQuery(sql);
            List<User> users = new ArrayList();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setSex(rs.getString("sex"));
                user.setBirthday(rs.getString("birth_date"));
                users.add(user);
            }

            User[] data = users.stream().toArray(User[]::new);

            if (data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                for (int i = 0; i < data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("userId", data[i].getId());
                    res.put("name", data[i].getName());
                    res.put("sex", data[i].getSex());
                    res.put("email", data[i].getEmail());
                    res.put("phone", data[i].getPhone());
                    res.put("birthday", data[i].getBirthday());
                    results[i] = res;
                }

                return results;
            } else {

                JSONObject res = new JSONObject();
                res.put("error", "No results found");
                objectToReturn[0] = res;
                return objectToReturn;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
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
    //Added by Delgersaikhan - Start

    /*Added by Enkhee, get event routes*/
    public JSONObject[] eventRoutes(int eventId, Boolean isActive) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONObject[] objectToReturn = new JSONObject[1];
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

            EventRoute[] data = routes.stream().toArray(EventRoute[]::new);

            if (data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                for (int i = 0; i < data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("eventId", data[i].getEventID());
                    res.put("startPosition", data[i].getStartPosition());
                    res.put("endPosition", data[i].getEndPosition());
                    res.put("status", data[i].getStatus());
                    res.put("priority", data[i].getPriority());
                    res.put("duration", data[i].getDuration());
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

    /*Added by Enkhee, start Event*/
    public void startEvent(int eventId) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("update event set status=1, start_date=sysdate(), " +
                    "end_date=date_add(sysdate(), interval (select sum(duration) from event_route where event_id=? group by event_id) minute) " +
                    "where event_id=?");
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, eventId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*Added by Enkhee, join Event*/
    public void joinEvent(int eventId, long userId) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("insert into event_member(event_id, user_id) values(?,?)");
            preparedStatement.setInt(1, eventId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*Added by Enkhee, finish Route*/
    public void finishRoute(int eventId, int priority) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("update event_route set status=1 where event_id=? and priority=?");
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, priority);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*Added by Enkhee, get emergency flags*/
    public JSONObject[] getEFlags() {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONObject[] objectToReturn = new JSONObject[1];
        try {
            preparedStatement = conn.prepareStatement("select " +
                    "  r.event_id, " +
                    "  CONCAT(start_position, ' -> ', end_position) position, " +
                    "  u.members, e.emergency_info, e.name  " +
                    "from event_route r left join event e on r.event_id = e.event_id and r.priority = e.emergency_flag " +
                    "  left join (select " +
                    "               m.event_id, " +
                    "               GROUP_CONCAT(u.name SEPARATOR ', ') members " +
                    "             from " +
                    "               event_member m, " +
                    "               user u " +
                    "             where " +
                    "               m.user_id = u.user_id " +
                    "             group by " +
                    "               m.event_id) u on r.event_id = u.event_id " +
                    "where e.emergency_flag > 0");
            resultSet = preparedStatement.executeQuery();
            List<EFlag> flags = new ArrayList();
            while (resultSet.next()) {
                EFlag flag = new EFlag();
                flag.setEventID(resultSet.getInt("event_id"));
                flag.setPostion(resultSet.getString("position"));
                flag.setMembers(resultSet.getString("members"));
                flag.setInfo(resultSet.getString("name"));
                flags.add(flag);
            }

            EFlag[] data = flags.stream().toArray(EFlag[]::new);

            if (data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                for (int i = 0; i < data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("eventId", data[i].getEventID());
                    res.put("position", data[i].getPostion());
                    res.put("members", data[i].getMembers());
                    res.put("info", data[i].getInfo());
                    results[i] = res;
                }
                return results;
            }

            return null;

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

    /*Added by Enkhee, get event members*/
    public JSONObject[] eventMembers(int eventId) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONObject[] objectToReturn = new JSONObject[1];
        try {
            preparedStatement = conn.prepareStatement("select e.event_id, e.user_id, u.name from Event_member e left join user u on e.user_id=u.user_id where e.event_id = ?");
            preparedStatement.setInt(1, eventId);
            resultSet = preparedStatement.executeQuery();
            List<EventMember> members = new ArrayList();
            while (resultSet.next()) {
                EventMember member = new EventMember();
                member.setEventID(resultSet.getInt("event_id"));
                member.setUserId(resultSet.getInt("user_id"));
                member.setUserName(resultSet.getString("name"));
                members.add(member);
            }

            EventMember[] data = members.stream().toArray(EventMember[]::new);

            if (data.length > 0) {
                JSONObject[] results = new JSONObject[data.length];
                for (int i = 0; i < data.length; i++) {
                    JSONObject res = new JSONObject();
                    res.put("eventId", data[i].getEventID());
                    res.put("userId", data[i].getUserId());
                    res.put("userName", data[i].getUserName());
                    results[i] = res;
                }
                return results;
            }

            return null;

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

    /*Added by Enkhee, create emergency flags*/
    public void updateEFlag(int eventId, int routePriority, String info) {
        conn = connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("update event set emergency_flag=?, emergency_info=? where event_id=?");
            preparedStatement.setInt(1, routePriority);
            preparedStatement.setString(2, info);
            preparedStatement.setInt(3, eventId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Result login(User user) {
        conn = connectDB();
        sql = "select * from User where email= ? ";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, user.getEmail());
            ResultSet rs = st.executeQuery();

            User res = new User();
            while (rs.next()) {
                res.setId(rs.getLong("User_id"));
                res.setName(rs.getString("name"));
                res.setEmail(rs.getString("email"));
                res.setPassword(rs.getString("password"));
                res.setPhone(rs.getString("phone"));
                res.setSex(rs.getString("sex"));
                res.setBirthday(rs.getString("birth_date"));

                String pwd = rs.getString("password");
                String pwdSha256 = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();

                if (pwd.equals(pwdSha256)) {
                    return new Result("", res);
                } else
                    return new Result("Invalid Username or password", null);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Result("Invalid Username or password", null);
        }
        return new Result("Invalid Username or password", null);
    }

    public Result addUser(User user) {
        String existCheckSQL = "select name from User where email= ?";

        sql = "insert into user (name, email, password, phone, sex, birth_date, created)" +
                " values (?,?,?,?,?,?, now())";

        try {
            PreparedStatement st = conn.prepareStatement(existCheckSQL);
            st.setString(1, user.getEmail());
            ResultSet result = st.executeQuery();

            if (result.next()) {
                return new Result("This email address already exists!", null);
            }

            PreparedStatement stat = conn.prepareStatement(sql);
            String pwdSha256 = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();
            stat.setString(1, user.getName());
            stat.setString(2, user.getEmail());
            stat.setString(3, pwdSha256);
            stat.setString(4, user.getPhone());
            stat.setString(5, user.getSex());
            stat.setString(6, user.getBirthday());

            int rs = stat.executeUpdate();
            if (rs == 1) {
                return new Result("Succeed sign up!", new String("Succeed sign up!"));
            }
        } catch (Exception e) {
            return new Result(e.getMessage(), null);
        }
        return null;
    }
}
