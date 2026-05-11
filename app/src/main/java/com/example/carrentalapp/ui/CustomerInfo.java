package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerInfo extends AppCompatActivity {

    ImageView imgXe, btnBack;

    TextView txtTenXe,
            txtBienSo,
            txtGia,
            txtNgayDat,
            txtTongTien;

    EditText edtHoTen,
            edtSDT,
            edtNgayLay,
            edtSoNgay,
            edtGhiChu;

    RadioGroup radioThanhToan;

    Button btnThue;

    OkHttpClient client =
            new OkHttpClient();

    // 🔥 IP WIFI MÁY
    String BASE_URL =
            "http://192.168.0.102:3000/";

    int gia = 0;

    int songaythue = 0;

    int thanhtien = 0;

    String ngaydat = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_customer_info);

        // ==========================
        // FINDVIEWBYID
        // ==========================

        imgXe =
                findViewById(R.id.imgXe);

        txtTenXe =
                findViewById(R.id.txtTenXe);

        txtBienSo =
                findViewById(R.id.txtBienSo);

        txtGia =
                findViewById(R.id.txtGia);

        txtNgayDat =
                findViewById(R.id.txtNgayDat);

        txtTongTien =
                findViewById(R.id.txtTongTien);

        edtHoTen =
                findViewById(R.id.edtHoTen);

        edtSDT =
                findViewById(R.id.edtSDT);

        edtNgayLay =
                findViewById(R.id.edtNgayLay);

        edtSoNgay =
                findViewById(R.id.edtSoNgay);

        edtGhiChu =
                findViewById(R.id.edtGhiChu);

        radioThanhToan =
                findViewById(R.id.radioThanhToan);

        btnThue =
                findViewById(R.id.btnThue);

        btnBack =
                findViewById(R.id.btnBack);

        // ==========================
        // GET INTENT
        // ==========================

        Intent intent =
                getIntent();

        String anhxe =
                intent.getStringExtra("anhxe");

        String tenxe =
                intent.getStringExtra("tenxe");

        String bienso =
                intent.getStringExtra("bienso");

        gia =
                intent.getIntExtra("gia",0);

        // ==========================
        // HIỂN THỊ XE
        // ==========================

        txtTenXe.setText(
                "Tên xe: " + tenxe
        );

        txtBienSo.setText(
                "Biển số: " + bienso
        );

        txtGia.setText(
                "Giá: " + gia + " VND"
        );

        // 🔥 CLOUDINARY
        Glide.with(this)
                .load(anhxe)
                .into(imgXe);

        // ==========================
        // NGÀY HÔM NAY
        // ==========================

        java.util.Calendar calendar =
                java.util.Calendar.getInstance();

        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                );

        ngaydat =
                sdf.format(new Date());

        txtNgayDat.setText(
                "Ngày đặt: " + ngaydat
        );

        // ==========================
        // TÍNH TIỀN
        // ==========================

        edtSoNgay.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {

                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {

                        if(!s.toString().isEmpty()){

                            songaythue =
                                    Integer.parseInt(
                                            s.toString()
                                    );

                            thanhtien =
                                    songaythue * gia;

                            txtTongTien.setText(

                                    "Tổng tiền: "
                                            + thanhtien
                                            + " VND"

                            );

                        }

                    }

                }

        );

        // ==========================
        // DATE PICKER
        // ==========================

        edtNgayLay.setOnClickListener(v -> {

            DatePickerDialog dp =
                    new DatePickerDialog(

                            this,

                            (view, year, month, day) -> {

                                String ngay =

                                        String.format(

                                                Locale.getDefault(),

                                                "%02d/%02d/%04d",

                                                day,

                                                month + 1,

                                                year

                                        );

                                edtNgayLay.setText(ngay);

                            },

                            calendar.get(
                                    java.util.Calendar.YEAR
                            ),

                            calendar.get(
                                    java.util.Calendar.MONTH
                            ),

                            calendar.get(
                                    java.util.Calendar.DAY_OF_MONTH
                            )

                    );

            dp.show();

        });

        // ==========================
        // THUÊ XE
        // ==========================

        btnThue.setOnClickListener(v -> {

            String hoten =
                    edtHoTen.getText()
                            .toString()
                            .trim();

            String sdt =
                    edtSDT.getText()
                            .toString()
                            .trim();

            String ngaylay =
                    edtNgayLay.getText()
                            .toString()
                            .trim();

            String ghichu =
                    edtGhiChu.getText()
                            .toString()
                            .trim();

            // ==========================
            // CHECK EMPTY
            // ==========================

            if(hoten.isEmpty()
                    || sdt.isEmpty()
                    || ngaylay.isEmpty()
                    || edtSoNgay.getText()
                    .toString()
                    .isEmpty()){

                Toast.makeText(

                        this,

                        "Nhập đầy đủ",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            // ==========================
            // CHECK DATE
            // ==========================

            try {

                SimpleDateFormat check =
                        new SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                        );

                check.setLenient(false);

                Date dateNgayDat =
                        check.parse(ngaydat);

                Date dateNgayLay =
                        check.parse(ngaylay);

                if(dateNgayLay.before(dateNgayDat)){

                    Toast.makeText(

                            this,

                            "Ngày lấy phải >= ngày đặt",

                            Toast.LENGTH_SHORT

                    ).show();

                    return;

                }

            }
            catch (Exception e){

                Toast.makeText(

                        this,

                        "Ngày không hợp lệ",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            // ==========================
            // THANH TOÁN
            // ==========================

            int selectedId =
                    radioThanhToan
                            .getCheckedRadioButtonId();

            if(selectedId == -1){

                Toast.makeText(

                        this,

                        "Chọn thanh toán",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            RadioButton radio =
                    findViewById(selectedId);

            String thanhtoan =
                    radio.getText().toString();

            // ==========================
            // GỬI API
            // ==========================

            datXe(

                    bienso,

                    hoten,

                    sdt,

                    ngaydat,

                    ngaylay,

                    songaythue,

                    gia,

                    thanhtien,

                    thanhtoan,

                    ghichu

            );

        });

        // ==========================
        // BACK
        // ==========================

        btnBack.setOnClickListener(v -> {

            startActivity(

                    new Intent(
                            CustomerInfo.this,
                            Home.class
                    )

            );

        });

    }

    // =================================
    // ĐẶT XE API
    // =================================

    private void datXe(

            String bienso,

            String hoten,

            String sdt,

            String ngaydat,

            String ngaylay,

            int songaythue,

            int dongia,

            int thanhtien,

            String thanhtoan,

            String ghichu

    ){

        try {

            JSONObject json =
                    new JSONObject();

            json.put("bienso", bienso);

            json.put("hoten", hoten);

            json.put("sodienthoai", sdt);

            json.put("ngaydat", ngaydat);

            json.put("ngaylay", ngaylay);

            json.put("songaythue", songaythue);

            json.put("dongia", dongia);

            json.put("thanhtien", thanhtien);

            json.put("thanhtoan", thanhtoan);

            json.put("tinhtrang", "Chưa phê duyệt");

            json.put("ghichu", ghichu);

            RequestBody body =

                    RequestBody.create(

                            json.toString(),

                            MediaType.parse(
                                    "application/json"
                            )

                    );

            Request request =

                    new Request.Builder()

                            .url(BASE_URL + "datxe")

                            .post(body)

                            .build();

            client.newCall(request)
                    .enqueue(new Callback() {

                        @Override
                        public void onFailure(
                                Call call,
                                java.io.IOException e
                        ) {

                            runOnUiThread(() ->

                                    Toast.makeText(

                                            CustomerInfo.this,

                                            "Lỗi server",

                                            Toast.LENGTH_SHORT

                                    ).show()

                            );

                        }

                        @Override
                        public void onResponse(
                                Call call,
                                Response response
                        ) throws java.io.IOException {

                            String result =
                                    response.body().string();

                            runOnUiThread(() -> {

                                Toast.makeText(

                                        CustomerInfo.this,

                                        "Thuê xe thành công",

                                        Toast.LENGTH_SHORT

                                ).show();

                                finish();

                            });

                        }

                    });

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }

}