package com.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String sex;
    private String email;
    private String phone;
    private String birthday;
    private String password;

    public User(){}

    public User(Long id, String name, String sex, String email, String phone, String birthday, String password) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int base = 23;
        base += email.hashCode() * 19;
        base += password.hashCode() * 19;
        return base;
    }
}
