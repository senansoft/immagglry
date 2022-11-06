package com.glarryimg.modulads.adsa.glory_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.glarryimg.apprr.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class glory_class_settings
{

    public  static  int the_count_click;
    public  static  int max_count_click=7;
    public static  boolean the_ads_active =false;
    public static  String the_date_today_servr;
    public static boolean the_have_been_checkd_ads;

    public static FirebaseFirestore ip_address_for_ads_db;


    public static ArrayList<Object> genrl_objects_sList = new ArrayList<>();

    public static glory_MyBounceInterpolator interpolator = new glory_MyBounceInterpolator(0.2, 10);

    @SuppressLint("ResourceType")
    public static Animation anim_view(Context context) {
        return AnimationUtils.loadAnimation(context, R.animator.milkshake);

    }



    public static void slava_chek_is_user_thift(Context mcontxt, int county_click) {


        //   public_things.count_click = mysharedPreferences.getInt("is_thift", 0);
        func_incrmet_clicks(mcontxt);

        if (county_click >= max_count_click && the_ads_active) {

            the_ads_active = false;
        }
        ip_address_for_ads_db= FirebaseFirestore.getInstance();

        try {
            String   android_device_id;

            HashMap<String,Object> my=new HashMap();

            //if (android_device_id.equalsIgnoreCase("") || android_device_id.equalsIgnoreCase("null") || android_device_id.trim().length() == 0)
            {
                android_device_id = func_getSerialNumber();
                my.put("deviceid",android_device_id);
                ip_address_for_ads_db.
                        collection("ipadress").
                        document(getTimeDate(new Date().getTime())).
                        collection("ipass").
                        add(my);
            }
        } catch (NullPointerException we)
        {
            Toast.makeText(mcontxt, "erwe"+we.toString(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException qa) {
            Toast.makeText(mcontxt, "erqa"+qa.toString(), Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(mcontxt, "erex"+ex.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public static  void func_incrmet_clicks(Context mcontext) {

        SharedPreferences countPref = mcontext.getSharedPreferences(func_greating_get_time_now(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOpenClose = countPref.edit();
        editorOpenClose.putInt("is_thift", the_count_click);
        editorOpenClose.commit();
        if (the_count_click >= max_count_click && the_ads_active) {

            slava_chek_is_user_thift(mcontext, the_count_click);
            the_ads_active = false;
        }
      //  slava_chek_is_user_thift(mcontext,count_click);
    }



    public static String getTimeDate(Long timestamp){
        try{
            //  SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
            return  sfd.format(new Date(timestamp));
        }
        catch(Exception e) {
            return "date";
        }
    }


    public static String func_getSerialNumber() {
        String serialNumber="unknown";

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = Build.SERIAL;
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ro.boot.serialno");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ro.ril.oem.sno");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ro.boot.ap_serial");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c, "ril.cdma.esn");
            if (serialNumber.equals("") || serialNumber.equals("unknown"))
                serialNumber = (String) get.invoke(c,"ro.vendor.hon.extserial.num");
            if (serialNumber.equals("") || serialNumber.equals("unknown")) {
                serialNumber = (String) get.invoke(c, "vendor.gsm.serial");
                serialNumber = serialNumber.substring(0, serialNumber.indexOf(' '));
            }
            // If none of the methods above worked
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;
            if (serialNumber.equals(Build.UNKNOWN))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serialNumber = Build.getSerial();
                }
            if (serialNumber.equals(Build.UNKNOWN))
                serialNumber = "unknown";
        } catch (Exception e) {
            Log.e("serialllllllllllll=", e.toString());

            e.printStackTrace();
            serialNumber = "unknown";
        }

        Log.e("serialllllllllllll=", serialNumber);
        return serialNumber;
    }

    public static String func_greating_get_time_now()//initial text start date
    {
        String date_time = "";
        int mYear;
        int mMonth;
        int mDay;
        int mHour;
        int mMinute;
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mMonth += 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date_time = mDay + "-" + (mMonth) + "-" + mYear;
        return date_time;
    }

    public static boolean func_check_ads(String ads)
    {
        String app_id="ca-app-pub-9237699433870463";

        if(!the_ads_active)
        {
            return  false;
        }

        if(the_count_click >= max_count_click)
        {
            return false;
        }
        else
        if(ads.contains(ads)&&ads.contains(app_id))
        {
            return  true;
        }
        else
            return ads.contains("3940256099942544");
    }
    public static boolean func_general_check_internet(Context mcontxx) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) mcontxx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            boolean isConnected = netInfo != null &&
                    netInfo.isConnectedOrConnecting();

            return isConnected;
        } catch (NullPointerException ex) {
        } catch (Exception e) {
        }
        return false;
    }


}
