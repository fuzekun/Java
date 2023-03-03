package com.example.demo.Eception;

public class BaseException extends Exception{
    String msg;
    public BaseException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
