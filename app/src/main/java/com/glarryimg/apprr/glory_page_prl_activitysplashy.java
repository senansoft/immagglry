package com.glarryimg.apprr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.glarryimg.modulads.adsa.glory_app.glory_BaseallActivity;
import com.github.florent37.viewanimator.ViewAnimator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;


public class glory_page_prl_activitysplashy extends glory_BaseallActivity {

    LinearLayout saleh_liner_start_liscora_space_tech,
            saleh_liner_start_more_space_tech, saleh_liner_space_tech_start_rate_app;
    TextView saleh_space_tech_txt_start_lsitn;
   CardView saleh_mycardd_space_bark;
   ImageView top_img2_space_bark;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void saleh_disease_setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.glory_gradient_4);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    void saleh_setSplashy(){
        ViewAnimator
                .animate(findViewById(R.id.txt_app_name_space_bark))
                .translationY(-700, 0)
                .alpha(0,1)
                .flipVertical()
                .andAnimate(findViewById(R.id.mycardd_space_bark))
                .translationY(-700, 0)
                .alpha(0,1)
                .flipHorizontal()

                .andAnimate(findViewById(R.id.top_img2_space_bark))
                //.translationY(700, 0)
                .alpha(0,1)
                .translationY(1000, 0)
                .fadeOut()
                .alpha(0,1)
                //.rotation(180f)
               // .swing()
                .duration(2000)


                .start();

    }

    int random_number()
    {
        int random = new Random().nextInt(Integer.parseInt(getString(R.string.count_walls))-2);
        if(random==0)
            random+=1;

        Log.e("random=",random+"");
        return random;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (NullPointerException e) {
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.glory_prl_morc_splashy);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        saleh_space_tech_txt_start_lsitn =findViewById(R.id.txt_start_lsitn_space_bark);
        saleh_liner_start_liscora_space_tech =findViewById(R.id.liner_start_listn_space_bark);
        saleh_setSplashy();
        saleh_disease_setStatusBarGradiant(glory_page_prl_activitysplashy.this);
        top_img2_space_bark=findViewById(R.id.top_img2_space_bark);
        saleh_mycardd_space_bark =findViewById(R.id.mycardd_space_bark);
        TextView t2 = (TextView) findViewById(R.id.text_police_space_bark);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                }
            }
        });
        String url = "drawable/" + "walls" +  random_number();

        int imageKey = getResources().getIdentifier(url, "drawable", getPackageName());

        Picasso.get()
                .load(imageKey)

             //   .fit()
               // .centerCrop()
                //  .transform(new RoundedTransformation(15, 0))
                //.resize(500, 0)

                .into(top_img2_space_bark, new Callback() {
                    @Override
                    public void onSuccess() {

                        //saleh_progressBarmain.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });
        saleh_mycardd_space_bark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(glory_page_prl_activitysplashy.this, glory_List_of_images.class));
            }
        });

    }


}
