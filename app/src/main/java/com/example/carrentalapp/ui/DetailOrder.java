package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
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

import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    txtCongSuat,
            txtSoGhe,
            txtNhienLieu,

    thanhtoan;

    String madon="";
    String hoten="";
    String sodienthoai="";


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

        txtCongSuat =
                findViewById(R.id.congxuat);

        txtSoGhe =
                findViewById(R.id.soghe);

        txtNhienLieu =
                findViewById(R.id.nhienlieu);


        NumberFormat nf = NumberFormat.getInstance(new Locale("vi","VN"));

       String madonIntent = getIntent().getStringExtra("madon");
//


        ApiService.getdetailorder(
                madonIntent,
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(
                                DetailOrder.this,
                                "Lỗi mạng",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

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

                                String thanhtoantext =
                                        object.getString("thanhtoan");

                                String tinhtrang =
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
                                                + thanhtoantext
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
                                        DetailOrder.this,
                                        "Lỗi server",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                }

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
                Intent intent = new Intent(DetailOrder.this, Payment.class);
                intent.putExtra("madon",madon);
                intent.putExtra("hoten",hoten);
                intent.putExtra("sodienthoai",sodienthoai);
                startActivity(intent);
            }
        });
    }



}