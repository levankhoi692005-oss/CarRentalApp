package com.example.carrentalapp.model;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import okhttp3.internal.http2.Header;

public class show_notification {
    public  static  void show_notifi(
            String notification,
            LinearLayout layout,
            TextView textView,
            Activity activity
    )
    {

        textView.setText(notification);

        layout.setVisibility(
                View.VISIBLE
        );
        layout.setAlpha(0f);
        layout.setTranslationY(-100);
        layout.animate()
                .alpha(1f)
                .setDuration(400)
                .translationY(0)
                .start();

        new Handler(Looper.getMainLooper())
                .postDelayed(()->
                {
                    layout.animate()
                            .alpha(0f)
                            .translationY(-100)
                            .setDuration(300)
                            .withEndAction(()->{

                                layout.setVisibility(
                                        View.GONE
                                );

                            }).start();


                },2000);







    }

    public static void show_noti(String notification,
                                 LinearLayout layout,
                                 TextView textView,
                                 Activity activity,
                                 String drawale)
    {
        GradientDrawable drawable_missing , drawable_error, drawable_success,drawable_enough;
        drawable_error = new GradientDrawable();
        drawable_missing = new GradientDrawable();
        drawable_success = new GradientDrawable();

        drawable_success.setStroke(4, Color.parseColor("#6edb7c"));
        drawable_success.setColor(Color.parseColor("#6edb7c"));
        drawable_success.setCornerRadius(5);

        drawable_error.setColor(Color.parseColor("#ec592f"));
        drawable_error.setCornerRadius(5);
        drawable_error.setStroke(4,Color.parseColor("#ec592f"));

        drawable_missing.setColor(Color.parseColor("#ffffff"));
        drawable_missing.setStroke(4, Color.RED);


        drawable_enough = new GradientDrawable();


        drawable_enough.setStroke(4, Color.WHITE);
        drawable_enough.setColor(Color.parseColor("#ffffff"));


        show_notifi(notification,layout,textView,activity);
        if(drawale.equals("error"))
        {
            layout.setBackground(drawable_error);
            show_notifi(notification,layout,textView,activity);

        } else if (drawale.equals("missing")) {
            layout.setBackground(drawable_missing);
            show_notifi(notification,layout,textView,activity);

        } else if (drawale.equals("success")) {

        layout.setBackground(drawable_success);
        show_notifi(notification,layout,textView,activity);
        }
     else if (drawale.equals("enough")) {

        layout.setBackground(drawable_enough);
        show_notifi(notification,layout,textView,activity);
    }



}
}
