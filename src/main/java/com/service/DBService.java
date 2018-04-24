package com.service;

import com.model.Event;
import com.model.User;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBService {

    static Connection conn = null;
    static Statement stmt = null;
    String sql;

    public static Connection connectDB() {
        try {
            if(conn == null) conn = DBSingleton.getInstance().getConnection();
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

        sql = "insert into event values (1, 'event1', 0, STR_TO_DATE('1-01-2012', '%d-%m-%Y')," +
                "STR_TO_DATE('1-01-2012', '%d-%m-%Y'),'asdf',1,0," +
                "'test',STR_TO_DATE('1-01-2012', '%d-%m-%Y'));";

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

            if(result.next()){
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
