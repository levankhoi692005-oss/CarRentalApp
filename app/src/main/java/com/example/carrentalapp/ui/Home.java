package com.example.carrentalapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.VehicleAdapter;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Home extends AppCompatActivity {

    RecyclerView recyclerVehicle;

    VehicleAdapter adapter;


    EditText edtSearch;

    ArrayList<Vehicle> listsearch;
    ArrayList<Vehicle> list;

    ImageView btnCart, btnMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home);

        edtSearch = findViewById(R.id.edtSearch);

        recyclerVehicle =
                findViewById(R.id.recyclerViewXe);

        btnCart =
                findViewById(R.id.btnCart);

        btnMenu =
                findViewById(R.id.btnMenu);

        list = new ArrayList<>();
        listsearch = new ArrayList<>();


        adapter =
                new VehicleAdapter(
                        this,
                        list
                );

        recyclerVehicle.setLayoutManager(
                new GridLayoutManager(
                        this,
                        1
                )
        );


        // lay du lieu intenput tu login -> home





        recyclerVehicle.setAdapter(adapter);

        // ==========================
        // LOAD DỮ LIỆU XE
        // ==========================

        loadVehicles();

        // ==========================
        // CART
        // ==========================

        btnCart.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            Cart.class
                    )
            );

        });

        // ==========================
        // MENU
        // ==========================

        btnMenu.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            Menu.class
                    )
            );

        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list.clear();
                for(Vehicle xe : listsearch)
                {
                    if(xe.getTen().toLowerCase().contains(
                            charSequence.toString().toLowerCase()
                    )){
                        list.add(xe);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

    }

    // =================================
    // LOAD VEHICLES
    // =================================




    private void loadVehicles(){

        ApiService.getVehicles(

                new Callback() {

                    @Override
                    public void onFailure(
                            Call call,
                            java.io.IOException e
                    ) {

                        runOnUiThread(() ->

                                Toast.makeText(
                                        Home.this,
                                        "Lỗi API",
                                        Toast.LENGTH_SHORT
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

                        try {

                            JSONArray array =
                                    new JSONArray(result);

                            list.clear();

                            for(int i = 0; i < array.length(); i++){

                                JSONObject object =
                                        array.getJSONObject(i);

                                Vehicle vehicle =
                                        new Vehicle(

                                                object.getInt("id"),

                                                object.getString("hinhxe1"),

                                                object.getString("hinhxe2"),

                                                object.getString("tenxe"),

                                                object.getString("bienso"),

                                                object.getInt("dongia"),

                                                object.getString("mota")

                                        );

                                list.add(vehicle);
                                listsearch.add(vehicle);

                            }

                            runOnUiThread(() -> {

                                adapter.notifyDataSetChanged();

                            });

                        }
                        catch (Exception e){

                            e.printStackTrace();

                        }

                    }

                }

        );

    }

}