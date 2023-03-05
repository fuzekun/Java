package com.example.demo.aync;


public enum EventType {
    Like(0),
    LOGIN(1),
    COMMENT(2);

    private int value;
    EventType(int value){this.value = value;}

    int getValue(){
        return this.value;
    }
}
