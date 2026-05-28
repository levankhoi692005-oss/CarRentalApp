package com.example.carrentalapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Vehicle;
import com.example.carrentalapp.ui.CustomerInfo;
import com.example.carrentalapp.ui.DetailVehicle;
import com.example.carrentalapp.ui.Vehicle_Image_View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VehicleAdapter
        extends RecyclerView.Adapter<VehicleAdapter.XeViewHolder> {

    Context context;
    List<Vehicle> list;

    String tinhtrang="";
    String ngaytraxe="";

    int dem=0;
    public VehicleAdapter(Context context, List<Vehicle> list) {

        this.context = context;
        this.list = list;

    }


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


    @Override
    public void onBindViewHolder(
            @NonNull XeViewHolder holder,
            int position
    ) {

        Vehicle xe = list.get(position);

        Glide.with(context)
                .load(xe.getHinh1())
                .into(holder.img1);

        Glide.with(context)
                .load(xe.getHinh2())
                .into(holder.img2);

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi","VN"));


        ApiService.getallmadon(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        ((Activity ) context).runOnUiThread(()->
                        {
                            Toast.makeText(
                                    context,
                                    "Lỗi mạng",
                                    Toast.LENGTH_SHORT
                            ).show();
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

                        ((Activity ) context).runOnUiThread(()->
                        {

                            try{
                                JSONObject jsonObject =  new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                boolean success = jsonObject.getBoolean("success");
                                if(success)
                                {
                                    for (int i = 0; i<jsonArray.length();i++)
                                    {
                                       JSONObject object = jsonArray.getJSONObject(i);
                                       if(object.getString("tinhtrang").equals("Đã duyệt"))
                                       {
                                           dem+=1;
                                           ngaytraxe = object.getString("ngaytraxe");
                                           return;

                                       }
                                    }

                                }
                                else
                                {
                                    ((Activity ) context).runOnUiThread(()->
                                    {
                                        Toast.makeText(
                                                context,
                                                "Lỗi mạng",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    });
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

        holder.txtTen.setText("Tên xe: " + xe.getTen());
        holder.txtBienSo.setText("Biển số: " + xe.getBienso());
        holder.txtGia.setText("Đơn giá: " + nf.format(xe.getGia()) +"VNĐ/Ngày");
        holder.txtSoGhe.setText("Ghế: "+xe.getSoghe());
        holder.txtnhienlieu.setText("Nhiên liệu: "+xe.getNhienlieu());
        holder.txtHopSo.setText("Hộp số: "+xe.getHopso());
        holder.txthanthue.setText("");



        if(dem>0)
        {
            holder.thue.setEnabled(false);
            holder.thue.setText("Đã được thuê");
            holder.txthanthue.setText("Ngày trả xe: "+ngaytraxe);
        }
        else
        {

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

                intent.putExtra(
                        "mota",
                        xe.getMota()
                );

                intent.putExtra(
                        "mau",
                        xe.getMau()
                );

                intent.putExtra(
                        "soghe",
                        xe.getSoghe()
                );

                intent.putExtra(
                        "nhienlieu",
                        xe.getNhienlieu()
                );

                intent.putExtra(
                        "hopso",
                        xe.getHopso()
                );

                intent.putExtra(
                        "congsuat",
                        xe.getCongsuat()
                );

                intent.putExtra(
                        "namsanxuat",
                        xe.getNamsanxuat()
                );


                context.startActivity(intent);

            });

        }


        holder.xemthem.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            DetailVehicle.class
                    );

            intent.putExtra("anh1", xe.getHinh1());
            intent.putExtra("anh2", xe.getHinh2());

            intent.putExtra("tenxe", xe.getTen());

            intent.putExtra("gia", xe.getGia());

            intent.putExtra("bienso", xe.getBienso());

            intent.putExtra("mota", xe.getMota());

            intent.putExtra("mau", xe.getMau());

            intent.putExtra("soghe", xe.getSoghe());

            intent.putExtra("nhienlieu", xe.getNhienlieu());

            intent.putExtra("hopso", xe.getHopso());

            intent.putExtra("congsuat", xe.getCongsuat());

            intent.putExtra(
                    "namsanxuat",
                    xe.getNamsanxuat()
            );

            context.startActivity(intent);

        });

        holder.img2.setOnClickListener(v->
        {
            Intent intent = new Intent(context, Vehicle_Image_View.class);
            intent.putExtra("hinh",xe.getHinh2());
            context.startActivity(intent);
        });

        holder.img1.setOnClickListener(v->
        {
            Intent intent = new Intent(context, Vehicle_Image_View.class);
            intent.putExtra("hinh",xe.getHinh1());
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {

        return list.size();

    }


    public static class XeViewHolder
            extends RecyclerView.ViewHolder {

        ImageView img1, img2;

        TextView txtTen,
                txtGia,
                txtBienSo;

        TextView
                txtSoGhe,
                txtHopSo,
                txtnhienlieu,
                txthanthue;

        Button thue, xemthem;

        public XeViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);
            txthanthue =itemView.findViewById(R.id.txthanthue);

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

            thue =
                    itemView.findViewById(R.id.btnThue);

            xemthem =
                    itemView.findViewById(R.id.btnXemThem);

            txtSoGhe =
                    itemView.findViewById(R.id.txtSoGhe);

            txtHopSo =
                    itemView.findViewById(R.id.txtHopSo);

            txtnhienlieu =
                    itemView.findViewById(R.id.txtNhienLieu);

        }

    }

}