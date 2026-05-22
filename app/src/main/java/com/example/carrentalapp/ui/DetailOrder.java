package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalapp.R;

public class DetailOrder extends AppCompatActivity {
    ImageView btnBack;

    TextView txtMaDon,
            txtTenKh,
            txtSdtKh,
            txtDiaChiKh,

    txtTenXe,
            txtHangXe,
            txtBienSo,
            txtNamSX,
            txtMauXe,
            txtHopSo,
            txtGia,

    txtNgayDangKy,
            txtNgayLay,

            txtsongaythue,
            txtDiaChiLay,

            txtghichu,

    thongtin,

    txtTongTien,
            txtLoaiThanhToan,
            txtTinhTrang,
    thanhtoan;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_order);

        btnBack =
                findViewById(R.id.btnBack);

        txtMaDon =
                findViewById(R.id.txtmadon);

        txtTenKh =
                findViewById(R.id.txttenkh);

        txtSdtKh =
                findViewById(R.id.txtsdtkh);

        txtDiaChiKh =
                findViewById(R.id.txtdiachikh);

        txtTenXe =
                findViewById(R.id.tenxe);

        txtHangXe =
                findViewById(R.id.hangxe);

        txtBienSo =
                findViewById(R.id.bienso);

        txtNamSX =
                findViewById(R.id.namsx);

        txtMauXe =
                findViewById(R.id.mauxe);

        txtHopSo =
                findViewById(R.id.hopso);

        txtGia =
                findViewById(R.id.gia);

        txtNgayDangKy =
                findViewById(R.id.ngaydangky);

        txtNgayLay =
                findViewById(R.id.ngaylay);

        txtsongaythue =
                findViewById(R.id.songaythue);

        txtDiaChiLay =
                findViewById(R.id.diachilay);

        txtTongTien =
                findViewById(R.id.tongtien);

        txtLoaiThanhToan =
                findViewById(R.id.loaithanhtoan);

        txtTinhTrang =
                findViewById(R.id.tinhtrang);

        txtghichu =
                findViewById(R.id.txtghichu);

        thongtin =
                findViewById(R.id.thongtin);

        thanhtoan =
                findViewById(R.id.thanhtoan);



        String getMadon = getIntent().getStringExtra("getMadon");
        String getHoten = getIntent().getStringExtra("getHoten");
        String getSodienthoai = getIntent().getStringExtra("getSodienthoai");
        String getNgaydat = getIntent().getStringExtra("getNgaydat");
        String getNgaylay = getIntent().getStringExtra("getNgaylay");
        String getGhichu = getIntent().getStringExtra("getGhichu");
        String getThanhtoan = getIntent().getStringExtra("getThanhtoan");
        String getTinhtrang = getIntent().getStringExtra("getTinhtrang");
        String getTenxe = getIntent().getStringExtra("getTenxe");
        String getDiachikh = getIntent().getStringExtra("getDiachikh");
        String getDiachinhan = getIntent().getStringExtra("getDiachinhan");
        String getBienso = getIntent().getStringExtra("getBienso");

        int getSongaythue = getIntent().getIntExtra("getSongaythue",-1);
        int getDongia = getIntent().getIntExtra("getDongia",-1);
        int getThanhtien = getIntent().getIntExtra("getThanhtien",-1);
        int getUser_id = getIntent().getIntExtra("getUser_id",-1);


        thongtin.setText(" Thông tin đơn "+getMadon);

        txtMaDon.setText(
                "Mã đơn: " + getMadon
        );

        txtTenKh.setText(
                "Họ và tên: " + getHoten
        );

        txtSdtKh.setText(
                "Số điện thoại: " + getSodienthoai
        );

        txtDiaChiKh.setText(
                "Địa chỉ: " + getDiachikh
        );

        txtTenXe.setText(
                getTenxe
        );

        txtBienSo.setText(
                "Biển số: " + getBienso
        );

        txtNgayDangKy.setText(
                "Ngày đăng ký: " + getNgaydat
        );

        txtNgayLay.setText(
                "Ngày lấy xe: " + getNgaylay
        );

        txtsongaythue.setText(
                "Số ngày thuê: "
                        + getSongaythue
                        + " ngày"
        );

        txtGia.setText(
                "Đơn giá: "
                        + getDongia
                        + "đ/ngày"
        );

        txtTongTien.setText(
                "Tổng tiền: "
                        + getThanhtien
                        + "đ"
        );

        txtLoaiThanhToan.setText(
                "Phương thức: "
                        + getThanhtoan
        );

        txtTinhTrang.setText(
                "Tình trạng: "
                        + getTinhtrang
        );

        txtDiaChiLay.setText(
                "Địa chỉ nhận: "
                        + getDiachinhan
        );

        txtghichu.setText(
                "Ghi chú: "
                        + getGhichu
        );



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailOrder.this,Cart.class));
            }
        });


        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }



}