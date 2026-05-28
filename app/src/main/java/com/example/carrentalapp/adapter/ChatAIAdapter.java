package com.example.carrentalapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.model.Messgae;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChatAIAdapter extends RecyclerView.Adapter<ChatAIAdapter.ViewHolder> {

    Context context;
    ArrayList<Messgae> list;



    public ChatAIAdapter(Context context, ArrayList<Messgae> list)
    {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getItemViewType(int position)
    {
        Messgae messgae = list.get(position);
        if(messgae.getName().startsWith("AI_"))
        {
            return 1;
        }
        return  0;
    }

    @NonNull
    @Override
    public ChatAIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType==1)
        {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.activity_chat_item_ai,
                            parent,false);
        }
        else
        {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.acticity_chat_user,
                            parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAIAdapter.ViewHolder holder, int position) {


        Messgae messgae = list.get(position);
        holder.txtmessage.setText(messgae.getMessage());
        holder.time.setText(messgae.getDate_time());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtmessage,time;
        LinearLayout layoutmessgae;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmessage = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            layoutmessgae = itemView.findViewById(R.id.layoutmessage);
        }
    }
}