package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalapp.R;
import com.example.carrentalapp.authentication.Login;

public class Menu extends AppCompatActivity {


    TextView hoten,id,kiemtra,capnhap,doimk,chinhsach,
            quatang,dangxuat;
    ImageView home,cart,menu,back;

    LinearLayout layoutchat, capnhapthongtin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        hoten = findViewById(R.id.txtten);
        id = findViewById(R.id.txtid);

        kiemtra = findViewById(R.id.txtkiemtra);
        capnhap = findViewById(R.id.txtcapnhap);
        doimk = findViewById(R.id.txtdoimk);
        chinhsach = findViewById(R.id.txtchinhsach);
        quatang = findViewById(R.id.txtquatang);
        dangxuat = findViewById(R.id.txtdangxuat);

        home = findViewById(R.id.btnHome1);
        cart = findViewById(R.id.btnCart1);
        menu = findViewById(R.id.btnMenu1);
        back = findViewById(R.id.img6);

        capnhapthongtin = findViewById(R.id.capnhapthongtin);

        layoutchat =
                findViewById(R.id.layoutchat);


        SharedPreferences sharedPreferences =
                getSharedPreferences("USER_FILE",
                        MODE_PRIVATE);

        int user_id = sharedPreferences.getInt("user_id",-1);
        String user_name = sharedPreferences.getString("username","");



        hoten.setText("  "+user_name);
        id.setText("  ID: "+user_id);

        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Menu.this,ChangePassword.class);
                intent.putExtra("id",user_id);
                startActivity(intent);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Home.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Cart.class);
                startActivity(intent);
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                sharedPreferences1.edit().clear().apply();



                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                sharedPreferences1.edit().clear().apply();
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
            }
        });

        layoutchat.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            ChatAI.class
                    )
            );

        });

        capnhapthongtin.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            UpdateProfile.class
                    )
            );

        });

        kiemtra.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            CheckInformation.class
                    )
            );



        });


        chinhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this,data_privacy.class));
            }
        });

        quatang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this,gift_user.class));
            }
        });




    }
}