package com.service;

public class Result {
    String desc;
    public String getDesc() {
        return desc;
    }

    public Object getObj() {
        return obj;
    }

    Object obj;

    public Result(String desc, Object obj) {
        this.desc = desc;
        this.obj = obj;
    }
}

