package com.example.carrentalapp.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.AdminVehicleAdapter;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.authentication.Login;
import com.example.carrentalapp.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminVehicleManagement extends AppCompatActivity {

    EditText edittenxe, editbienso, editdongia, editmota;

    Button chonanh1, chonanh2, them, xoa, sua;

    ImageView anh1, anh2;

    TextView btnql;

    Uri uri1, uri2;

    List<Vehicle> list;

    RecyclerView recyclerView;

    AdminVehicleAdapter adapter;

    int selectposition = -1;

    interface UploadCallback{
        void onSuccess(String imageUrl);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle save) {

        super.onCreate(save);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_admin_vehicle_management);

        // ==========================
        // PERMISSION
        // ==========================

        if(android.os.Build.VERSION.SDK_INT >= 33){

            requestPermissions(

                    new String[]{
                            android.Manifest.permission.READ_MEDIA_IMAGES
                    },

                    1

            );

        }

        // ==========================
        // FINDVIEWBYID
        // ==========================

        edittenxe = findViewById(R.id.edittenxe);

        editbienso = findViewById(R.id.editbienso);

        editdongia = findViewById(R.id.editdongia);

        editmota = findViewById(R.id.editmota);

        chonanh1 = findViewById(R.id.btnChonAnh1);

        chonanh2 = findViewById(R.id.btnChonAnh2);

        them = findViewById(R.id.btnthem);

        xoa = findViewById(R.id.btnxoa);

        sua = findViewById(R.id.btnsua);

        anh1 = findViewById(R.id.img1);

        anh2 = findViewById(R.id.img2);

        btnql = findViewById(R.id.btnquaylaidangnhap);

        recyclerView = findViewById(R.id.recyclereditxe);

        // ==========================
        // RECYCLERVIEW
        // ==========================

        list = new ArrayList<>();

        adapter = new AdminVehicleAdapter(

                list,

                position -> {

                    selectposition = position;

                    Toast.makeText(

                            this,

                            "Đã chọn xe",

                            Toast.LENGTH_SHORT

                    ).show();

                }

        );

        recyclerView.setLayoutManager(

                new LinearLayoutManager(this)

        );

        recyclerView.setAdapter(adapter);

        loadVehicles();

        // ==========================
        // PICK IMAGE 1
        // ==========================

        ActivityResultLauncher<Intent> pickImage1 =

                registerForActivityResult(

                        new ActivityResultContracts.StartActivityForResult(),

                        result -> {

                            if(result.getResultCode() == RESULT_OK
                                    && result.getData() != null){

                                uri1 =
                                        result.getData().getData();

                                anh1.setImageURI(uri1);

                            }

                        }

                );

        // ==========================
        // PICK IMAGE 2
        // ==========================

        ActivityResultLauncher<Intent> pickImage2 =

                registerForActivityResult(

                        new ActivityResultContracts.StartActivityForResult(),

                        result -> {

                            if(result.getResultCode() == RESULT_OK
                                    && result.getData() != null){

                                uri2 =
                                        result.getData().getData();

                                anh2.setImageURI(uri2);

                            }

                        }

                );

        // ==========================
        // CHỌN ẢNH 1
        // ==========================

        chonanh1.setOnClickListener(v -> {

            Intent i =
                    new Intent(Intent.ACTION_PICK);

            i.setType("image/*");

            pickImage1.launch(i);

        });

        // ==========================
        // CHỌN ẢNH 2
        // ==========================

        chonanh2.setOnClickListener(v -> {

            Intent i =
                    new Intent(Intent.ACTION_PICK);

            i.setType("image/*");

            pickImage2.launch(i);

        });

        // ==========================
        // THÊM XE
        // ==========================

        them.setOnClickListener(v -> {

            String tenxe =
                    edittenxe.getText().toString().trim();

            String bienso =
                    editbienso.getText().toString().trim();

            String mota =
                    editmota.getText().toString().trim();

            String gia =
                    editdongia.getText().toString().trim();

            if(tenxe.isEmpty()
                    || bienso.isEmpty()
                    || mota.isEmpty()
                    || gia.isEmpty()
                    || uri1 == null
                    || uri2 == null){

                Toast.makeText(

                        this,

                        "Nhập đầy đủ",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            int dongia =
                    Integer.parseInt(gia);

            uploadCloudinary(uri1, url1 -> {

                uploadCloudinary(uri2, url2 -> {

                    ApiService.addVehicle(

                            url1,

                            url2,

                            tenxe,

                            bienso,

                            dongia,

                            mota,

                            new Callback() {

                                @Override
                                public void onFailure(
                                        Call call,
                                        java.io.IOException e
                                ) {

                                    runOnUiThread(() ->

                                            Toast.makeText(

                                                    AdminVehicleManagement.this,

                                                    "Lỗi API",

                                                    Toast.LENGTH_SHORT

                                            ).show()

                                    );

                                }

                                @Override
                                public void onResponse(
                                        Call call,
                                        Response response
                                ){

                                    runOnUiThread(() -> {

                                        Toast.makeText(

                                                AdminVehicleManagement.this,

                                                "Thêm xe thành công",

                                                Toast.LENGTH_SHORT

                                        ).show();

                                        loadVehicles();

                                    });

                                }

                            }

                    );

                });

            });

        });

        // ==========================
        // XÓA XE
        // ==========================

        xoa.setOnClickListener(v -> {

            if(selectposition == -1){

                Toast.makeText(

                        this,

                        "Chọn xe trước",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            Vehicle xe =
                    list.get(selectposition);

            ApiService.deleteVehicle(

                    xe.getStt(),

                    new Callback() {

                        @Override
                        public void onFailure(
                                Call call,
                                java.io.IOException e
                        ) {

                        }

                        @Override
                        public void onResponse(
                                Call call,
                                Response response
                        ){

                            runOnUiThread(() -> {

                                Toast.makeText(

                                        AdminVehicleManagement.this,

                                        "Xóa thành công",

                                        Toast.LENGTH_SHORT

                                ).show();

                                loadVehicles();

                            });

                        }

                    }

            );

        });

        // ==========================
        // SỬA XE
        // ==========================

        sua.setOnClickListener(v -> {

            if(selectposition == -1){

                Toast.makeText(

                        this,

                        "Chọn xe trước",

                        Toast.LENGTH_SHORT

                ).show();

                return;

            }

            Vehicle xe =
                    list.get(selectposition);

            String tenxe =
                    edittenxe.getText().toString().trim();

            String bienso =
                    editbienso.getText().toString().trim();

            String mota =
                    editmota.getText().toString().trim();

            String gia =
                    editdongia.getText().toString().trim();

            if(gia.isEmpty()){

                return;

            }

            int dongia =
                    Integer.parseInt(gia);

            if(uri1 != null){

                uploadCloudinary(uri1, url1 -> {

                    if(uri2 != null){

                        uploadCloudinary(uri2, url2 -> {

                            updateVehicle(

                                    xe,

                                    url1,

                                    url2,

                                    tenxe,

                                    bienso,

                                    dongia,

                                    mota

                            );

                        });

                    }

                });

            }

        });

        // ==========================
        // QUAY LẠI
        // ==========================

        btnql.setOnClickListener(v -> {

            startActivity(

                    new Intent(

                            this,

                            Login.class

                    )

            );

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

                                        AdminVehicleManagement.this,

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

    // =================================
    // UPDATE VEHICLE
    // =================================

    private void updateVehicle(

            Vehicle xe,

            String hinh1,

            String hinh2,

            String tenxe,

            String bienso,

            int dongia,

            String mota

    ){

        ApiService.updateVehicle(

                xe.getStt(),

                hinh1,

                hinh2,

                tenxe,

                bienso,

                dongia,

                mota,

                new Callback() {

                    @Override
                    public void onFailure(
                            Call call,
                            java.io.IOException e
                    ) {

                    }

                    @Override
                    public void onResponse(
                            Call call,
                            Response response
                    ){

                        runOnUiThread(() -> {

                            Toast.makeText(

                                    AdminVehicleManagement.this,

                                    "Cập nhật thành công",

                                    Toast.LENGTH_SHORT

                            ).show();

                            loadVehicles();

                        });

                    }

                }

        );

    }

    // =================================
    // CLOUDINARY
    // =================================

    private void uploadCloudinary(

            Uri uri,

            UploadCallback callback

    ){

        try {

            InputStream inputStream =

                    getContentResolver()
                            .openInputStream(uri);

            byte[] imageBytes =

                    new byte[inputStream.available()];

            inputStream.read(imageBytes);

            RequestBody requestBody =

                    new MultipartBody.Builder()

                            .setType(MultipartBody.FORM)

                            .addFormDataPart(

                                    "file",

                                    "image.jpg",

                                    RequestBody.create(

                                            imageBytes,

                                            MediaType.parse("image/*")

                                    )

                            )

                            .addFormDataPart(

                                    "upload_preset",

                                    "Upload_Picture"

                            )

                            .build();

            Request request =

                    new Request.Builder()

                            .url("https://api.cloudinary.com/v1_1/daooi67hh/image/upload")

                            .post(requestBody)

                            .build();

            OkHttpClient client =
                    new OkHttpClient();

            client.newCall(request)
                    .enqueue(new Callback() {

                        @Override
                        public void onFailure(
                                Call call,
                                java.io.IOException e
                        ) {

                            runOnUiThread(() ->

                                    Toast.makeText(

                                            AdminVehicleManagement.this,

                                            "Upload lỗi",

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

                                JSONObject json =
                                        new JSONObject(result);

                                String imageUrl =
                                        json.getString("secure_url");

                                runOnUiThread(() ->

                                        callback.onSuccess(imageUrl)

                                );

                            }
                            catch (Exception e){

                                e.printStackTrace();

                            }

                        }

                    });

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }

}