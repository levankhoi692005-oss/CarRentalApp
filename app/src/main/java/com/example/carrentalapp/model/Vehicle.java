package com.example.carrentalapp.model;

public class Vehicle {

    private int stt;
    private String hinh1;
    private String hinh2;
    private String ten;
    private int gia;
    private String bienso;
    private String mota;

    private String mau;
    private int soghe;
    private int namsanxuat;
    private String hopso;
    private String congsuat;
    private String nhienlieu;

    public Vehicle(int stt,String hinh1, String hinh2, String ten, String bienso,int gia, String mota
    ,String mau,int soghe, int namsanxuat, String hopso, String congsuat, String nhienlieu) {
        this.stt =stt;
        this.hinh1 = hinh1;
        this.hinh2 = hinh2;
        this.ten = ten;
        this.gia = gia;
        this.bienso =bienso;
        this.mota = mota;
        this.mau = mau;
        this.soghe = soghe;
        this.namsanxuat = namsanxuat;
        this.hopso = hopso;
        this.congsuat = congsuat;
        this.nhienlieu = nhienlieu;
    }

    public int getStt() {return stt;}
    public String getHinh1() { return hinh1; }
    public String getHinh2(){ return hinh2; }
    public String getTen() { return ten; }
    public int getGia() { return gia; }
    public String getBienso() {return bienso; }
    public String getMota() { return mota; }
    public String getMau() {return mau;}

    public int getSoghe() {return soghe;}

    public int getNamsanxuat() {return namsanxuat;}

    public String getHopso() {return hopso;}

    public String getCongsuat() {return congsuat;}

    public String getNhienlieu () {return nhienlieu;}
}
