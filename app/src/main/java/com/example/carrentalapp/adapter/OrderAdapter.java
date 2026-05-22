package com.example.carrentalapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Order;
import com.example.carrentalapp.model.Vehicle;
import com.example.carrentalapp.ui.DetailOrder;

import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<Order> list;

    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenXe, txtGia, txtBienSo, txtNgayDangKy, txtNgayLay,
                txtSoNgay, txtThanhTien, txtTinhTrang, txtThanhToan,txtmadon;

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



        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

        String ngay =
                o.getNgaylay()
                        .split("T")[0];
        SimpleDateFormat ngaydat = new SimpleDateFormat("YYYY-MM-DD",Locale.getDefault());
        SimpleDateFormat giodat = new SimpleDateFormat("HH:MM:SS",Locale.getDefault());

        String ngay_dat= ngaydat.format(new Date());
        String gio_dat = giodat.format(new Date());

        h.txtTenXe.setText("  " + o.getTenxe());
        h.txtGia.setText(nf.format(o.getDongia()) + "đ/ngày");
        h.txtmadon.setText("Mã đơn: "+o.getMadon());
        h.txtBienSo.setText("Biển số: " + o.getBienso());

        h.txtNgayDangKy.setText("Ngày đăng ký: " + ngay_dat+"-"+gio_dat);
        h.txtNgayLay.setText("Ngày lấy: " + ngay);

        h.txtSoNgay.setText("⏱ " + o.getSongaythue() + " ngày");

        h.txtThanhTien.setText("💰 " + nf.format(o.getThanhtien()) + "đ");

        h.txtThanhToan.setText(o.getThanhtoan());

        h.txtTinhTrang.setText(o.getTinhtrang());

        // đổi màu trạng thái
        if (o.getTinhtrang().equalsIgnoreCase("Chưa phê duyệt")) {
            h.txtTinhTrang.setTextColor(0xFFFF9800); // cam
        } else {
            h.txtTinhTrang.setTextColor(0xFF4CAF50); // xanh
        }



        // nút hủy
        h.btnHuy.setOnClickListener(v -> {
            ApiService.cancelOrder(
                    o.getId(),
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
                            ((Activity) context).runOnUiThread(()->{
                                try {
                                    JSONObject object = new JSONObject(result);

                                    boolean success = object.getBoolean("success");
                                    if(success) {
                                        h.txtTinhTrang.setText("Đã hủy");

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
                    }
            );



        }
        );


        h.btnXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(context, DetailOrder.class);

                intent.putExtra("getMadon",o.getMadon());
                intent.putExtra("getHoten",o.getHoten());
                intent.putExtra("getBienso",o.getBienso());
                intent.putExtra("getSodienthoai",o.getSodienthoai());
                intent.putExtra("getNgaydat",o.getNgaydat());
                intent.putExtra("getThanhtoan",o.getThanhtoan());
                intent.putExtra("getNgaylay",o.getNgaylay());
                intent.putExtra("getTinhtrang",o.getTinhtrang());
                intent.putExtra("getGhichu",o.getGhichu());
                intent.putExtra("getSongaythue",o.getSongaythue());
                intent.putExtra("getDongia",o.getDongia());
                intent.putExtra("getThanhtien",o.getThanhtien());
                intent.putExtra("getTenxe",o.getTenxe());
                intent.putExtra("getUser_id",o.getUser_id());
                intent.putExtra("getDiachikh",o.getDiachikh());
                intent.putExtra("getDiachinhan",o.getDiachinhan());

                context.startActivity(intent);


            }
        });

         // da duyet
        if(o.getTinhtrang().equals("Đã duyệt")){
            h.btnHuy.setEnabled(false);
            h.btnHuy.setText("Đã duyệt");
            h.btnHuy.setBackgroundTintList(
                    ColorStateList.valueOf(Color.GRAY)
            );
        }
        // da huy
        if(o.getTinhtrang().equals("Đã hủy")){
            h.btnHuy.setEnabled(false);
            h.btnHuy.setText("Đã hủy");
            h.btnHuy.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}