package com.example.carrentalapp.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.model.Vehicle;

import java.util.List;

public class AdminVehicleAdapter extends RecyclerView.Adapter<AdminVehicleAdapter.adminxeHolder> {
    List<Vehicle> list;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    OnItemClickListener listener;

    public AdminVehicleAdapter (List<Vehicle> list, OnItemClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminVehicleAdapter.adminxeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_admin_vehicle_list,parent,false);
        return new adminxeHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdminVehicleAdapter.adminxeHolder holder, int position) {
        Vehicle xe = list.get(position);

        Glide.with(holder.itemView.getContext())
                .load(xe.getHinh1())
                .into(holder.anh1);

        Glide.with(holder.itemView.getContext())
                .load(xe.getHinh2())
                .into(holder.anh2);

        holder.txttenxe.setText("Tên xe: " + xe.getTen());
        holder.txtbienso.setText("Biển số: " + xe.getBienso());
        holder.txtgia.setText("Đơn giá: " + xe.getGia());
        holder.txtsoghe.setText("Ghế: "+xe.getSoghe());
        holder.txtnhienlieu.setText("Nhiên liệu: "+xe.getNhienlieu());
        holder.txthopso.setText("Hộp số: "+xe.getHopso());
        holder.itemView.setOnClickListener(v ->
        {
            int position1 = holder.getAdapterPosition();
            listener.OnItemClick(position1);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class adminxeHolder extends RecyclerView.ViewHolder {
        ImageView anh1,anh2;
        TextView txttenxe,txtbienso,txtgia,txtmota;
        TextView txtsoghe,
                txthopso,
                txtnhienlieu;

        public adminxeHolder(@NonNull View itemView) {
            super(itemView);
            anh1 = itemView.findViewById(R.id.imgxe1);
            anh2 = itemView.findViewById(R.id.imgxe2);
            txttenxe = itemView.findViewById(R.id.txtTenXe);
            txtgia = itemView.findViewById(R.id.txtGia);
            txtbienso = itemView.findViewById(R.id.txtBienSo);
            txtsoghe = itemView.findViewById(R.id.txtSoGhe);
            txtnhienlieu = itemView.findViewById(R.id.txtNhienLieu);
            txthopso = itemView.findViewById(R.id.txtHopSo);



        }
    }
}
