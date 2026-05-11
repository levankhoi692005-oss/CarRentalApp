package com.example.carrentalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.OrderAdapter;
import com.example.carrentalapp.database.DatabaseHelper;
import com.example.carrentalapp.model.Order;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerOrder;
    ImageView btnBack, btnHome, btnCart, btnMenu;

    DatabaseHelper db;
    ArrayList<Order> list;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // ánh xạ
        recyclerOrder = findViewById(R.id.recyclerOrder);
        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome1);
        btnCart = findViewById(R.id.btnCart1);
        btnMenu = findViewById(R.id.btnMenu1);

        // setup recycler
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        // 🔥 lấy dữ liệu đúng
        list = db.getalldatxe();

        adapter = new OrderAdapter(this, list);
        recyclerOrder.setAdapter(adapter);

        // back
        btnBack.setOnClickListener(v -> finish());

        // home
        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(Cart.this, Home.class));
        });

        // menu
        btnMenu.setOnClickListener(v -> {
            startActivity(new Intent(Cart.this, Menu.class));
        });
    }
}