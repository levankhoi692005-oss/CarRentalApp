package com.example.carrentalapp.model;

public class Order {

    String madon, hoten, bienso, sodienthoai, ngaydat, ngaylay, ghichu, thanhtoan, tinhtrang,tenxe
            ,diachikh,diachinhan;
    int id,songaythue, dongia, thanhtien,user_id;

    public Order(int id,String madon, String hoten, String bienso, String sodienthoai,
                 String ngaydat, String ngaylay,
                 int songaythue, int dongia, int thanhtien,
                 String thanhtoan, String tinhtrang, String ghichu,int user_id,String tenxe,
    String diachikh, String diachinhan) {

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
        this.user_id =user_id;
        this.id =id;
        this.tenxe = tenxe;
        this.diachikh =diachikh;
        this.diachinhan = diachinhan;
    }
    public int getId() {return id; }

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
    public int getUser_id() { return user_id; }
    public String getTenxe() { return tenxe; }

    public String getDiachikh() { return diachikh; }

    public String getDiachinhan() { return diachinhan; }
}