package com.example.carrentalapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapp.R;
import com.example.carrentalapp.admin.AdminApproval;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ApprovalOrderAdapter extends RecyclerView.Adapter<ApprovalOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<Order> list;

    public ApprovalOrderAdapter(Context context, ArrayList<Order> list)
    {
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public ApprovalOrderAdapter.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.activity_approval_item,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder
            (@NonNull ApprovalOrderAdapter.ViewHolder holder, int position) {

        Order o = list.get(position);

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi","VN"));

        ApiService.getdetailorder(
                o.getMadon(),
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        ((Activity) context).runOnUiThread(() -> {

                            Toast.makeText(

                                    context,

                                    "Lỗi kết nối server",

                                    Toast.LENGTH_SHORT

                            ).show();

                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();
                        ((Activity) context).runOnUiThread(()->
                        {

                            try {

                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject object = jsonArray.getJSONObject(0);


                                boolean success = jsonObject.getBoolean("success");

                                if ( success)
                                {
                                    String hinh1 = object.getString("hinhxe1");

                                    holder.txtTenXe.setText(o.getTenxe());
                                    holder.txtthanhtoan.setText(o.getThanhtoan());
                                    holder.txtBienSo.setText("Biển số: " + o.getBienso());
                                    holder.txtMadon.setText("Mã Đơn: "+o.getMadon());
                                    holder.txtGia.setText("Giá: "+o.getDongia() + "VNĐ/ngày");
                                    holder.txtTongTien.setText("Tổng tiền: "+nf.format(o.getThanhtien()));
                                    holder.txtTinhTrang.setText(o.getTinhtrang());

                                    Glide.with(context)
                                            .load(hinh1)
                                            .into(holder.imgXe);

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

        holder.xemchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminApproval.class);
                intent.putExtra("madon",o.getMadon());
                context.startActivity(intent);


            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenXe,
                txtGia,
                txtBienSo,
                txtTinhTrang,
                txtTongTien,
                txtMadon,
                txtthanhtoan;
        LinearLayout xemchitiet;



        ImageView imgXe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenXe =
                    itemView.findViewById(R.id.txttenxe);

            txtGia =
                    itemView.findViewById(R.id.txtGia);

            txtBienSo =
                    itemView.findViewById(R.id.txtBienSo);

            txtTinhTrang =
                    itemView.findViewById(R.id.txtTinhTrang);

            txtTongTien =
                    itemView.findViewById(R.id.txtThanhTien);

            txtMadon =
                    itemView.findViewById(R.id.txtmadon);

            txtthanhtoan =
                    itemView.findViewById(R.id.txtThanhToan);

            xemchitiet = itemView.findViewById(R.id.xemchitiet);


            imgXe =
                    itemView.findViewById(R.id.imgXe);


        }
    }
}