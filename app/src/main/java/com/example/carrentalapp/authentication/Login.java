package com.example.carrentalapp.authentication;

import static com.example.carrentalapp.api.ApiService.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.admin.AdminVehicleManagement;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.show_notification;
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

    TextView btnRegister,notification;
    LinearLayout layoutSuccess;


    GradientDrawable drawable_missing ;
    GradientDrawable drawable_error ;
    GradientDrawable drawable_success ;
    GradientDrawable drawable_enough ;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edtPhone = findViewById(R.id.user_sodt);

        edtPassword = findViewById(R.id.user_password);

        btnLogin = findViewById(R.id.btnLogin);

        btnRegister = findViewById(R.id.btnRegister);

        layoutSuccess = findViewById(R.id.layoutSuccess);

        notification = findViewById(R.id.notification);

        SharedPreferences sharedPreferences =
                getSharedPreferences("USER_FILE",
                        MODE_PRIVATE);
        String user_passwor = sharedPreferences.getString("password", "");
        String user_phonenumber = sharedPreferences.getString("phonenumber", "");


         drawable_missing = new GradientDrawable();
         drawable_error = new GradientDrawable();
         drawable_success = new GradientDrawable();
         drawable_enough = new GradientDrawable();


        drawable_enough.setStroke(4, Color.WHITE);
        drawable_enough.setColor(Color.parseColor("#ffffff"));

        drawable_success.setStroke(4,Color.parseColor("#6edb7c"));
        drawable_success.setColor(Color.parseColor("#6edb7c"));
        drawable_success.setCornerRadius(5);

        drawable_error.setColor(Color.parseColor("#ec592f"));
        drawable_error.setCornerRadius(5);
        drawable_error.setStroke(4,Color.parseColor("#ec592f"));

        drawable_missing.setColor(Color.parseColor("#ffffff"));
        drawable_missing.setStroke(4, Color.RED);


        if (!user_phonenumber.isEmpty() && !user_passwor.isEmpty()) {
            loginUser(user_phonenumber, user_passwor);
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

            if (phone.isEmpty()
                        || pass.isEmpty()) {


                if (phone.isEmpty()
                        && pass.isEmpty()) {
                    layoutSuccess.setBackground(drawable_error);
                    edtPhone.setBackground(drawable_missing);
                    edtPassword.setBackground(drawable_missing);
                    edtPassword.setHint("Mật khẩu");
                    edtPhone.setHint("Nhập số điện thoại");
                    show_notification("Hãy nhập đầy đủ",
                            layoutSuccess,notification,this);

                    return;
                }



                    if(pass.isEmpty())
                    {
                        layoutSuccess.setBackground(drawable_error);
                        edtPhone.setBackground(drawable_missing);
                        edtPassword.setHint("Mật khẩu");
                        edtPhone.setBackground(drawable_enough);
                        show_notification("Hãy nhập mật khẩu",
                                layoutSuccess,notification,this);
                        return;

                    }

                    if(phone.isEmpty())
                    {
                        layoutSuccess.setBackground(drawable_error);
                        edtPassword.setBackground(drawable_missing);
                        edtPhone.setHint("Nhập số điện thoại");
                        edtPassword.setBackground(drawable_enough);
                        show_notification("Hãy nhập số điện thoại",
                                layoutSuccess,notification,this);
                        return;


                }




                return;

            }

            // ADMIN

            if (phone.equals("0")
                    && pass.equals("0")) {



                show_notification.show_noti(
                        "Bạn đã đăng nhập vào Admin",
                        layoutSuccess,
                        notification,
                        this,
                        "success"
                );
                new Handler(Looper.getMainLooper())
                        .postDelayed(()->
                        {


                            startActivity(

                                    new Intent(
                                            Login.this,
                                            AdminVehicleManagement.class
                                    )

                            );


                        },2000);


                return;

            }

            loginUser(phone, pass);

        });

    }


    // LOGIN API

    private void loginUser(String phone, String password) {


        ApiService.login(
                phone,
                password,
                new Callback() {

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

                                if (success) {


                                    layoutSuccess.setBackground(drawable_success);
                                    show_notification("Đăng nhập thành công",
                                            layoutSuccess,
                                            notification,
                                            Login.this);

                                    int userId =
                                            object.getInt("id");

                                    String name =
                                            object.getString("name");


                                    SharedPreferences sharedPreferences =
                                            getSharedPreferences("USER_FILE",
                                                    MODE_PRIVATE);

                                    SharedPreferences.Editor editor =
                                            sharedPreferences.edit();

                                    editor.putString("username", name);
                                    editor.putInt("user_id", userId);
                                    editor.putString("password", password);
                                    editor.putString("phonenumber", phone);
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


                                } else {


                                    show_notification.show_noti("Sai tài khoản",
                                            layoutSuccess,
                                            notification,
                                            Login.this,
                                            "error");




                                }

                            } catch (Exception e) {

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


    private Handler networkHandler = new Handler();
    private Runnable networkRunnable;

    private void starNetworkChecking()
    {
        networkRunnable = new Runnable() {
            @Override
            public void run() {
                if(!isInternetAvailable())
                {

                    show_notification.show_noti("Lỗi kết nối mạng!",
                            layoutSuccess,
                            notification,
                            Login.this,
                            "error");
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


    void show_notification(String notification_message, LinearLayout layout, TextView textView, Activity activity)
    {
        // gan message vao text
        textView.setText(notification_message);
        // set độ hiển thị: Visible : hoàn toàn, gone: trong suất
        layout.setVisibility(
                View.VISIBLE
        );
        // set độ trong xuất: 0f:0%
        layout.setAlpha(0f);
        //set thiết lập vị trí dịch theo chiều: Y dọc, x ngang, - xuống, + lên

        layout.setTranslationY(-100);
        // thực hiện animate

        layout.animate()
                .alpha(1f)
                .translationY(0)
                .setDuration(400)
                .start();

        new Handler(Looper.getMainLooper())
                .postDelayed(()->{
                   layout.animate()
                           .alpha(0f)
                           .translationY(-100)
                           .setDuration(300)
                           .withEndAction(()->{
                              layout.setVisibility(
                                      View.GONE
                              );
                           }).start();

                },1000);







    }










}
