package com.example.carrentalapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.adapter.ChatAIAdapter;
import com.example.carrentalapp.api.ApiService;
import com.example.carrentalapp.model.Messgae;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.jar.JarEntry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChatAI extends AppCompatActivity {


    LinearLayout layoutHome,layoutchat,layoutCart,layoutMenu;

    ImageView btnsend;

    EditText edtmessage;

    RecyclerView viewitem;

    ArrayList<Messgae> list;

    ChatAIAdapter chatAIAdapter;
    String message_ai="";

    ArrayList<String> list_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_ai);


        viewitem = findViewById(R.id.viewitem);

        edtmessage = findViewById(R.id.edtmessage);

        btnsend = findViewById(R.id.btnsend);

        layoutHome = findViewById(R.id.layoutHome);
        layoutMenu = findViewById(R.id.layoutMenu);
        layoutCart = findViewById(R.id.layoutCart);

        list = new ArrayList<>();
        chatAIAdapter = new ChatAIAdapter(this,list);

        viewitem.setLayoutManager(new LinearLayoutManager(this));
        viewitem.setHasFixedSize(true);
        viewitem.setAdapter(chatAIAdapter);


        SharedPreferences sharedPreferences =
                getSharedPreferences("USER_FILE",
                        MODE_PRIVATE);



        String user_name = sharedPreferences.getString("username","");
        edtmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                message_ai = edtmessage.getText().toString();

            }
        });


        ApiService.getmessagechatai(
                user_name,
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        runOnUiThread(()->
                        {
                            Toast.makeText(
                                    ChatAI.this,
                                    "Không thể kết nối server",
                                    Toast.LENGTH_SHORT
                            ).show();
                        });

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        String result = response.body().string();

                        runOnUiThread(()->
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                boolean success = jsonObject.getBoolean("success");

                                if(success)
                                {

                                    for (int i = 0 ; i<jsonArray.length();i++)
                                    {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if(object.getString("user").equals("AI_"+user_name))
                                        {
                                            Messgae messgae_history = new Messgae(
                                                    "AI_"+user_name,
                                                    object.getString("message"),
                                                    object.getString("send_time_format")

                                            );
                                            list.add(messgae_history);
                                            chatAIAdapter.notifyItemInserted(
                                                    list.size()-1
                                            );
                                            viewitem.scrollToPosition(list.size()-1);

                                        } else if (object.getString("user").equals(user_name+"_AI")) {

                                            Messgae messgae_history = new Messgae(
                                                    user_name+"AI_",
                                                    object.getString("message"),
                                                    object.getString("send_time_format")

                                            );
                                            list.add(messgae_history);
                                            chatAIAdapter.notifyItemInserted(
                                                    list.size()-1
                                            );
                                            viewitem.scrollToPosition(list.size()-1);
                                        }

                                    }



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


        btnsend.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if( message_ai.isEmpty())
                {
                    runOnUiThread(() -> {

                        Toast.makeText(
                                ChatAI.this,
                                "Lỗi mạng",
                                Toast.LENGTH_SHORT
                        ).show();

                    });
                }
                else {

                    Messgae userMessage =
                            new Messgae(
                                    user_name+"_AI",
                                    message_ai,
                                    getdatetime()
                            );

                    list.add(userMessage);

                    chatAIAdapter.notifyItemInserted(
                            list.size() - 1
                    );
                    viewitem.scrollToPosition(list.size() - 1);
                    ApiService.messagechat(

                            message_ai,
                            user_name,
                            getdatetime(),
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    runOnUiThread(() -> {

                                        Toast.makeText(
                                                ChatAI.this,
                                                "Lỗi mạng",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                    });
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                    String result = response.body().string();
                                    runOnUiThread(()->
                                    {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            String message_answer_ai = jsonObject.getString("answer");
                                            Boolean success = jsonObject.getBoolean("success");
                                            if(!success)
                                            {
                                                Toast.makeText(ChatAI.this,"Lỗi ChatAI",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                            {


                                                Messgae messgae = new Messgae(
                                                        "AI_"+user_name,
                                                        jsonObject.getString("answer"),
                                                        getdatetime()
                                                );


                                                list.add(messgae);

                                                chatAIAdapter.notifyItemInserted(
                                                        list.size() - 1
                                                );
                                                viewitem.scrollToPosition(list.size() - 1);
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
                edtmessage.setText("");



            }
        });













        // Chuyen giao dien

        layoutCart.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            Cart.class
                    )
            );

        });


        layoutMenu.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            Menu.class
                    )
            );

        });

        layoutHome.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            this,
                            Home.class
                    )
            );

        });








    }

    String getdatetime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY_MM_dd HH:mm:ss",Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String send_time = simpleDateFormat.format(new Date());
        return send_time;
    }
}