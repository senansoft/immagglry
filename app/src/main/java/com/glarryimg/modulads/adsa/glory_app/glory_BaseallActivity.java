package com.glarryimg.modulads.adsa.glory_app;

import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_ads_active;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_count_click;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_date_today_servr;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_general_check_internet;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_greating_get_time_now;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_have_been_checkd_ads;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.max_count_click;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.glarryimg.apprr.R;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.glarryimg.modulads.glory_high_projct.vedio_pro.glory_aid_ramthan.glory_CustomDialog_settings;
import com.glarryimg.modulads.glory.glory_imgtovedioapp.glory_sample.Manager.glory_PrefManager;
import com.glarryimg.modulads.glory_textonphoto.glory_customqoutescreator.activity.glory_packk_name;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class glory_BaseallActivity extends AppCompatActivity implements View.OnClickListener{

    private glory_CustomDialog_settings dialog_setwallpaper;
    FirebaseFirestore ipadressdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glory_activity_baseall);
        FirebaseApp.initializeApp(getBaseContext());

        ipadressdb=FirebaseFirestore.getInstance();

        glory_packk_name.pagknamey= BuildConfig.APPLICATION_ID;
        get_counter_thif();
        if(!checkEmulator()) {
            gettt_info_devs_cloud();
        }
        else
        {
            Log.e("yesss","emulator");
        }
        //get_device_info_ads();


    }

    String get_ads_id()
    {
        AdvertisingIdClient.Info idInfo = null;
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(glory_BaseallActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        };

        return idInfo.getId();
    }




    boolean checkEmulator()
    {
        try
        {
            String buildDetails = (Build.FINGERPRINT + Build.DEVICE + Build.MODEL + Build.BRAND + Build.PRODUCT + Build.MANUFACTURER + Build.HARDWARE).toLowerCase();

            Log.e("buildDetails=",buildDetails);
            if (buildDetails.contains("generic")
                    ||  buildDetails.contains("unknown")
                    ||  buildDetails.contains("emulator")
                    ||  buildDetails.contains("nox")
                    ||  buildDetails.contains("genymotion")
                    ||  buildDetails.contains("goldfish")
                    ||  buildDetails.contains("test-keys"))
                return true;
        }
        catch (Throwable t) {
            }

        try
        {
            TelephonyManager tm  = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String              non = tm.getNetworkOperatorName().toLowerCase();
            if (non.equals("android"))
                return true;
        }
        catch (Throwable t) {}

        try
        {
            if (new File("/init.goldfish.rc").exists())
                return true;
        }
        catch (Throwable t) {}

        return false;
    }

    public void gettt_info_devs_cloud() {


       if(! func_general_check_internet(getBaseContext()))
       {
           return;
       }
        if(the_have_been_checkd_ads && the_date_today_servr !=null&& the_date_today_servr.length()>4)
        {
            return;
        }

        String dvica_idia= glory_class_settings.func_getSerialNumber();

        if(dvica_idia.equalsIgnoreCase("null")||dvica_idia.equalsIgnoreCase("NULL")||dvica_idia.equalsIgnoreCase("unknown"))
        {
            dvica_idia=get_ads_id();
            if(dvica_idia==null||dvica_idia.equalsIgnoreCase("null")||dvica_idia.equalsIgnoreCase("unknown"))
            {
                dvica_idia = UUID.randomUUID().toString();

            }

        }
        if(dvica_idia==null||dvica_idia.equalsIgnoreCase("null")||dvica_idia.equalsIgnoreCase("unknown"))
        {
            the_ads_active =true;
            return;
        }
      // Toasty.info(getBaseContext(),"checkking").show();
        Map<String, Object> data = new HashMap<>();
        data.put("devicid",dvica_idia);
        FirebaseFunctions.getInstance() // Optional region: .getInstance("europe-west1")
                .getHttpsCallable("currentTimeTwo")

                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {

                            String result = (String) task.getResult().getData();
                            get_statu_update(result);
                             return result;
                    }
                }).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                        Log.e("responsee_compl_fail=", e.toString());
                    }
                }
                else {
                    String result = (String) task.getResult().toString();

                    get_statu_update(result);

                 //   Log.e("responsee_complsuc=", result);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("responsee_failer=", e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                //Log.e("responsee_success=", s);
                get_statu_update(s);


            }
        });
    }
    void get_counter_thif()
    {
        SharedPreferences mysharedPreferences = getSharedPreferences(func_greating_get_time_now(), MODE_APPEND);

        the_count_click = mysharedPreferences.getInt("is_thift", 0);

        if (the_count_click >= max_count_click) {

            the_ads_active = false;
            //  method_incrmet_clicks(mcontxt);
        }
    }
    private void get_statu_update(String strresult) {
        try {
            JSONObject obj = new JSONObject(strresult);
            the_ads_active = !Boolean.parseBoolean(obj.getString("status"));
            the_date_today_servr =obj.getString("data");
            if(the_date_today_servr !=null&& the_date_today_servr.length()>4)
            {
                the_have_been_checkd_ads =true;
            }
            Log.e("responsee_status=", obj.getString("status"));
            Log.e("responsee_date=", obj.getString("data"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 /*   void get_device_info_ads()
    {
        if(!class_settings.general_check_internet(getBaseContext())) {
            class_settings.ads_active=false;
            return;
        }
        try
        {

            if(have_been_checkd_ads)
            {
                return;
            }
            String android_device_id = class_settings.getSerialNumber();
            ipadressdb.collection("ipadress").
                    document(class_settings.getTimeDate(new Date().getTime())).
                    collection("ipass").whereEqualTo("deviceid", android_device_id).get().
                    addOnSuccessListener(BaseallActivity.this, new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                class_settings.ads_active = true;
                            }
                            have_been_checkd_ads=true;
                            Toasty.info(getBaseContext(),"checked"+android_device_id).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (Exception ex){
        }
    }*/

    private Dialog rateDialog;


    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        final glory_PrefManager prf = new glory_PrefManager(getApplicationContext());
        if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
            //  super.onBackPressed();
            show_dialog_bottom();
        } else {
            rateDialog(true);
        }
    }


    public void show_dialog_bottom() {


        dialog_setwallpaper = new glory_CustomDialog_settings(this, 0, 0,
                R.layout.glory_rate_and_other, R.style.Theme_dialog, Gravity.BOTTOM, R.style.DialogAnimation);

        CardView card_rate_app = dialog_setwallpaper.findViewById(R.id.card_rate_app);
        CardView card_more_app = dialog_setwallpaper.findViewById(R.id.card_more_app);
        CardView card_share_app = dialog_setwallpaper.findViewById(R.id.card_share_app);
        CardView card_policy_privacy = dialog_setwallpaper.findViewById(R.id.card_policy_privacy);
        CardView card_back = dialog_setwallpaper.findViewById(R.id.card_back);
        card_share_app.setOnClickListener(this);
        card_rate_app.setOnClickListener(this);
        card_more_app.setOnClickListener(this);
        card_policy_privacy.setOnClickListener(this);
        card_back.setOnClickListener(this);

        dialog_setwallpaper.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog_setwallpaper.show();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_rate_app) {

            rateDialog(true);

        }  if (id == R.id.card_more_app) {

            open_devloper_account();
        }

        if (id == R.id.card_policy_privacy) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
            }
            dialog_setwallpaper.dismiss();
        } else if (id == R.id.card_share_app) {
            share_app();
            dialog_setwallpaper.dismiss();
        } else if (id == R.id.card_back) {
            dialog_setwallpaper.dismiss();

            super.onBackPressed();

            /*final PrefManager prf = new PrefManager(getApplicationContext());
            if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
                super.onBackPressed();
            } else {
                super.onBackPressed();
               // rateDialog(true);
            }*/

        } else
            dialog_setwallpaper.dismiss();//100 all shares
    }

    private void open_devloper_account()
    {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id==" + getString(R.string.devlper_name))));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.devlper_name))));
        }
    }


    private void share_app() {
        final String appPackageName = getApplication().getPackageName();
        String shareBody = "Download " + getString(R.string.app_name) + " From :  " + "http://play.google.com/store/apps/details?id=" + appPackageName;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
    }
    public void rateDialog(final boolean close) {
        this.rateDialog = new Dialog(this, R.style.Theme_Dialog);

        rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateDialog.setCancelable(true);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = rateDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        final glory_PrefManager prf = new glory_PrefManager(getApplicationContext());
        rateDialog.setCancelable(false);
        rateDialog.setContentView(R.layout.glory_rating_app);
        final AppCompatRatingBar AppCompatRatingBar_dialog_rating_app = (AppCompatRatingBar) rateDialog.findViewById(R.id.AppCompatRatingBar_dialog_rating_app);
        final LinearLayout linear_layout_feedback = (LinearLayout) rateDialog.findViewById(R.id.linear_layout_feedback);
        final LinearLayout linear_layout_rate = (LinearLayout) rateDialog.findViewById(R.id.linear_layout_rate);
        final Button buttun_send_feedback = (Button) rateDialog.findViewById(R.id.buttun_send_feedback);
        final Button button_later = (Button) rateDialog.findViewById(R.id.button_later);
        final Button btn_rate_now = (Button) rateDialog.findViewById(R.id.btn_rate_now);
        final Button button_cancel = (Button) rateDialog.findViewById(R.id.button_cancel);
        btn_rate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getApplication().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                prf.setString("NOT_RATE_APP", "TRUE");
                rateDialog.dismiss();
            }
        });
        button_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        final EditText edit_text_feed_back = (EditText) rateDialog.findViewById(R.id.edit_text_feed_back);
        buttun_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        AppCompatRatingBar_dialog_rating_app.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    if (rating >= 1) {
                        final String appPackageName = getApplication().getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        prf.setString("NOT_RATE_APP", "TRUE");
                        rateDialog.dismiss();
                    } else {
                        linear_layout_feedback.setVisibility(View.VISIBLE);
                        linear_layout_rate.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        });
        rateDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    rateDialog.dismiss();
                    if (close)
                        finish();
                }
                return true;

            }
        });
        rateDialog.show();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.glory_abd_grad);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.setStatusBarColor(activity.getResources().getColor(R.color.status_bar));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}