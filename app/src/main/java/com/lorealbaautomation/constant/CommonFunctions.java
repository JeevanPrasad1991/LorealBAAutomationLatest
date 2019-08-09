package com.lorealbaautomation.constant;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.lorealbaautomation.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;

/**
 * Created by jeevanp on 2/16/2017.
 */

public class CommonFunctions {

    public static boolean checkNetIsAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        return m_cal.get(Calendar.HOUR_OF_DAY) + "" + m_cal.get(Calendar.MINUTE) + "" + m_cal.get(Calendar.SECOND);

    }

    public static void startAnncaCameraActivity(final Context context, final String path, Fragment fragment, final boolean showGrid) {
        final AnncaConfiguration.Builder dialogDemo;
        if (fragment == null) {
            dialogDemo = new AnncaConfiguration.Builder((Activity) context, CommonString.CAPTURE_MEDIA);
        } else {
            dialogDemo = new AnncaConfiguration.Builder(fragment, CommonString.CAPTURE_MEDIA);
        }
        dialogDemo.setMediaAction(AnncaConfiguration.MEDIA_ACTION_PHOTO);
        dialogDemo.setMediaResultBehaviour(AnncaConfiguration.PREVIEW);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.view_horizontal_camera);
        dialog.setCancelable(false);
        if (dialog != null && (!dialog.isShowing())) {
            dialog.show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                new Annca(dialogDemo.build()).launchCamera(path, showGrid);
            }
        }, 3000);
    }

    public static void setScaledImage(ImageView imageView, final String path) {
        final ImageView iv = imageView;
        ViewTreeObserver viewTreeObserver = iv.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                int imageViewHeight = iv.getMeasuredHeight();
                int imageViewWidth = iv.getMeasuredWidth();
                iv.setImageBitmap(decodeSampledBitmapFromPath(path, imageViewWidth, imageViewHeight));
                return true;
            }
        });
    }

    private static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static String getCurrentTimeHHMMSS() {
        Calendar m_cal = Calendar.getInstance();
        return m_cal.get(Calendar.HOUR_OF_DAY) + "" + m_cal.get(Calendar.MINUTE) + "" + m_cal.get(Calendar.SECOND);
    }


    public static String setMetadataAtImages(String store_Nm, String store_Id, String type, String userId) {
        String ss = "Store Name : " + store_Nm + " | Store Id : " + store_Id + " " + " | Merchandiser Id : " + userId + " | Image Type : " + type;
        return ss;
    }


    public static String setMetadataAtImagesforwindow(String store_Nm, String store_Id, String window_cd, String type, String userId) {
        String ss = "Store Name : " + store_Nm + " | Store Id : " + store_Id + " " + " | Window Id : " + window_cd + " | Merchandiser Id : " + userId + " | Image Type : " + type;
        return ss;
    }

    public static String setMetadataAtImagesforvisitorstorewise(String store_Nm, String store_Id, String visitor_nm, String type, String userId) {
        String ss = "Store Name : " + store_Nm + " | Store Id : " + store_Id + " " + " | Visitor Name : " + visitor_nm + " | Merchandiser Id : " + userId + " | Image Type : " + type;
        return ss;
    }

    public static String setMetadataAtImagesforvisitor(String visitor_nm, String type, String userId) {
        String ss = "Visitor Name : " + visitor_nm + " | Merchandiser Id : " + userId + " | Image Type : " + type;
        return ss;
    }

    public static String setMetadataAtImagesdpreporting(String type, String userId) {
        String ss = "Merchandiser Id : " + userId + " | Image Type : " + type;
        return ss;
    }


    public static Bitmap addMetadataAndTimeStampToImage(Context context, final String path, String metadata, String visit_date) {
        Bitmap bmp1 = BitmapFactory.decodeFile(path);
        View view = LayoutInflater.from(context).inflate(R.layout.preview_image, null);
        view.layout(0, 0, bmp1.getWidth(), bmp1.getHeight());
        Bitmap bmp = getViewBitmap(view, bmp1, path, metadata, visit_date);
        try {

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(path)));
            return bmp;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Crashlytics.logException(e);
            return bmp1;
        }
    }

    public static Bitmap getViewBitmap(View view, Bitmap bmp, String path, String metadata, String visit_date) {
        try {
            //Get the dimensions of the view so we can re-layout the view at its current size
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            String dateTime = sdf.format(Calendar.getInstance().getTime());
            String copm = dateTime;
            ImageView temp_img = (ImageView) view.findViewById(R.id.temp_img);
            //ImageView temp_map = (ImageView) view.findViewById(R.id.temp_map);
            TextView storeM = (TextView) view.findViewById(R.id.storeM);
            int copleteValue = 0;
            try {
                copm = copm.replaceAll("[- ]", " ");
                String[] items = copm.split(":");
                String seconds = items[2];
                int lastIndex;
                lastIndex = Integer.parseInt(seconds);
                int day = Integer.parseInt(visit_date.substring(3, 5));
                int a = Integer.parseInt("10") * Integer.parseInt("40");
                a = a + day;
                lastIndex = lastIndex * 2;
                copleteValue = a + lastIndex;
            } catch (NumberFormatException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }

            //timestamp on image
            Bitmap dest = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas cs = new Canvas(dest);
            Paint tPaint = new Paint();
            tPaint.setTextSize(27);
            tPaint.setColor(Color.RED);
            tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            cs.drawBitmap(bmp, 0f, 0f, null);
            float height_ = tPaint.measureText("yY");
            cs.drawText(dateTime + "[" + "10" + "] " + String.valueOf(copleteValue), 20f, height_ + 15f, tPaint);
            try {
                dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(path)));
            } catch (FileNotFoundException e) {
                Crashlytics.logException(e);
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            storeM.setText(metadata + " | Date : " + dateTime + "[" + "10" + "] " + String.valueOf(copleteValue));

            bmp = BitmapFactory.decodeFile(path);

            temp_img.setImageBitmap(bmp);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            //Cause the view to re-layout
            view.measure(measuredWidth, measuredHeight);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            //Create a bitmap backed Canvas to draw the view into
            Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            view.draw(c);
            return b;
        } finally {
        }
    }
}
