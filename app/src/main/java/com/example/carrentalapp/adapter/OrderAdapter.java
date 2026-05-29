package com.example.carrentalapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.admin.AdminApproval;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Order;
import com.example.carrentalapp.ui.DetailOrder;
import com.example.carrentalapp.ui.Vehicle_Image_View;

import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.jar.JarEntry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<Order> list;

    String hinh1 ="";

    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenXe, txtGia, txtBienSo, txtNgayDangKy, txtNgayLay,
                txtSoNgay, txtThanhTien, txtTinhTrang, txtThanhToan, txtmadon;

        Button btnXemThem, btnHuy;
        ImageView imgXe;

        public ViewHolder(View itemView) {
            super(itemView);

            imgXe = itemView.findViewById(R.id.imgXe);
            txtmadon = itemView.findViewById(R.id.txtmadon);

            txtTenXe = itemView.findViewById(R.id.txtTenXe);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtBienSo = itemView.findViewById(R.id.txtBienSo);
            txtNgayDangKy = itemView.findViewById(R.id.txtNgayDangKy);
            txtNgayLay = itemView.findViewById(R.id.txtNgayLay);
            txtSoNgay = itemView.findViewById(R.id.txtSoNgay);
            txtThanhTien = itemView.findViewById(R.id.txtThanhTien);
            txtTinhTrang = itemView.findViewById(R.id.txtTinhTrang);
            txtThanhToan = itemView.findViewById(R.id.txtThanhToan);

            btnXemThem = itemView.findViewById(R.id.btnXemThem);
            btnHuy = itemView.findViewById(R.id.btnHuy);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_cart_vehicle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        Order o = list.get(position);

        // format tiền
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

        ApiService.getimagevehicle(
                o.getBienso(),
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

                        ((Activity) context).runOnUiThread(()->
                        {
                            try{
                                JSONObject jsonObject = new JSONObject(result);
                                hinh1 = jsonObject.getString("hinhxe1");
                                Glide.with(context).load(hinh1).into(h.imgXe);

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        });
                    }
                }
        );



        // 👉 hiển thị
        h.txtmadon.setText("Mã đơn: "+ o.getMadon());
        h.txtTenXe.setText("Xe: " + o.getTenxe()); // nếu chưa có tên xe thì dùng biển
        h.txtGia.setText(nf.format(o.getDongia()) + "đ/ngày");

        h.txtBienSo.setText("Biển số: " + o.getBienso());

        h.txtNgayDangKy.setText("Đăng ký: " + o.getNgaydat());
        h.txtNgayLay.setText("Lấy : " + o.getNgaylay());

        h.txtSoNgay.setText("Thuê: " + o.getSongaythue() + " ngày");

        h.txtThanhTien.setText("Tổng: " + nf.format(o.getThanhtien()) + "VNĐ");

        h.txtThanhToan.setText(o.getThanhtoan());

        h.txtTinhTrang.setText(o.getTinhtrang());

        // 🔥 đổi màu trạng thái
        if (o.getTinhtrang().equalsIgnoreCase("Chưa phê duyệt")) {
            h.txtTinhTrang.setTextColor(0xFFFF9800); // cam
        } else {
            h.txtTinhTrang.setTextColor(0xFF4CAF50); // xanh
        }

        // 🔥 nút chi tiết
        h.btnXemThem.setOnClickListener(v -> {
            Toast.makeText(context, "Xem chi tiết đơn: " + o.getMadon(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailOrder.class);
            intent.putExtra("madon",o.getMadon());
            context.startActivity(intent);
        });

        if(o.getTinhtrang().equals("Đã hủy"))
        {
            h.btnHuy.setText("Đã hủy");
        }

        // 🔥 nút hủy
        h.btnHuy.setOnClickListener(v -> {

            if(o.getTinhtrang().equals("Chờ xác nhận"))
            {
                ApiService.UpdatOrder(
                        o.getMadon(),
                        "Đã hủy",
                        o.getThanhtoan(),
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Toast.makeText(
                                        context,
                                        "Lỗi mạng",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                String result = response.body().string();
                                ((Activity) context).runOnUiThread(()->
                                {
                                    try {
                                        JSONObject object = new JSONObject(result);

                                        boolean success = object.getBoolean("success");
                                        if(success) {
                                            h.btnHuy.setText("Đã hủy");

                                            Toast.makeText(

                                                    context,

                                                    "Đã hủy đơn",

                                                    Toast.LENGTH_SHORT

                                            ).show();

                                        }
                                        else {
                                            Toast.makeText(

                                                    context,

                                                    object.getString("message"),

                                                    Toast.LENGTH_SHORT

                                            ).show();

                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        });
            }



        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}