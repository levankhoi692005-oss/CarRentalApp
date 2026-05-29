package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Messgae;
import com.example.carrentalapp.model.show_notification;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

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
            txtTongTien,
            txtNgayDangKy,
            txtGioDangKy,

    notification;

    EditText edtHoTen,
            edtSDT,
            edtNgayLay,


    edtSoNgay,
            edtGhiChu,
            txtsonha2,
                    txtsonha1;

    LinearLayout edtdiachinhan,

    edtdiachikh;

    RadioGroup radioThanhToan;

    LinearLayout layoutSuccess;


    Button btnThue;

    OkHttpClient client =
            new OkHttpClient();


    int gia = 0;

    int songaythue = 0;

    int thanhtien = 0;

    int user_id = -1;

    String tenxe = "";
    String ngaydat = "";

    String giodangky = "";
    String province = "";
    String district = "";
    String ward = "";

    String diachikh ="",
            diachinhan="";



    Spinner spProvince,
            spDistrict,
            spWard,
            spProvince1,
            spDistrict1,
            spWard1;

    String str_xa = "";
    String str_huyen = "";

    String str_tinh = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_info);


        txtsonha2 =
                findViewById(R.id.txtsonha2);

        txtsonha1 =
                findViewById(R.id.txtsonha1);

        imgXe =
                findViewById(R.id.imgXe);

        btnBack =
                findViewById(R.id.btnBack);

        txtTenXe =
                findViewById(R.id.txtTenXe);

        txtBienSo =
                findViewById(R.id.txtBienSo);

        txtGia =
                findViewById(R.id.txtGia);

        txtTongTien =
                findViewById(R.id.txtTongTien);

        txtNgayDangKy =
                findViewById(R.id.txtNgayDangKy);

        txtGioDangKy =
                findViewById(R.id.txtGioDangKy);

        edtHoTen =
                findViewById(R.id.edtHoTen);

        edtSDT =
                findViewById(R.id.edtSDT);

        edtNgayLay =
                findViewById(R.id.edtNgayLay);

        edtSoNgay =
                findViewById(R.id.edtSoNgay);

        edtdiachinhan =
                findViewById(R.id.edtdiachinhan);

        edtdiachikh =
                findViewById(R.id.edtdiachikh);

        edtGhiChu =
                findViewById(R.id.edtGhiChu);

        radioThanhToan =
                findViewById(R.id.radioThanhToan);

        btnThue =
                findViewById(R.id.btnThue);


        notification =
                findViewById(R.id.notification);

        layoutSuccess =
                findViewById(R.id.layoutSuccess);


        spProvince = findViewById(R.id.spinnerProvince);
        spDistrict = findViewById(R.id.spinnerDistrict);
        spWard = findViewById(R.id.spinnerWard);


        spProvince1 = findViewById(R.id.spinnerProvince1);
        spDistrict1 = findViewById(R.id.spinnerDistrict1);
        spWard1 = findViewById(R.id.spinnerWard1);


        // GET USER ID


        SharedPreferences preferences =
                getSharedPreferences(
                        "USER_FILE",
                        MODE_PRIVATE
                );

        user_id =
                preferences.getInt(
                        "user_id",
                        -1
                );


        // GET INTENT


        Intent intent =
                getIntent();

        String anhxe =
                intent.getStringExtra("anhxe");

        tenxe =
                intent.getStringExtra("tenxe");

        String bienso =
                intent.getStringExtra("bienso");

        gia =
                intent.getIntExtra(
                        "gia",
                        0
                );


        // HIỂN THỊ XE


        txtTenXe.setText(
                tenxe
        );

        txtBienSo.setText(
                bienso
        );

        txtGia.setText(
                gia + " VND / ngày"
        );

        Glide.with(this)
                .load(anhxe)
                .into(imgXe);

        // NGÀY GIỜ HIỆN TẠI
        SimpleDateFormat sdfNgay =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                );

        SimpleDateFormat sdfGio =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault()
                );

        SimpleDateFormat sdfdatetime = new SimpleDateFormat("YYYY_MM_dd HH:mm:ss", Locale.getDefault());

        sdfdatetime.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        sdfNgay.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        sdfGio.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        ngaydat =
                sdfNgay.format(new Date());

        giodangky =
                sdfGio.format(new Date());

        String datetime = sdfdatetime.format(new Date());

        txtNgayDangKy.setText(
                ngaydat
        );


        txtGioDangKy.setText(giodangky);

        // TÍNH TIỀN


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

                        if (!s.toString().isEmpty()) {

                            songaythue =
                                    Integer.parseInt(
                                            s.toString()
                                    );

                            thanhtien =
                                    songaythue * gia;

                            txtTongTien.setText(

                                    thanhtien
                                            + " VND"

                            );

                        } else {

                            txtTongTien.setText(
                                    "0 VND"
                            );

                        }

                    }

                }

        );


        // DATE PICKER


        edtNgayLay.setOnClickListener(v -> {


            String ngaydat = txtNgayDangKy.getText().toString();
            java.util.Calendar calendar =
                    java.util.Calendar.getInstance();

            DatePickerDialog dp =
                    new DatePickerDialog(

                            this,

                            (view, year, month, day) -> {

                                String ngay =

                                        String.format(

                                                Locale.getDefault(),

                                                "%04d-%02d-%02d",

                                                year,

                                                month + 1,

                                                day

                                        );

                                edtNgayLay.setText(
                                        ngay
                                );

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

            try {

                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                );

                Date datedat = sdf.parse(ngaydat);

                dp.getDatePicker().setMinDate(
                        datedat.getTime() + 86400000
                );


            } catch (Exception e) {
                e.printStackTrace();
            }


            dp.show();

        });


        // BUTTON THUÊ

        get_tinh(spProvince,spDistrict,spWard);
       get_tinh(spProvince1,spDistrict1,spWard1);

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
            String sonha1 = txtsonha1.getText().toString().trim();
            String sonha2 = txtsonha2.getText().toString().trim();
            String diachikh =
                    sonha1
                            + ", "
                            + spWard.getSelectedItem().toString()
                            + ", "
                            + spDistrict.getSelectedItem().toString()
                            + ", "
                            + spProvince.getSelectedItem().toString();

            String diachinhan =
                    sonha2
                            + ", "
                            + spWard1.getSelectedItem().toString()
                            + ", "
                            + spDistrict1.getSelectedItem().toString()
                            + ", "
                            + spProvince1.getSelectedItem().toString();


            // CHECK EMPTY


            if (hoten.isEmpty()
                    || sdt.isEmpty()
                    || ngaylay.isEmpty()
                    || sonha1.isEmpty()
                    || sonha2.isEmpty()
                    || edtSoNgay.getText()
                    .toString()
                    .isEmpty()) {


                show_notification.show_noti(
                        "Nhập đầy đủ thông tin",
                        layoutSuccess,
                        notification,
                        CustomerInfo.this,
                        "messing"
                );

                return;

            }


            // CHECK THANH TOÁN


            int selectedId =
                    radioThanhToan
                            .getCheckedRadioButtonId();

            if (selectedId == -1) {

                Toast.makeText(

                        this,

                        "Chọn phương thức thanh toán",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            RadioButton radio =
                    findViewById(selectedId);

            String thanhtoan =
                    radio.getText()
                            .toString();




            // GỬI API


            ApiService.order(

                    bienso,

                    hoten,

                    sdt,

                    datetime,

                    ngaylay,

                    songaythue,

                    gia,

                    thanhtien,

                    thanhtoan,

                    ghichu,

                    user_id,

                    tenxe,

                    diachikh,

                    diachinhan,

                    new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            runOnUiThread(() ->
                                    Toast.makeText(CustomerInfo.this,
                                            "Khong the ket noi server",
                                            Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            String result =
                                    response.body().string();
                            runOnUiThread(() -> {
                                try {

                                    JSONObject object =
                                            new JSONObject(result);

                                    boolean success =
                                            object.getBoolean("success");

                                    if (success) {

                                        String madon =
                                                object.getString("madon");


                                        show_notification.show_noti(
                                                "Đặt xe thành công",
                                                layoutSuccess,
                                                notification,
                                                CustomerInfo.this,
                                                "success"
                                        );

                                        Toast.makeText(

                                                CustomerInfo.this,

                                                "Thuê xe thành công\nMã đơn: "
                                                        + madon,

                                                Toast.LENGTH_LONG

                                        ).show();

                                        finish();

                                    } else {


                                        show_notification.show_noti(
                                                "Đặt xe thất bại",
                                                layoutSuccess,
                                                notification,
                                                CustomerInfo.this,
                                                "error"
                                        );
                                        Toast.makeText(

                                                CustomerInfo.this,

                                                "Đặt xe thất bại",

                                                Toast.LENGTH_SHORT

                                        ).show();

                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }
                            });
                        }
                    }

            );

        });


        // BACK


        btnBack.setOnClickListener(v -> {

            finish();

        });

    }


    private String get_tinh_json(String ten_file) {
        String json = null;

        try {

            InputStream is = getAssets().open(ten_file);

            int size = is.available();


            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private void get_tinh(Spinner spinnerProvince,
                          Spinner spinnerDistrict,
                          Spinner spinnerWard) {

        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("tinh_tp.json"));

            Iterator<String> keys = jsonObject.keys();
            ArrayList<String> list_tinh_name = new ArrayList<>();
            ArrayList<String> list_tinh_code = new ArrayList<>();


            while (keys.hasNext()) {

                String key = keys.next();

                JSONObject tinh = jsonObject.getJSONObject(key);

                String name = tinh.getString("name");
                String code = tinh.getString("code");
                list_tinh_name.add(name);
                list_tinh_code.add(code);
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_dropdown_item,
                            list_tinh_name);

            spinnerProvince.setAdapter(adapter);

            spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    province = list_tinh_name.get(i);
                   get_huyen(list_tinh_code.get(i), spinnerDistrict, spinnerWard);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void get_huyen(String tinh, Spinner spinnerDistrict,
                             Spinner spinnerWard) {


        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("quan_huyen.json"));
            Iterator<String> keys = jsonObject.keys();
            ArrayList<String> list_huyen_name = new ArrayList<>();

            ArrayList<String> list_huyen_code = new ArrayList<>();

            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject huyen = jsonObject.getJSONObject(key);
                String parent_code = huyen.getString("parent_code");

                if (parent_code.equals(tinh)
                ) {
                    String name = huyen.getString("name");
                    String code = huyen.getString("code");
                    list_huyen_name.add(name);
                    list_huyen_code.add(code);

                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    list_huyen_name
            );
            spinnerDistrict.setAdapter(adapter);

            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    district = list_huyen_name.get(i);
                   get_xa(list_huyen_code.get(i), spinnerWard);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_xa(String huyen,
                          Spinner spinnerWard) {

        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("xa_phuong.json"));

            Iterator<String> keys = jsonObject.keys();
            ArrayList<String> list_xa = new ArrayList<>();

            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject xa = jsonObject.getJSONObject(key);
                String parent_code = xa.getString("parent_code");
                if (parent_code.equals(huyen)) {
                    String name = xa.getString("name");
                    list_xa.add(name);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    list_xa
            );

            spinnerWard.setAdapter(adapter);
            spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    ward = list_xa.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }











}