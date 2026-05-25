package com.example.carrentalapp.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    EditText edtName,
            edtPhone,
            edtPassword,
            edtConfirm;

    Button btnRegister;

    TextView btnLogin;

    OkHttpClient client =
            new OkHttpClient();

    String BASE_URL =
            "https://jonnie-unpoetic-coldly.ngrok-free.dev/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


        // FINDVIEWBYID


        edtName =
                findViewById(R.id.edtName);

        edtPhone =
                findViewById(R.id.edtPhone);

        edtPassword =
                findViewById(R.id.edtPassword);

        edtConfirm =
                findViewById(R.id.edtConfirm);

        btnRegister =
                findViewById(R.id.btnRegister);


        btnLogin =
                findViewById(R.id.textView2);


        // QUAY LẠI LOGIN


        btnLogin.setOnClickListener(v -> {

            startActivity(

                    new Intent(

                            Register.this,

                            Login.class

                    )

            );

            finish();

        });


        // REGISTER


        btnRegister.setOnClickListener(v -> {

            String name =
                    edtName.getText()
                            .toString()
                            .trim();

            String phone =
                    edtPhone.getText()
                            .toString()
                            .trim();

            String pass =
                    edtPassword.getText()
                            .toString()
                            .trim();

            String confirm =
                    edtConfirm.getText()
                            .toString()
                            .trim();


            // CHECK EMPTY


            if(name.isEmpty()
                    || phone.isEmpty()
                    || pass.isEmpty()
                    || confirm.isEmpty()){

                Toast.makeText(

                        this,

                        "Nhập đầy đủ",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }


            // CHECK PASSWORD


            if(!pass.equals(confirm)){

                Toast.makeText(

                        this,

                        "Mật khẩu không khớp",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            registerUser(
                    name,
                    phone,
                    pass
            );

        });

    }


    // REGISTER USER


    private void registerUser(

            String name,

            String phone,

            String password

    ){

        try {

            JSONObject json =
                    new JSONObject();

            json.put("name", name);

            json.put("phone", phone);

            json.put("password", password);

            RequestBody body =

                    RequestBody.create(

                            json.toString(),

                            MediaType.parse(
                                    "application/json"
                            )

                    );

            Request request =

                    new Request.Builder()

                            .url(BASE_URL + "register")

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

                                            Register.this,

                                            e.toString(),

                                            Toast.LENGTH_LONG

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

                                try {

                                    JSONObject object =
                                            new JSONObject(result);

                                    boolean success =
                                            object.getBoolean("success");

                                    if(success){

                                        Toast.makeText(

                                                Register.this,

                                                "Đăng ký thành công",

                                                Toast.LENGTH_SHORT

                                        ).show();

                                        startActivity(

                                                new Intent(

                                                        Register.this,

                                                        Login.class

                                                )

                                        );

                                        finish();

                                    }
                                    else{

                                        String message =
                                                object.getString("message");

                                        Toast.makeText(

                                                Register.this,

                                                message,

                                                Toast.LENGTH_SHORT

                                        ).show();

                                    }

                                }
                                catch (Exception e){

                                    e.printStackTrace();

                                }

                            });

                        }

                    });

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }

}