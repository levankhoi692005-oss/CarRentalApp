package com.example.carrentalapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {

    ImageView imgthoat;
    EditText mkhientai,mkmoi,mkxacthuc;
    TextView capnhap;

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
                                                Toast.makeText(
                                                        ChangePassword.this,
                                                        message,
                                                        Toast.LENGTH_SHORT
                                                ).show();

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("password", new_password);
                                                editor.apply();

                                                mkhientai.setText("Nhập mật khẩu hiện tại");
                                                mkmoi.setText("NHập mật khẩu mới");
                                                mkxacthuc.setText("Xác nhận mật khẩu mới");

                                            } else {
                                                Toast.makeText(
                                                        ChangePassword.this,
                                                        message,
                                                        Toast.LENGTH_SHORT
                                                ).show();
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
                startActivity(new Intent(ChangePassword.this,Menu.class));
            }
        });

    }
}