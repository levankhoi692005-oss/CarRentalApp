package com.example.carrentalapp.model;

public class Vehicle {

    private int stt;
    private String hinh1;
    private String hinh2;
    private String ten;
    private int gia;
    private String bienso;
    private String mota;

    public Vehicle(int stt,String hinh1, String hinh2, String ten, String bienso,int gia, String mota) {
        this.stt =stt;
        this.hinh1 = hinh1;
        this.hinh2 = hinh2;
        this.ten = ten;
        this.gia = gia;
        this.bienso =bienso;
        this.mota = mota;
    }

    public int getStt() {return stt;}
    public String getHinh1() { return hinh1; }
    public String getHinh2(){ return hinh2; }
    public String getTen() { return ten; }
    public int getGia() { return gia; }
    public String getBienso() {return bienso; }
    public String getMota() { return mota; }
}
