package com.example.carrentalapp.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.admin.AdminVehicleManagement;
import com.example.carrentalapp.ui.Home;
import com.example.carrentalapp.ui.Menu;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    EditText edtPhone, edtPassword;

    Button btnLogin;

    TextView btnRegister;

    OkHttpClient client = new OkHttpClient();

    String BASE_URL =
            "https://jonnie-unpoetic-coldly.ngrok-free.dev/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edtPhone = findViewById(R.id.user_sodt);

        edtPassword = findViewById(R.id.user_password);

        btnLogin = findViewById(R.id.btnLogin);

        btnRegister = findViewById(R.id.btnRegister);


        SharedPreferences sharedPreferences =
                getSharedPreferences("USER_FILE",
                        MODE_PRIVATE);
        String user_passwor = sharedPreferences.getString("password","");
        String user_phonenumber = sharedPreferences.getString("phonenumber","");

        if(!user_phonenumber.isEmpty() && !user_passwor.isEmpty())
        {
            loginUser(user_phonenumber,user_passwor);
        }



        starNetworkChecking();


        // REGISTER









        btnRegister.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            Login.this,
                            Register.class
                    )
            );

        });




        // LOGIN



        btnLogin.setOnClickListener(v -> {

            String phone =
                    edtPhone.getText()
                            .toString()
                            .trim();

            String pass =
                    edtPassword.getText()
                            .toString()
                            .trim();

            if(phone.isEmpty()
                    || pass.isEmpty()){

                Toast.makeText(

                        Login.this,

                        "Nhập đầy đủ",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            // ADMIN

            if(phone.equals("0")
                    && pass.equals("0")){

                startActivity(

                        new Intent(
                                Login.this,
                                AdminVehicleManagement.class
                        )

                );

                return;

            }

            loginUser(phone, pass);

        });

    }


    // LOGIN API


    private void loginUser(
            String phone,
            String password
    ){

        try {

            JSONObject json =
                    new JSONObject();

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

                            .url(BASE_URL + "login")

                            .post(body)

                            .build();

            client.newCall(request)
                    .enqueue(new Callback() {

                        // ==========================
                        // ERROR
                        // ==========================

                        @Override
                        public void onFailure(
                                Call call,
                                java.io.IOException e
                        ) {

                            Log.e("LOGIN_ERROR",
                                    e.toString());

                            runOnUiThread(() ->

                                    Toast.makeText(

                                            Login.this,

                                            e.toString(),

                                            Toast.LENGTH_LONG

                                    ).show()

                            );

                        }


                        // SUCCESS


                        @Override
                        public void onResponse(
                                Call call,
                                Response response
                        ) throws java.io.IOException {

                            String result =
                                    response.body().string();

                            Log.e("SERVER_RESULT",
                                    result);

                            runOnUiThread(() -> {

                                try {

                                    JSONObject object =
                                            new JSONObject(result);

                                    boolean success =
                                            object.getBoolean("success");

                                    if(success){

                                        int userId =
                                                object.getInt("id");

                                        String name =
                                                object.getString("name");





                                        SharedPreferences sharedPreferences =
                                                getSharedPreferences("USER_FILE",
                                                        MODE_PRIVATE);

                                        SharedPreferences.Editor editor =
                                                sharedPreferences.edit();

                                        editor.putString("username",name);
                                        editor.putInt("user_id",userId);
                                        editor.putString("password",password);
                                        editor.putString("phonenumber",phone);
                                        editor.apply();



                                        Toast.makeText(

                                                Login.this,

                                                "Xin chào " + name,

                                                Toast.LENGTH_SHORT

                                        ).show();




                                        startActivity(

                                                new Intent(

                                                        Login.this,

                                                        Home.class

                                                )

                                        );


                                    }
                                    else{

                                        Toast.makeText(

                                                Login.this,

                                                "Sai tài khoản",

                                                Toast.LENGTH_SHORT

                                        ).show();


                                    }

                                }
                                catch (Exception e){

                                    e.printStackTrace();

                                    Toast.makeText(

                                            Login.this,

                                            e.toString(),

                                            Toast.LENGTH_LONG

                                    ).show();

                                }

                            });

                        }

                    });

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }
    private Handler networkHandler = new Handler();
    private Runnable networkRunnable;

    private void starNetworkChecking()
    {
        networkRunnable = new Runnable() {
            @Override
            public void run() {
                if(!isInternetAvailable())
                {
                    Toast.makeText(
                            Login.this,
                            "Lỗi mạng, kiểm tra lại kết nối!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                networkHandler.postDelayed(this, 3000);
            }
        };
        networkHandler.post(networkRunnable);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(
                            connectivityManager.getActiveNetwork());

            return capabilities != null &&
                    (
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    );
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (networkHandler != null && networkRunnable != null) {
            networkHandler.removeCallbacks(networkRunnable);
        }
    }


}