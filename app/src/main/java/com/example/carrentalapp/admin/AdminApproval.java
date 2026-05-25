package com.example.carrentalapp.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.ui.Cart;
import com.example.carrentalapp.ui.DetailOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdminApproval extends AppCompatActivity {


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

    txtCongSuat,
            txtSoGhe,
            txtNhienLieu,


    xacnhantt,
    duyetdon,
            tuchoi;
    ImageView img1,img2;

    String madon="";
    String hoten="";
    String sodienthoai="";

    String tinhtrang="";
    String thanhtoan="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_approval_order_adapter);


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



        txtCongSuat =
                findViewById(R.id.congxuat);

        txtSoGhe =
                findViewById(R.id.soghe);

        txtNhienLieu =
                findViewById(R.id.nhienlieu);


        xacnhantt =
                findViewById(R.id.xacnhantt);
        duyetdon =
                findViewById(R.id.duyetdon);
        tuchoi =
                findViewById(R.id.tuchoi);
        img1 =
                findViewById(R.id.img1);
        img2 =
                findViewById(R.id.img2);



        NumberFormat nf = NumberFormat.getInstance(new Locale("vi","VN"));

        String madonIntent = getIntent().getStringExtra("madon");


        ApiService.getdetailorder(
                madonIntent,
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(
                                AdminApproval.this,
                                "Lỗi mạng",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

                        runOnUiThread(()->
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Boolean success = jsonObject.getBoolean("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject object = jsonArray.getJSONObject(0);

                                if(success) {

                                    madon =
                                            object.getString("madon");

                                    String bienso =
                                            object.getString("bienso");

                                    hoten =
                                            object.getString("hoten");

                                    sodienthoai =
                                            object.getString("sodienthoai");

                                    String ngaydat =
                                            object.getString("ngaydat");

                                    String ngaylay =
                                            object.getString("ngaylay");

                                    int songaythue =
                                            object.getInt("songaythue");

                                    int dongia =
                                            object.getInt("dongia");

                                    int thanhtien =
                                            object.getInt("thanhtien");

                                    thanhtoan =
                                            object.getString("thanhtoan");

                                    tinhtrang =
                                            object.getString("tinhtrang");

                                    String ghichu =
                                            object.getString("ghichu");

                                    int user_id =
                                            object.getInt("user_id");

                                    String tenxe =
                                            object.getString("tenxe");

                                    String diachikh =
                                            object.getString("diachikh");

                                    String diachinhan =
                                            object.getString("diachinhan");

                                    String hinhxe1 =
                                            object.getString("hinhxe1");

                                    String hinhxe2 =
                                            object.getString("hinhxe2");

                                    String mota =
                                            object.getString("mota");

                                    String mau =
                                            object.getString("mau");

                                    int soghe =
                                            object.getInt("soghe");

                                    int namsanxuat =
                                            object.getInt("namsanxuat");

                                    String hopso =
                                            object.getString("hopso");

                                    String congsuat =
                                            object.getString("congsuat");

                                    String nhienlieu =
                                            object.getString("nhienlieu");

                                    Glide.with(img1.getContext()).load(hinhxe1).into(img1);
                                    Glide.with(img2.getContext()).load(hinhxe2).into(img2);


                                    thongtin.setText(
                                            "Thông tin đơn " + madon
                                    );

                                    txtMaDon.setText(
                                            "Mã đơn: " + madon
                                    );

                                    txtTenKh.setText(
                                            "Họ và tên: " + hoten
                                    );

                                    txtSdtKh.setText(
                                            "Số điện thoại: " + sodienthoai
                                    );

                                    txtDiaChiKh.setText(
                                            "Địa chỉ: " + diachikh
                                    );

                                    txtTenXe.setText(
                                            tenxe
                                    );

                                    txtBienSo.setText(
                                            "Biển số: " + bienso
                                    );

                                    txtNamSX.setText(
                                            "Năm sản xuất: " + namsanxuat
                                    );

                                    txtMauXe.setText(
                                            "Màu xe: " + mau
                                    );

                                    txtHopSo.setText(
                                            "Hộp số: " + hopso
                                    );

                                    txtCongSuat.setText(
                                            "Công suất: " + congsuat
                                    );

                                    txtSoGhe.setText(
                                            "Số ghế: " + soghe
                                    );

                                    txtNhienLieu.setText(
                                            "Nhiên liệu: " + nhienlieu
                                    );

                                    txtGia.setText(
                                            "Đơn giá: "
                                                    + nf.format(dongia)
                                                    + " VNĐ/ngày"
                                    );

                                    txtNgayDangKy.setText(
                                            "Ngày đăng ký: " + ngaydat
                                    );

                                    txtNgayLay.setText(
                                            "Ngày lấy xe: " + ngaylay
                                    );

                                    txtsongaythue.setText(
                                            "Số ngày thuê: "
                                                    + songaythue
                                                    + " ngày"
                                    );

                                    txtDiaChiLay.setText(
                                            "Địa chỉ nhận: "
                                                    + diachinhan
                                    );

                                    txtTongTien.setText(
                                            "Tổng tiền: "
                                                    + nf.format(thanhtien)
                                                    + " VNĐ"
                                    );

                                    txtLoaiThanhToan.setText(
                                            "Phương thức: "
                                                    + thanhtoan
                                    );

                                    txtTinhTrang.setText(
                                            "Tình trạng: "
                                                    + tinhtrang
                                    );

                                    txtghichu.setText(
                                            "Ghi chú: "
                                                    + ghichu
                                    );
                                }
                                else
                                {
                                    Toast.makeText(
                                            AdminApproval.this,
                                            "Lỗi server",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        });



                    }
                }

        );

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminApproval.this, AdminViewOrder.class));
            }
        });


        xacnhantt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ApiService.UpdatOrder(
                        madon,
                        tinhtrang,
                        "Đã thanh toán",
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Toast.makeText(
                                        AdminApproval.this,
                                        "Lỗi mạng",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                String result = response.body().string();
                                runOnUiThread(()->{
                                    try {
                                        JSONObject object = new JSONObject(result);

                                        boolean success = object.getBoolean("success");
                                        if(success) {
                                            txtLoaiThanhToan.setText("Đã thanh toán");

                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    "Đã chỉnh thanh toán",

                                                    Toast.LENGTH_SHORT

                                            ).show();
                                        }
                                        else {
                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    object.getString("message"),

                                                    Toast.LENGTH_SHORT

                                            ).show();

                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        });
            }
        });



        duyetdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ApiService.UpdatOrder(
                        madon,
                        "Đã duyệt",
                        thanhtoan,
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Toast.makeText(
                                        AdminApproval.this,
                                        "Lỗi mạng",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                String result = response.body().string();
                                runOnUiThread(()->{
                                    try {
                                        JSONObject object = new JSONObject(result);

                                        boolean success = object.getBoolean("success");
                                        if(success) {
                                            txtTinhTrang.setText("Đã duyệt");

                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    "Đã chỉnh duyệt đơn",

                                                    Toast.LENGTH_SHORT

                                            ).show();
                                        }
                                        else {
                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    object.getString("message"),

                                                    Toast.LENGTH_SHORT

                                            ).show();

                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                });


                            }

                        });
            }
        });




        tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ApiService.UpdatOrder(
                        madon,
                        "Đã từ chối",
                        thanhtoan,
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Toast.makeText(
                                        AdminApproval.this,
                                        "Lỗi mạng",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                String result = response.body().string();
                                runOnUiThread(()->{
                                    try {
                                        JSONObject object = new JSONObject(result);

                                        boolean success = object.getBoolean("success");
                                        if(success) {
                                            txtTinhTrang.setText("Đã từ chối");

                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    "Đã chỉnh duyệt đơn",

                                                    Toast.LENGTH_SHORT

                                            ).show();
                                        }
                                        else {
                                            Toast.makeText(

                                                    AdminApproval.this,

                                                    object.getString("message"),

                                                    Toast.LENGTH_SHORT

                                            ).show();

                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                });
                            }});

            }
        });


    }


}