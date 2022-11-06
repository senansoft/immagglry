package com.glarryimg.apprr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.glarryimg.modulads.adsa.glory_app.glory_BaseallActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class glory_MainActivity extends glory_BaseallActivity {

    Button saleh_btn_open;
    ImageView saleh_imgwelcom;
    ProgressBar saleh_progressBarmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glory_activity_main);
        saleh_imgwelcom =findViewById(R.id.imageView_welcom);
        saleh_progressBarmain =findViewById(R.id.progressBar);
        String url = "drawable/" + "romanc" +  random_number();
        TextView t2 = (TextView) findViewById(R.id.text_police);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    glory_MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    glory_MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                }
            }
        });
        int imageKey = getResources().getIdentifier(url, "drawable", getPackageName());

        Picasso.get()
                .load(imageKey)

              //  .transform(new RoundedTransformation(15, 0))
                .resize(500, 0)

                .into(saleh_imgwelcom, new Callback() {
                    @Override
                    public void onSuccess() {
                        saleh_progressBarmain.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });
        saleh_btn_open =findViewById(R.id.btn_open);
        saleh_btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(glory_MainActivity.this, glory_List_of_images.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(glory_MainActivity.this, glory_List_of_images.class);
                startActivity(i);
                finish();
            }
        }, 3000); // Timer
    }

    int random_number()
    {
         int random = new Random().nextInt(Integer.parseInt(getString(R.string.count_walls))-2);
         if(random==0)
         random+=1;

        Log.e("random=",random+"");
        return random;
    }
}
