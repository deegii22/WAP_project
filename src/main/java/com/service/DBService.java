package com.service;

import com.model.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                "values ('"+event.getName()+"', 0, STR_TO_DATE('25-04-2018', '%d-%m-%Y'), STR_TO_DATE('26-04-2018', '%d-%m-%Y'), '', 1, 0, '', sysdate());";

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
