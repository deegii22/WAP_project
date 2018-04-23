package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginCheck{
    private List<User> users = new ArrayList<User>();

    public boolean checkUser(User user){
        LoginCheck check = new LoginCheck();
        check.getUserInfoFromDB();

        for(User u : check.users){
            if(u.equals(user)) return true;
        }

        return false;
/*
        int pos = users.indexOf(user);
        if(pos != -1){
            user = users.get(pos);
            return true;
        }
*/
    }

    public void getUserInfoFromDB(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        String dir = "C:\\Users\\deegi\\Documents\\WAP\\01.PROJECT\\src\\main\\recources\\";
        String filename = "users.txt";

        try
        {
            FileReader reader = new FileReader(dir + filename);
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray userList = (JSONArray) obj;
            System.out.println(userList);

            //Iterate over user array
            userList.forEach( user -> parseUserObject( (JSONObject) user ));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void parseUserObject(JSONObject jsonUser)
    {
        //Get user object within list
        JSONObject userObject = (JSONObject) jsonUser.get("user");
        User user = new User();

        //Get user name
        Long id = (Long) userObject.get("id");
        String name = (String) userObject.get("name");
        String sex = (String) userObject.get("sex");
        String email = (String) userObject.get("email");
        String phone = (String) userObject.get("phone");
        String birthday = (String) userObject.get("birthday");
        String password = (String) userObject.get("password");

        user.setId(id);
        user.setName(name);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBirthday(birthday);
        user.setPassword(password);

        users.add(user);
    }
}
