package com.example.carrentalapp.model;

public class Order {

    String madon, hoten, bienso, sodienthoai, ngaydat, ngaylay, ghichu, thanhtoan, tinhtrang;
    int songaythue, dongia, thanhtien;

    public Order(String madon, String hoten, String bienso, String sodienthoai,
                 String ngaydat, String ngaylay,
                 int songaythue, int dongia, int thanhtien,
                 String thanhtoan, String tinhtrang, String ghichu) {

        this.madon = madon;
        this.hoten = hoten;
        this.bienso = bienso;
        this.sodienthoai = sodienthoai;
        this.ngaydat = ngaydat;
        this.ngaylay = ngaylay;
        this.songaythue = songaythue;
        this.dongia = dongia;
        this.thanhtien = thanhtien;
        this.thanhtoan = thanhtoan;
        this.tinhtrang = tinhtrang;
        this.ghichu = ghichu;
    }

    public String getMadon() { return madon; }
    public String getHoten() { return hoten; }
    public String getBienso() { return bienso; }
    public String getSodienthoai() { return sodienthoai; }
    public String getNgaydat() { return ngaydat; }
    public String getNgaylay() { return ngaylay; }
    public String getGhichu() { return ghichu; }
    public String getThanhtoan() { return thanhtoan; }
    public String getTinhtrang() { return tinhtrang; }

    public int getSongaythue() { return songaythue; }
    public int getDongia() { return dongia; }
    public int getThanhtien() { return thanhtien; }
}