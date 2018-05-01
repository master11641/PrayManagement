package com.example.alireza.myapplication.utility;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import com.example.alireza.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by alireza on 4/13/2018.
 */

public class Helper {
    public static void showDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public static void showDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onClickListener)
                .show();
    }

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, GetUniqueFileName()+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
   public  static  String GetUniqueFileName(){
       String uuid = UUID.randomUUID().toString();
       return uuid;
   }
    public static Bitmap GetBimapFromPath(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //Drawable d = new BitmapDrawable(getResources(), myBitmap);
            return myBitmap;
        }
        return null;
    }
    public static String[] getPagesUrl(String url, int count) {
        String[] result;
        int page;
        if (count <= 10) {
            page = 1;
        } else {
            if (count % 10 == 0) {
                page = count / 10;
            } else {
                page = count / 10 + 1;
            }
        }
        result = new String[page];
        for (int i = 0; i < page; i++) {
            result[i] = url + "&page=" + (i + 1);
        }
        return result;
    }
public void showNotification( Activity activity,String title,String body){
    // channel ID is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions
    String channelID="";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel
        CharSequence name = activity.getString(R.string.channel_name);
        String description = activity.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel("0", name, importance);
        mChannel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        android.app.NotificationManager notificationManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);
        channelID=mChannel.getId();
    }

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity, channelID)
            .setSmallIcon(R.drawable.pdlg_icon_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    NotificationManager notificationManager = (NotificationManager)activity.getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(0, mBuilder.build());
}
  /*  public static void goToWebView(String url, Activity activity){
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("link",url);
        activity.startActivity(intent);
    }*/
}
