package com.example.carrentalapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.carrentalapp.model.show_notification;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {

    ImageView imgthoat;
    EditText mkhientai,mkmoi,mkxacthuc;
    TextView capnhap,notification;
    LinearLayout layoutSuccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        imgthoat = findViewById(R.id.imgthoat);

        mkhientai = findViewById(R.id.mkhientai);
        mkmoi = findViewById(R.id.mkmoi);
        mkxacthuc = findViewById(R.id.mkxacthuc);

        capnhap = findViewById(R.id.capnhap);

        notification = findViewById(R.id.notification);
        layoutSuccess = findViewById(R.id.layoutSuccess);


        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);

        int user_id = sharedPreferences.getInt("user_id",-1);



        capnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old_password = mkhientai.getText().toString();
                String new_password = mkmoi.getText().toString();
                String au_password = mkxacthuc.getText().toString();

                if(!new_password.equals(au_password))
                {
                    Toast.makeText(ChangePassword.this,"Mật khẩu xác thực không trùng",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    ApiService.changepassword(
                            user_id,
                            old_password,
                            new_password,
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    Toast.makeText(ChangePassword.this,"Lỗi server",
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                    String data = response.body().string();
                                    runOnUiThread(() -> {

                                        try {
                                            JSONObject object = new JSONObject(data);
                                            boolean success = object.getBoolean("success");
                                            String message = object.getString("message");

                                            if (success) {

                                                show_notification.show_noti(
                                                        "Đổi mật khẩu thành công",
                                                        layoutSuccess,
                                                        notification,
                                                        ChangePassword.this,
                                                        "success"

                                                );

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("password", new_password);
                                                editor.apply();

                                                mkxacthuc.setText("");
                                                mkmoi.setText("");
                                                mkhientai.setText("");

                                                mkhientai.setHint("Nhập mật khẩu hiện tại");
                                                mkmoi.setHint("NHập mật khẩu mới");
                                                mkxacthuc.setHint("Xác nhận mật khẩu mới");

                                            } else {
                                                mkxacthuc.setText("");
                                                mkmoi.setText("");
                                                mkhientai.setText("");

                                                mkhientai.setHint("Nhập mật khẩu hiện tại");
                                                mkmoi.setHint("NHập mật khẩu mới");
                                                mkxacthuc.setHint("Xác nhận mật khẩu mới");
                                                show_notification.show_noti(
                                                        "Đổi mật không thành công",
                                                        layoutSuccess,
                                                        notification,
                                                        ChangePassword.this,
                                                        "error"

                                                );
                                            }


                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    );

                                }
                            }

                    );
                }

            }
        });




        imgthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}