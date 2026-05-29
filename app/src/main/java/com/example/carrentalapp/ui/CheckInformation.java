package com.example.carrentalapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckInformation extends AppCompatActivity {

    TextView txtName,
            txtGender,
            txtBirthday,
            txtPhone,
            txtEmail,
            txtCCCD,
            txtLicenseNumber,
            txtLicenseClass,
            txtLicenseExpiry,
            txtAddress;


    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_information);

        txtName = findViewById(R.id.txtName);
        txtGender = findViewById(R.id.txtGender);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtLicenseNumber = findViewById(R.id.txtLicenseNumber);
        txtLicenseClass = findViewById(R.id.txtLicenseClass);
        txtLicenseExpiry = findViewById(R.id.txtLicenseExpiry);
        txtAddress = findViewById(R.id.txtAddress);
        back = findViewById(R.id.btnBack);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "USER_FILE",
                MODE_PRIVATE
        );

        int id_user = sharedPreferences.getInt("user_id",-1);
        ApiService.get_user_profile(
                id_user
                , new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(()->
                        {
                            Toast.makeText(CheckInformation.this,"Lỗi server",Toast.LENGTH_SHORT).show();
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
                                    txtName.setText("Họ tên: "+ object.getString("name"));
                                    txtEmail.setText("Email: "+object.getString("email"));
                                    txtGender.setText("Giới tính: "+object.getString("gender"));
                                    txtPhone.setText("Số điện thoại: "+object.getString("phone"));
                                    txtBirthday.setText("Ngày sinh: "+object.getString("birthday_format"));
                                    txtCCCD.setText("CCCD: "+object.getString("national_id"));
                                    txtLicenseNumber.setText("Mã GPLX lái: "+object.getString("driver_license_number"));
                                    txtLicenseClass.setText("Hạng bằng: "+object.getString("driver_license_class"));
                                    txtLicenseExpiry.setText("Hết hạn: "+object.getString("license_expiry_format"));
                                   txtAddress.setText("Địa chỉ: "+object.getString("street_address"));
                                }
                                else
                                {
                                    runOnUiThread(()->
                                    {
                                        Toast.makeText(CheckInformation.this,"Load dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                                    });
                                }

                            }
                            catch (
                                    Exception e
                            )
                            {
                                e.printStackTrace();
                            }

                        });


                    }
                }

        );


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}