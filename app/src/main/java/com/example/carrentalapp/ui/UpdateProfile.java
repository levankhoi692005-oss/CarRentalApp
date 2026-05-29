package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.show_notification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateProfile extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone,
            edtBirthday, edtCCCD,
            edtGPLX, edtExpire,
            edtDetailAddress;
            TextView notification;

    Spinner spinnerProvince,
            spinnerDistrict,
            spinnerWard,
            spinnerLicense,
            edtgender;

    Button btnConfirm, thoat;

    LinearLayout layoutSuccess;
    String province = "";
    String district = "";
    String ward = "";
    String   name="",
            gender="",
             email="",
             phone="",
             birthday="",
             national_id="",
            license_number="",
            license_class="",
            license_expiry="",
            street_address="",

             date_blx="";

    String savedProvince = "";
    String savedDistrict = "";
    String savedWard = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        anhXa();
        gioi_tinh();
        ngay_sinh(edtBirthday);
        bang_lai_xe();
        han_blx(edtExpire);
        get_tinh();




        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id",-1);


        get_profie(user_id);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();
                birthday = edtBirthday.getText().toString().trim();
                national_id = edtCCCD.getText().toString().trim();
                license_number = edtGPLX.getText().toString().trim();
                license_expiry = edtExpire.getText().toString().trim();
                street_address = edtDetailAddress.getText().toString().trim();
                gender = edtgender.getSelectedItem().toString();
                license_class = spinnerLicense.getSelectedItem().toString();
                province = spinnerProvince.getSelectedItem().toString();
                district = spinnerDistrict.getSelectedItem().toString();
                ward = spinnerWard.getSelectedItem().toString();
                date_blx = edtExpire.getText().toString().trim();

                if(
                        name.isEmpty() ||
                                gender.isEmpty() ||
                                email.isEmpty() ||
                                phone.isEmpty() ||
                                birthday.isEmpty() ||
                                national_id.isEmpty() ||
                                license_number.isEmpty() ||
                                license_class.isEmpty() ||
                                license_expiry.isEmpty() ||
                                province.isEmpty() ||
                                district.isEmpty() ||
                                ward.isEmpty() ||
                                street_address.isEmpty()
                ) {
                   show_notification.show_noti(
                           "Nhập đầy đủ thông tin",
                           layoutSuccess,
                           notification,
                           UpdateProfile.this,
                           "error"
                   );
                    return;
                }

                String fullAddress = street_address+","+ ward + "," + district + "," + province;
                api(user_id,name,gender,fullAddress,email,phone,chuan_hoa_date(birthday)
                        ,national_id,license_number,license_class,chuan_hoa_date(license_expiry));
            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProfile.this,Menu.class));
            }
        });


    }




    private  void anhXa()
    {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.editEmail);
        edtPhone = findViewById(R.id.edtPhone);

        edtBirthday = findViewById(R.id.edtBirthday);
        edtCCCD = findViewById(R.id.edtCCCD);
        edtGPLX = findViewById(R.id.edtGPLX);
        edtExpire = findViewById(R.id.edtExpire);
        edtDetailAddress = findViewById(R.id.edtDetailAddress);

        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);
        spinnerLicense = findViewById(R.id.spinnerLicense);
        edtgender = findViewById(R.id.edtgender);

        btnConfirm = findViewById(R.id.btnConfirm);

        notification =
                findViewById(R.id.notification);

        layoutSuccess =
                findViewById(R.id.layoutSuccess);

        thoat =
                findViewById(R.id.btnthoat);
    }


    private String chuan_hoa_date(String date)
    {
        String[] date_split = date.split("/");
        String day = date_split[0].trim();
        String month = date_split[1].trim();
        String year = date_split[2].trim();

        return year+"-"+month+"-"+day;
    }
    private void han_blx(TextView textView)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        edtExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateProfile.this,
                        (view,y,m,d)->{

                            String date = d +"/"+(m+1)+"/"+y;
                            textView.setText(date);
                            date_blx = date;
                        },year,month,day
                );
                datePickerDialog.show();
            }
        });



    }

    private void gioi_tinh()
    {
        ArrayList<String> list_gt = new ArrayList<>(
                Arrays.asList("Nam","Nữ","Khác")
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                list_gt
        );
        edtgender.setAdapter(adapter);
        edtgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender= list_gt.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void ngay_sinh(EditText editText)
    {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateProfile.this,
                        (view ,y,m,d)->
                        {
                            String date =
                                    d+"/" +(m+1) +"/"+ y;
                            editText.setText(date);

                        },year,month,day

                );
                datePickerDialog.show();
            }
        });



    }
    private void bang_lai_xe()
    {

        ArrayList<String> list_blx = new ArrayList<>(
                Arrays.asList("A1", "A2", "B1", "B2", "C", "D")
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                list_blx
        );
        spinnerLicense.setAdapter(adapter);
    }
    private String  get_tinh_json(String ten_file)
    {
        String json = null;

        try {

            InputStream is = getAssets().open(ten_file);

            int size = is.available();


            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer,"UTF-8");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return json;
    }



    private void get_tinh()
    {

        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("tinh_tp.json"));

            Iterator<String> keys = jsonObject.keys();
            ArrayList<String > list_tinh_name = new ArrayList<>();
            ArrayList<String > list_tinh_code = new ArrayList<>();


            while (keys.hasNext())
            {

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

                    province =list_tinh_name.get(i);
                    get_huyen(list_tinh_code.get(i));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private void get_huyen(String tinh)
    {


        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("quan_huyen.json"));
            Iterator<String> keys = jsonObject.keys();
            ArrayList<String> list_huyen_name = new ArrayList<>();

            ArrayList<String> list_huyen_code = new ArrayList<>();

            while (keys.hasNext())
            {
                String key = keys.next();
                JSONObject huyen = jsonObject.getJSONObject(key);
                String parent_code = huyen.getString("parent_code");

                if( parent_code.equals(tinh)
                )
                {
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
                    get_xa(list_huyen_code.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void get_xa(String huyen) {

        try {
            JSONObject jsonObject = new JSONObject(get_tinh_json("xa_phuong.json"));

            Iterator<String> keys = jsonObject.keys();
            ArrayList<String> list_xa = new ArrayList<>();

            while (keys.hasNext())
            {
                String key = keys.next();
                JSONObject xa = jsonObject.getJSONObject(key);
                String parent_code = xa.getString("parent_code");
                if(parent_code.equals(huyen))
                {
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


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private  void api( int id,
                   String name,
                   String gender,
                   String street_address,
                   String email,
                   String phone,
                   String birthday,
                   String national_id,
                   String license_number,
                   String license_class,
                   String license_expiry
                  )
    {
        ApiService.update_profile(id,
                name,
                gender,
                street_address,
                email,
                phone,
                birthday,
                national_id,
                license_number,
                license_class,
                license_expiry,
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(()->
                        {
                            Toast.makeText(UpdateProfile.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();
                        runOnUiThread(()->
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean success = jsonObject.getBoolean("success");
                                if(success)
                                {
                                    show_notification.show_noti(
                                            "Cập nhập thành công",
                                            layoutSuccess,
                                            notification,
                                            UpdateProfile.this,
                                            "success"
                                    );
                                }
                                else {
                                    show_notification.show_noti(
                                            "Cập nhập thất bại",
                                            layoutSuccess,
                                            notification,
                                            UpdateProfile.this,
                                            "error"
                                    );
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


    private void get_profie(int id)
    {
        ApiService.get_user_profile(id
                , new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(()->
                        {
                            Toast.makeText(UpdateProfile.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();
                        runOnUiThread(()->
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                boolean success = jsonObject.getBoolean("success");
                                if(success)
                                {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    edtName.setText(object.getString("name"));
                                    edtEmail.setText(object.getString("email"));

                                    String gender = object.getString("gender");
                                    ArrayAdapter adapter_gender = (ArrayAdapter) edtgender.getAdapter();
                                    int position_gender = adapter_gender.getPosition(gender);
                                    edtgender.setSelection(position_gender);


                                    edtPhone.setText(object.getString("phone"));

                                    edtBirthday.setText(object.getString("birthday_format"));

                                    edtCCCD.setText(object.getString("national_id"));
                                    edtGPLX.setText(object.getString("driver_license_number"));

                                    String hangbanglai = object.getString("driver_license_class");
                                    ArrayAdapter adapter_gplx = (ArrayAdapter)  spinnerLicense.getAdapter();
                                    int posision_gplx = adapter_gplx.getPosition(hangbanglai);
                                    spinnerLicense.setSelection(posision_gplx);
                                    edtExpire.setText(object.getString("license_expiry_format"));




                                    String address = object.getString("street_address");

                                    String[] address_split = address.split(",");

                                    if(address_split.length >= 4)
                                    {
                                        edtDetailAddress.setText(address_split[0]);

                                        savedWard = address_split[1];
                                        savedDistrict = address_split[2];
                                        savedProvince = address_split[3];

                                        // Chọn tỉnh
                                        ArrayAdapter<String> adapterProvince =
                                                (ArrayAdapter<String>) spinnerProvince.getAdapter();

                                        if(adapterProvince != null)
                                        {
                                            int provincePos =
                                                    adapterProvince.getPosition(savedProvince);

                                            if(provincePos >= 0)
                                            {
                                                spinnerProvince.setSelection(provincePos);

                                                // Đợi spinnerDistrict được tạo
                                                spinnerProvince.postDelayed(() -> {

                                                    ArrayAdapter<String> adapterDistrict =
                                                            (ArrayAdapter<String>) spinnerDistrict.getAdapter();

                                                    if(adapterDistrict != null)
                                                    {
                                                        int districtPos =
                                                                adapterDistrict.getPosition(savedDistrict);

                                                        if(districtPos >= 0)
                                                        {
                                                            spinnerDistrict.setSelection(districtPos);

                                                            // Đợi spinnerWard được tạo
                                                            spinnerDistrict.postDelayed(() -> {

                                                                ArrayAdapter<String> adapterWard =
                                                                        (ArrayAdapter<String>) spinnerWard.getAdapter();

                                                                if(adapterWard != null)
                                                                {
                                                                    int wardPos =
                                                                            adapterWard.getPosition(savedWard);

                                                                    if(wardPos >= 0)
                                                                    {
                                                                        spinnerWard.setSelection(wardPos);
                                                                    }
                                                                }

                                                            }, 200);

                                                        }
                                                    }

                                                }, 1000);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    runOnUiThread(()->
                                    {
                                        Toast.makeText(UpdateProfile.this,"Load dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                                    });
                                }

                            }catch (Exception e)
                            {
                    e.printStackTrace();

                            }


                        });




                    }
                });
    }

}