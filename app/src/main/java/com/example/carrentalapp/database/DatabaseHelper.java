package com.example.carrentalapp.database;


import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carrentalapp.model.Order;
import com.example.carrentalapp.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CarRentalApp.db";
    public static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsers = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "password TEXT)";
        db.execSQL(sqlUsers);

        // Tạo bảng lưu thông tin xe đã thuê
        String sqlDatxe = "CREATE TABLE datxe (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "madon TEXT, " +
                "bienso TEXT, " +
                "hoten TEXT, " +
                "sodienthoai TEXT, " +
                "ngaydat TEXT, " +
                "ngaylay TEXT, " +
                "songaythue INTEGER, " +
                "dongia INTEGER, " +
                "thanhtien INTEGER, " +
                "thanhtoan TEXT, " +
                "tinhtrang TEXT, " +
                "ghichu TEXT)";
        db.execSQL(sqlDatxe);


        // luu thong tin ve xe
        String sqlDatabaseXe = "CREATE TABLE dulieuxe (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hinhxe1 TEXT, " +
                "hinhxe2 TEXT, " +
                "tenxe TEXT, " +
                "bienso TEXT, " +
                "dongia INTEGER, " +
                "mota TEXT) ";

        db.execSQL(sqlDatabaseXe);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS datxe");
        db.execSQL("DROP TABLE IF EXISTS dulieuxe");
        onCreate(db);
    }

    // thêm user
    public boolean insertUser(String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1;
    }

    // hàm thêm dữ liệu đặt xe
    public boolean inserdatxe(String madon , String bienso,
                              String hoten, String sdt,  String ngaydat, String ngaylay,
                              int songaythue, int dongia, int thanhtien, String thanhtoan,
                              String tinhtrang, String ghichu) {

        SQLiteDatabase db = this.getWritableDatabase(); // lấy databsae ở chế độ ghi :cho phép
        ContentValues values = new ContentValues(); // Tạo đối tượng chứa dữ liệu: <tên cột> <giá trị>

        // Gán dữ liệu vao cột
        values.put("madon", madon);
        values.put("bienso", bienso);
        values.put("hoten", hoten);
        values.put("sodienthoai", sdt);
        values.put("ngaydat", ngaydat);
        values.put("ngaylay", ngaylay);
        values.put("songaythue", songaythue);
        values.put("dongia", dongia);
        values.put("thanhtien", thanhtien);
        values.put("thanhtoan", thanhtoan);
        values.put("tinhtrang", tinhtrang);
        values.put("ghichu", ghichu);

        // Thực hiện insert vào bảng:
        long result = db.insert("datxe", null, values);

        return result != -1;
    }


    // ham them du lieu databasexe
    public boolean inserdatabasexe(String hinhxe1, String hinhxe2, String tenxe, String bienso, int dongia, String mota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // gan du lieu
        values.put("hinhxe1", hinhxe1);
        values.put("hinhxe2", hinhxe2);
        values.put("tenxe", tenxe);
        values.put("bienso", bienso);
        values.put("dongia", dongia);
        values.put("mota", mota);
        long result = db.insert("dulieuxe", null, values);
        return result != -1;

    }


    // kiểm tra login
    public boolean checkUser(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE phone=? AND password=?",
                new String[]{phone, password}
        );
        return cursor.getCount() > 0;
    }

    // lấy tên
    public String getName(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name FROM users WHERE phone=? AND password=?",
                new String[]{phone, password}
        );

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return "";
    }


    //ham lay list danh sach xe da dat
    // Khai bao ham
    // public : co the goi tu class khac
    // Arraylist<datxe> : tra ve danh sach doi tuong datxe
    // getalldatxe: ten ham lay du lieu datxe
    public ArrayList<Order> getalldatxe() {
        //tao mot Array rong de chua du lieu lay tu database
        ArrayList<Order> list = new ArrayList<>();
        // mo databse de doc du lieu
        SQLiteDatabase db = this.getReadableDatabase();
        // Cau lenh thuc thi SQL
        //dung rawQuery de chay Sql  , NUll la khong co tham so truyen vao
        // ket qua se tra ve sursor < con tro tro den dong du lieu >
        Cursor cursor = db.rawQuery("SELECT * FROM datxe", null);

        //Duyet tung dong du lieu
        // motonext la duyet den dong tiep theo , tra ve true neu con du lieu

        while (cursor.moveToNext()) {
            list.add(new Order(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12)


            ));
        }
        return list;
    }

    // lay thong tin xe

    public ArrayList<Vehicle> laythongtinxe() {
        ArrayList<Vehicle> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM dulieuxe", null);
        while (cursor.moveToNext()) {
            list.add(new Vehicle(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6)


            ));
        }
        cursor.close();
        return list;
    }

    public boolean suathongtinxe(int stt, String hinh1, String hinh2, String ten, String bienso, int gia, String mota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //gan gia tri moi
        values.put("hinh1", hinh1);
        values.put("hinh2", hinh2);
        values.put("ten", ten);
        values.put("bienso", bienso);
        values.put("gia", gia);
        values.put("mota", mota);

        int result = db.update("dulieuxe", values, "id=?", new String[]{String.valueOf(stt)});

        return result > 0;
    }

    public boolean xoathongtinxe(int stt) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("dulieuxe", "id=?", new String[]{String.valueOf(stt)});
        return result > 0;

    }


    //hàm tạo mã đơn tự động
    public String taomadon(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(CAST(madon AS INTEGER)) FROM datxe",null);

        int madon = 690;
        if (cursor.moveToFirst())
        {
            madon = cursor.getInt(0) +1;

        }
        cursor.close();
        return String.valueOf(madon);


    }




}















