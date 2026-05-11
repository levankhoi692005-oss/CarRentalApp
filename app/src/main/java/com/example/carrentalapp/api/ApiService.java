package com.example.carrentalapp.api;

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
    // UPDATE VEHICLE
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

}