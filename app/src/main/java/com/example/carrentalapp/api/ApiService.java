package com.example.carrentalapp.api;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiService {

    private static final String BASE_URL =
            "https://jonnie-unpoetic-coldly.ngrok-free.dev/";

    private static final OkHttpClient client =
            new OkHttpClient();

    // ==============================
    // GET VEHICLES
    // ==============================

    public static void getVehicles(
            Callback callback
    ){

        Request request =
                new Request.Builder()
                        .url(BASE_URL + "vehicles")
                        .build();

        client.newCall(request)
                .enqueue(callback);

    }

    // ==============================
    // ADD VEHICLE
    // ==============================

    public static void addVehicle(

            String hinhxe1,
            String hinhxe2,
            String tenxe,
            String bienso,
            int dongia,
            String mota,

            Callback callback

    ){

        String json = "{"

                + "\"hinhxe1\":\"" + hinhxe1 + "\","

                + "\"hinhxe2\":\"" + hinhxe2 + "\","

                + "\"tenxe\":\"" + tenxe + "\","

                + "\"bienso\":\"" + bienso + "\","

                + "\"dongia\":" + dongia + ","

                + "\"mota\":\"" + mota + "\""

                + "}";

        RequestBody body =
                RequestBody.create(
                        json,
                        MediaType.parse(
                                "application/json"
                        )
                );

        Request request =
                new Request.Builder()
                        .url(BASE_URL + "addvehicle")
                        .post(body)
                        .build();

        client.newCall(request)
                .enqueue(callback);

    }

    // ==============================
    // DELETE VEHICLE
    // ==============================

    public static void deleteVehicle(
            int id,
            Callback callback
    ){

        Request request =
                new Request.Builder()

                        .url(
                                BASE_URL
                                        + "deletevehicle/"
                                        + id
                        )

                        .delete()

                        .build();

        client.newCall(request)
                .enqueue(callback);

    }

    // ==============================
    // UPDATE VEHICLE hhh
    // ==============================

    public static void updateVehicle(

            int id,

            String hinhxe1,
            String hinhxe2,
            String tenxe,
            String bienso,
            int dongia,
            String mota,

            Callback callback

    ){

        String json = "{"

                + "\"hinhxe1\":\"" + hinhxe1 + "\","

                + "\"hinhxe2\":\"" + hinhxe2 + "\","

                + "\"tenxe\":\"" + tenxe + "\","

                + "\"bienso\":\"" + bienso + "\","

                + "\"dongia\":" + dongia + ","

                + "\"mota\":\"" + mota + "\""

                + "}";

        RequestBody body =
                RequestBody.create(
                        json,
                        MediaType.parse(
                                "application/json"
                        )
                );

        Request request =
                new Request.Builder()

                        .url(
                                BASE_URL
                                        + "updatevehicle/"
                                        + id
                        )

                        .put(body)

                        .build();

        client.newCall(request)
                .enqueue(callback);

    }


    // POST ORDER //

    public static void order(
            String bienso,

            String hoten,

            String sodienthoai,

            String ngaydat,

            String ngaylay,

            int songaythue,

            int dongia,

            int thanhtien,

            String thanhtoan,

            String ghichu,

            int user_id,

            String tenxe,

            String diachikh,

            String diachinhan,

            Callback callback
    )
    {
        JSONObject object = new JSONObject();

        try {

            object.put("bienso", bienso);

            object.put("hoten", hoten);

            object.put("sodienthoai", sodienthoai);

            object.put("ngaydat", ngaydat);

            object.put("ngaylay", ngaylay);

            object.put("songaythue", songaythue);

            object.put("dongia", dongia);

            object.put("thanhtien", thanhtien);

            object.put("thanhtoan", thanhtoan);

            object.put("tinhtrang", "Chờ xác nhận");

            object.put("ghichu", ghichu);

            object.put("user_id", user_id);

            object.put("tenxe", tenxe);

            object.put("diachikh", diachikh);

            object.put("diachinhan", diachinhan);

        }
        catch (Exception e){

            e.printStackTrace();

        }
        RequestBody body =
                RequestBody.create(
                        object.toString(),
                        MediaType.parse("application/json"
                        )
                );
        Request request =
                new Request.Builder()
                        .url(BASE_URL+"datxe")
                        .addHeader("Content-Type",
                                "application/json")
                        .post(body)
                        .build();

                client.newCall(request)
                        .enqueue(callback);
    }



    // ==============================
// GET ORDERS
// ==============================

    public static void getOrders(
            Callback callback
    ){

        Request request =
                new Request.Builder()
                        .url(BASE_URL + "orders")
                        .build();

        client.newCall(request)
                .enqueue(callback);

    }



    public  static void getOrdersByUserId(
            int user_id,
            Callback calllbcak
    )
    {
        Request request=
                new Request.Builder().url(
                        BASE_URL
                        + "datxe/"
                        + user_id
                ).build();
        client.newCall(request).enqueue(calllbcak);



    }
    // CANCEL ORDER

    public  static void cancelOrder(
            int id,
            Callback callback
    )
    {
        Request request = new Request.Builder().url(
                BASE_URL
                        + "cancelorder/"
                        + id
        ).put(
                RequestBody.create(
                        "",
                        null
                )
        ).build();
        client.newCall(request).enqueue(callback);
    }


    // lay mk va doi mk


    public static void changepassword(
            int user_id,
            String old_password,
            String new_password,
            Callback callback
    )
    {
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("user_id",user_id);
                jsonObject.put("old_password",old_password);
                jsonObject.put("new_password",new_password);

            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

        RequestBody body = RequestBody.create(
          jsonObject.toString(),
          MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL+"changepassword")
                .put(body)
                .build();

        client.newCall(request).enqueue(callback);


    }


}