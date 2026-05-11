package com.example.carrentalapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.model.Vehicle;
import com.example.carrentalapp.ui.CustomerInfo;

import java.util.List;

public class VehicleAdapter
        extends RecyclerView.Adapter<VehicleAdapter.XeViewHolder> {

    Context context;
    List<Vehicle> list;

    // =========================
    // CONSTRUCTOR
    // =========================

    public VehicleAdapter(Context context, List<Vehicle> list) {

        this.context = context;
        this.list = list;

    }

    // =========================
    // CREATE VIEW
    // =========================

    @NonNull
    @Override
    public XeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.activity_admin_item_vehicle,
                        parent,
                        false
                );

        return new XeViewHolder(view);

    }

    // =========================
    // BIND DATA
    // =========================

    @Override
    public void onBindViewHolder(
            @NonNull XeViewHolder holder,
            int position
    ) {

        Vehicle xe = list.get(position);

        // =========================
        // LOAD IMAGE
        // =========================

        Glide.with(context)
                .load(xe.getHinh1())
                .into(holder.img1);

        Glide.with(context)
                .load(xe.getHinh2())
                .into(holder.img2);

        // =========================
        // SET TEXT
        // =========================

        holder.txtTen.setText(xe.getTen());

        holder.txtGia.setText(
                xe.getGia() + " VNĐ"
        );

        holder.txtBienSo.setText(
                xe.getBienso()
        );

        holder.txtThongtin.setText(
                xe.getMota()
        );

        // =========================
        // BUTTON THUÊ
        // =========================

        holder.thue.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            CustomerInfo.class
                    );

            intent.putExtra(
                    "anhxe",
                    xe.getHinh1()
            );

            intent.putExtra(
                    "tenxe",
                    xe.getTen()
            );

            intent.putExtra(
                    "gia",
                    xe.getGia()
            );

            intent.putExtra(
                    "bienso",
                    xe.getBienso()
            );

            context.startActivity(intent);

        });

    }

    // =========================
    // ITEM COUNT
    // =========================

    @Override
    public int getItemCount() {

        return list.size();

    }

    // =========================
    // VIEW HOLDER
    // =========================

    public static class XeViewHolder
            extends RecyclerView.ViewHolder {

        ImageView img1, img2;

        TextView txtTen,
                txtGia,
                txtBienSo,
                txtThongtin;

        Button thue, xemthem;

        public XeViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            img1 =
                    itemView.findViewById(R.id.imgxe1);

            img2 =
                    itemView.findViewById(R.id.imgxe2);

            txtTen =
                    itemView.findViewById(R.id.txtTenXe);

            txtGia =
                    itemView.findViewById(R.id.txtGia);

            txtBienSo =
                    itemView.findViewById(R.id.txtBienSo);

            txtThongtin =
                    itemView.findViewById(R.id.txtThongTin);

            thue =
                    itemView.findViewById(R.id.btnThue);

            xemthem =
                    itemView.findViewById(R.id.btnXemThem);

        }

    }

}