package com.example.carrentalapp.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<Order> list;

    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenXe, txtGia, txtBienSo, txtNgayDangKy, txtNgayLay,
                txtSoNgay, txtThanhTien, txtTinhTrang, txtThanhToan;

        Button btnXemThem, btnHuy;
        ImageView imgXe;

        public ViewHolder(View itemView) {
            super(itemView);

            imgXe = itemView.findViewById(R.id.imgXe);

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

        // 👉 hiển thị
        h.txtTenXe.setText("Xe: " + o.getBienso()); // nếu chưa có tên xe thì dùng biển
        h.txtGia.setText(nf.format(o.getDongia()) + "đ/ngày");

        h.txtBienSo.setText("Biển số: " + o.getBienso());

        h.txtNgayDangKy.setText("📅 ĐK: " + o.getNgaydat());
        h.txtNgayLay.setText("🚗 Lấy: " + o.getNgaylay());

        h.txtSoNgay.setText("⏱ " + o.getSongaythue() + " ngày");

        h.txtThanhTien.setText("💰 " + nf.format(o.getThanhtien()) + "đ");

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
        });

        // 🔥 nút hủy
        h.btnHuy.setOnClickListener(v -> {
            list.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Đã hủy đơn", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}