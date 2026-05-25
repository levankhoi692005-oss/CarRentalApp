package com.example.carrentalapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.ApprovalOrderAdapter;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdminViewOrder extends AppCompatActivity {

    TextView btnTatCa,btnChoDuyet,btnDaDuyet,btnDaHuy;

    EditText edtSearch;

    ImageView btnBack;
    RecyclerView recyclerView;


    ArrayList<Order> list,listsearch;
    ApprovalOrderAdapter approvalOrderAdapter;


    ArrayList<String> listmadon;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_approval);


        recyclerView = findViewById(R.id.recyclerOrder);

        btnTatCa = findViewById(R.id.btnTatCa);
        btnChoDuyet = findViewById(R.id.btnChoDuyet);
        btnDaDuyet = findViewById(R.id.btnDaDuyet);
        btnDaHuy = findViewById(R.id.btnDaHuy);

        edtSearch = findViewById(R.id.edtSearch);
        btnBack = findViewById(R.id.btnBack);

        list = new ArrayList<>();
        listsearch = new ArrayList<>();


        listmadon = new ArrayList<>();




        approvalOrderAdapter = new ApprovalOrderAdapter(this,list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(approvalOrderAdapter);


        getmadon();

        evenclick();
        search();


    }

    private void getmadon()
    {
        ApiService.getallmadon(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(
                                AdminViewOrder.this,
                                "Lỗi kết nối server",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

                        runOnUiThread(()->
                        {

                            try {

                                JSONObject jsonObject = new JSONObject(result);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Boolean success = jsonObject.getBoolean("success");

                                if(success)
                                {
                                    for ( int i =0 ; i< jsonArray.length();i++)
                                    {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String madon = object.getString("madon");
                                        listmadon.add(madon);
                                        getorder(madon);
                                    }

                                }
                                else {
                                    Toast.makeText(
                                            AdminViewOrder.this,
                                            "Lỗi truy xuất dữ liệu",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }

                            }
                            catch (Exception e)
                            {


                            }
                        });



                    }
                }
        );


    }
    private void getorder(String madon)
    {



        ApiService.getdetailorder(
                madon,
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(
                                AdminViewOrder.this,
                                "Lỗi kết nối server",
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if(response.body() == null)
                        {
                            return;
                        }
                        String result = response.body().string();

                        runOnUiThread(()->
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(jsonArray.length()==0)
                                {
                                    Toast.makeText(AdminViewOrder.this,
                                            "Chưa có đơn nào",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    for (int i =0 ; i< jsonArray.length(); i++)
                                    {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Order order = new Order(object.getInt("id"),

                                                object.getString("madon"),

                                                object.getString("hoten"),

                                                object.getString("bienso"),

                                                object.getString("sodienthoai"),

                                                object.getString("ngaydat"),

                                                object.getString("ngaylay"),

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
                                        listsearch.add(order);



                                    }
                                    approvalOrderAdapter.notifyDataSetChanged();

                                }



                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        });


                    }
                }
        );


    }
    private void evenclick() {

        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list.addAll(listsearch);
                approvalOrderAdapter.notifyDataSetChanged();


            }
        });

        btnChoDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                for (Order order : listsearch) {
                    if (order.getTinhtrang().equals("Chờ xác nhận")) {
                        list.add(order);
                    }

                }
                approvalOrderAdapter.notifyDataSetChanged();
            }
        });

        btnDaDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                for (Order order : listsearch) {
                    if (order.getTinhtrang().equals("Đã duyệt")) {
                        list.add(order);
                    }

                }
                approvalOrderAdapter.notifyDataSetChanged();
            }
        });

        btnDaHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                for (Order order : listsearch) {
                    if (order.getTinhtrang().equals("Đã hủy")||order.getTinhtrang().equals("Đã từ chối")) {
                        list.add(order);
                    }

                }
                approvalOrderAdapter.notifyDataSetChanged();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           startActivity(new Intent(AdminViewOrder.this, AdminVehicleManagement.class));

                                       }
                                   }
        );
    }


    private void search()
    {

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
                for ( Order order : listsearch)
                {
                    if(order.getMadon().toLowerCase().
                            contains(charSequence.toString().toLowerCase()))
                    {
                        list.add(order);


                    }
                    approvalOrderAdapter.notifyDataSetChanged();
                }



            }
        });

    }
}
