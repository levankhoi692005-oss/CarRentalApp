package com.example.carrentalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;

public class DetailVehicle extends AppCompatActivity {

    ImageView img1,img2;

    TextView txtTen,
            txtGia,
            txtBienSo,
            txtMoTa,
            txtMau,
            txtSoGhe,
            txtNhienLieu,
            txtHopSo,
            txtCongSuat,
            txtNamSX,
            txtCongTy;

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_vehicle);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);

        txtTen = findViewById(R.id.txtTenXe);
        txtGia = findViewById(R.id.txtGia);
        txtBienSo = findViewById(R.id.txtBienSo);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtMau = findViewById(R.id.txtMau);
        txtSoGhe = findViewById(R.id.txtSoGhe);
        txtNhienLieu = findViewById(R.id.txtNhienLieu);
        txtHopSo = findViewById(R.id.txtHopSo);
        txtCongSuat = findViewById(R.id.txtCongSuat);
        txtNamSX = findViewById(R.id.txtNamSX);
        txtCongTy = findViewById(R.id.txtCongTy);

        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();

        String anh1 = intent.getStringExtra("anh1");
        String anh2 = intent.getStringExtra("anh2");

        Glide.with(this)
                .load(anh1)
                .into(img1);

        Glide.with(this)
                .load(anh2)
                .into(img2);

        txtTen.setText(intent.getStringExtra("tenxe"));

        txtGia.setText(
                "Giá: "
                        + intent.getIntExtra("gia",0)
                        + " VNĐ"
        );

        txtBienSo.setText(
                "Biển số: "
                        + intent.getStringExtra("bienso")
        );

        txtMoTa.setText(
                "Mô tả: "
                        + intent.getStringExtra("mota")
        );

        txtMau.setText(
                "Màu: "
                        + intent.getStringExtra("mau")
        );

        txtSoGhe.setText(
                "Số ghế: "
                        + intent.getIntExtra("soghe",0)
        );

        txtNhienLieu.setText(
                "Nhiên liệu: "
                        + intent.getStringExtra("nhienlieu")
        );

        txtHopSo.setText(
                "Hộp số: "
                        + intent.getStringExtra("hopso")
        );

        txtCongSuat.setText(
                "Công suất: "
                        + intent.getStringExtra("congsuat")
        );

        txtNamSX.setText(
                "Năm SX: "
                        + intent.getIntExtra("namsanxuat",0)
        );

        txtCongTy.setText(
                "Công ty quản lý: Công ty CP thuê xe MMT01"
        );

        btnBack.setOnClickListener(v -> {

            startActivity(new Intent(DetailVehicle.this,Home.class));

        });
    }
}