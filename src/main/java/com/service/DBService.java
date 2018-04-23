package com.service;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {

        static Connection conn = null;
        static Statement stmt = null;
        String sql;
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);


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

        public Result addEmpData(Employee employee) {

            sql = "INSERT INTO Employee VALUES ('" + employee.getID() + "','" + employee.getFirstName() + "','"
                    + employee.getLastName() + "','" + employee.getPhone() + "','" + employee.getEmail() + "','"
                    + employee.getRole() + "','" + employee.getManagerID() + "','" + employee.getEmpPass() + "','"
                    + employee.getEmpStatus() + "')";

            try {

                int rs = stmt.executeUpdate(sql);
                if (rs == 1) {
                    return new Result("Add Employee Successful", null);
                }
            } catch (Exception e) {
                return new Result(e.getMessage(), null);
            }
            return null;
        }
        */
}
