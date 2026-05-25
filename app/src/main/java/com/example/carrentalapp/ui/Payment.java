package com.example.carrentalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalapp.R;

public class Payment extends AppCompatActivity {

    ImageView imageView,btnBack;
    TextView textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        String madonIntent = getIntent().getStringExtra("madon");
        String sodienthoaiIntent = getIntent().getStringExtra("sodienthoai");
        String hotenIntent = getIntent().getStringExtra("hoten");

        btnBack =
                findViewById(R.id.btnBack);
        imageView =
                findViewById(R.id.imageView);
        textView7 =
                findViewById(R.id.textView7);

        textView7.setText("Nội dung: "+madonIntent+" "+hotenIntent+" "+sodienthoaiIntent);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, DetailOrder.class);
                intent.putExtra("madon",madonIntent);
                intent.putExtra("hoten",hotenIntent);
                intent.putExtra("sodienthoai",sodienthoaiIntent);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Qr.class);
                intent.putExtra("madon",madonIntent);
                intent.putExtra("hoten",hotenIntent);
                intent.putExtra("sodienthoai",sodienthoaiIntent);
                startActivity(intent);
            }
        });
    }
}