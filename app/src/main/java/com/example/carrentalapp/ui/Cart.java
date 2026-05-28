package com.example.carrentalapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.OrderAdapter;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerOrder;

    ImageView
            btnHome,
            btnCart,
            btnMenu;

    ProgressBar progressBar;

    LinearLayout layoutEmpty;

    ArrayList<Order> list;

    OrderAdapter adapter;


    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        initViews();

        getUserId();

        setupRecycler();

        loadOrders();

        clickEvents();

    }


    // INIT VIEW


    private void initViews(){

        recyclerOrder =
                findViewById(R.id.recyclerOrder);



        btnHome =
                findViewById(R.id.btnHome1);

        btnCart =
                findViewById(R.id.btnCart1);

        btnMenu =
                findViewById(R.id.btnMenu1);

        progressBar =
                findViewById(R.id.progressBar);

        layoutEmpty =
                findViewById(R.id.layoutEmpty);

    }


    // GET USER ID


    private void getUserId(){

        SharedPreferences preferences =
                getSharedPreferences(
                        "USER_FILE",
                        MODE_PRIVATE
                );

        userId =
                preferences.getInt(
                        "user_id",
                        -1
                );

    }


    // SETUP RECYCLER


    private void setupRecycler(){

        list = new ArrayList<>();

        adapter =
                new OrderAdapter(
                        this,
                        list
                );

        recyclerOrder.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerOrder.setAdapter(adapter);

    }


    // LOAD ORDERS MYSQL


    private void loadOrders(){

        progressBar.setVisibility(View.VISIBLE);

        ApiService.getOrdersByUserId(

                userId,

                new Callback() {

                    @Override
                    public void onFailure(
                            Call call,
                            IOException e
                    ) {

                        runOnUiThread(() -> {

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(
                                    Cart.this,
                                    "Không thể kết nối server",
                                    Toast.LENGTH_SHORT
                            ).show();

                        });

                    }

                    @Override
                    public void onResponse(
                            Call call,
                            Response response
                    ) throws IOException {

                        String result =
                                response.body().string();

                        runOnUiThread(() -> {

                            try {

                                progressBar.setVisibility(View.GONE);

                                JSONArray array =
                                        new JSONArray(result);

                                list.clear();

                                // EMPTY
                                if(array.length() == 0){

                                    layoutEmpty.setVisibility(View.VISIBLE);

                                }
                                else{

                                    layoutEmpty.setVisibility(View.GONE);

                                }

                                for(int i = 0;
                                    i < array.length();
                                    i++){

                                    JSONObject object =
                                            array.getJSONObject(i);

                                    Order order =
                                            new Order(
                                                    object.getInt("id"),

                                                    object.getString("madon"),

                                                    object.getString("hoten"),

                                                    object.getString("bienso"),

                                                    object.getString("sodienthoai"),

                                                    object.getString("ngaydat_format"),

                                                    object.getString("ngaylay_format"),

                                                    object.getInt("songaythue"),

                                                    object.getInt("dongia"),

                                                    object.getInt("thanhtien"),

                                                    object.getString("thanhtoan"),

                                                    object.getString("tinhtrang"),

                                                    object.getString("ghichu"),

                                                    object.getInt("user_id"),

                                                    object.getString("tenxe"),

                                                    object.getString("diachikh"),

                                                    object.getString("diachinhan")

                                            );

                                    list.add(order);

                                }

                                adapter.notifyDataSetChanged();

                            }
                            catch (Exception e){

                                e.printStackTrace();

                                Toast.makeText(
                                        Cart.this,
                                        e.toString(),
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        });

                    }

                }

        );

    }

    // CLICK EVENTS


    private void clickEvents(){



        btnHome.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            Cart.this,
                            Home.class
                    )
            );

        });

        btnMenu.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            Cart.this,
                            Menu.class
                    )
            );

        });


    }

}