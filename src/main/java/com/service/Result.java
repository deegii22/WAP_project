package com.service;

public class Result {
    String desc;
    Object obj;

    public String getDesc() {
        return desc;
    }

    public Object getObj() {
        return obj;
    }

    public Result(String desc, Object obj) {
        this.desc = desc;
        this.obj = obj;
    }
}

