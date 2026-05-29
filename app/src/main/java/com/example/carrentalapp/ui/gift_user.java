package com.example.carrentalapp.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalapp.R;

public class gift_user extends AppCompatActivity {
    ImageView btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gift_user);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {

            finish();
        });
    }
}