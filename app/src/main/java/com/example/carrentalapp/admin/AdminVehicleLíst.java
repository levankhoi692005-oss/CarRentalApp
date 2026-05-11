package com.example.carrentalapp.admin;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.model.Vehicle;

import java.util.List;

public class AdminVehicleLíst extends RecyclerView.Adapter<AdminVehicleLíst.adminxeHolder> {
    List<Vehicle> list;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    OnItemClickListener listener;

    public AdminVehicleLíst (List<Vehicle> list, OnItemClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminVehicleLíst.adminxeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_admin_item_vehicle,parent,false);
        return new adminxeHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdminVehicleLíst.adminxeHolder holder, int position) {
        Vehicle xe = list.get(position);
        holder.anh1.setImageURI(Uri.parse(xe.getHinh1()));
        holder.anh2.setImageURI(Uri.parse(xe.getHinh2()));
        holder.txttenxe.setText("Tên xe: " + xe.getTen());
        holder.txtbienso.setText("Biển số: " + xe.getBienso());
        holder.txtgia.setText("Đơn giá: " + xe.getGia());
        holder.txtmota.setText("Mô tả: " + xe.getMota());
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

        public adminxeHolder(@NonNull View itemView) {
            super(itemView);
            anh1 = itemView.findViewById(R.id.imgxe1);
            anh2 = itemView.findViewById(R.id.imgxe2);
            txttenxe = itemView.findViewById(R.id.txtTenXe);
            txtbienso = itemView.findViewById(R.id.txtBienSo);
            txtgia = itemView.findViewById(R.id.txtGia);
            txtmota = itemView.findViewById(R.id.txtThongTin);

        }
    }
}
