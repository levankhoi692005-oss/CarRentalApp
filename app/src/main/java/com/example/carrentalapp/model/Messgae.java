package com.example.carrentalapp.model;

public class Messgae {
    String name,message,date_time;

    public Messgae (String name, String message,String date_time)
    {
        this.message= message;
        this.name = name;
        this.date_time = date_time;
    }

    public String getName(){return  name;}
    public String getMessage(){return message;}
    public String getDate_time(){return  date_time;}

}
