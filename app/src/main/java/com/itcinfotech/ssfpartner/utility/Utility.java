package com.itcinfotech.ssfpartner.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    //Process dialog
    private static ProgressDialog dialog;


    public static void printLog(String... msg) {
        String str = "";
        for (String i : msg) {
            str = str + "\n" + i;
        }
        if (true) {
            Log.i("Diyo", str);
        }
    }

    public static String getAPIRequest(String data) {


        return null;
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static float round(float value) {

        try {

            int places = 2;
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);

            return bd.floatValue();

        } catch (Exception e) {
            return Float.parseFloat(String.format("%.2f", value));
        }
    }

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Alert");
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    public static void showErrorAlert(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Oops...");
        dialogBuilder.setMessage("Something went wrong!");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String changeDateFormat(String currentDate, String currentFormat, String expectedFormat) {
        try {
            DateFormat originalFormat = new SimpleDateFormat(currentFormat, Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat(expectedFormat);
            Date date = originalFormat.parse(currentDate);
            String formattedDate = targetFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            return "";
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void applyFontForToolbarTitle(Context context, Toolbar toolbar) {

        try {
            for (int i = 0; i < toolbar.getChildCount(); i++) {
                View view = toolbar.getChildAt(i);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    Typeface titleFont = TypefaceSingleton.getInstance().getTypeface(context);
                    if (tv != null) {
                        tv.setTypeface(titleFont);
                        break;
                    }
                } else if (view instanceof EditText) {
                    EditText et = (EditText) view;
                    Typeface titleFont = TypefaceSingleton.getInstance().getTypeface(context);
                    if (et != null) {
                        et.setTypeface(titleFont);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    public static void showProgresDialog(Context context) {
        dialog = ProgressDialog.show(context, "", "Loading. Please wait...", true);
    }

    public static void dismessProgresDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static String getCurrentDate(String format){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(c);
        return formattedDate;
    }
}