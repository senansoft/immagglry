package com.glarryimg.apprr;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.glarryimg.modulads.adsa.glory_app.glory_class_settings;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_ads_active;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.genrl_objects_sList;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_incrmet_clicks;
import static com.glarryimg.modulads.adsa.glory_app.glory_my_ads_unit.full_ad;
import static com.glarryimg.modulads.adsa.glory_app.glory_my_ads_unit.native_ad;
import static com.glarryimg.apprr.glory_public_class.anim_view;
import static com.glarryimg.apprr.glory_public_class.saleh_arr_objects;
import static com.glarryimg.apprr.glory_public_class.saleh_count_img_to_show_native_ads;
import static com.glarryimg.apprr.glory_public_class.saleh_current_index_loaded;
import static com.glarryimg.apprr.glory_public_class.saleh_myarr_imgs;
import static com.glarryimg.apprr.glory_public_class.saleh_start_pos_opend_pager;

public class glory_second_activity extends AppCompatActivity implements View.OnClickListener, glory_interface_src {

    public static final String TAG = "KKViewPager";
    AdRequest adRequestfull = null;
    InterstitialAd saleh_mInterstitialAd;
    CountDownTimer saleh_countDownTimer;

    // ImageView iv_back, iv_preview_share, iv_preview_down;
    ViewPager2 saleh_view_pager_single_slide;
    //ViewPager view_pagers;
    int src = R.drawable.mm0;
    boolean saleh_permission_allowed;
    String saleh_back_ground_file_path = "";
    RecyclerView saleh_recycler_slider;
    //slider_adapter adapter;
    glory_ViewPagerAdapter saleh_myadptr;
    String saleh_recived_mg_name ="";
    TextView txt_wait_ads;
    RecyclerView.LayoutManager saleh_recylerViewLayoutManager;
    private glory_dialog_setting saleh_dialog_setwallpaper;
    private Button saleh_btn_lock;
    private Button saleh_btn_desktop;
    private Button saleh_btn_all;
    private Button btn_set_wallpaper;
    private WallpaperManager saleh_wpManager;
    private List<NativeAd> saleh_mNativeAds = new ArrayList<>();
    private AdLoader saleh_adLoader;
    private float MAX_SCALE = 0.0f;
    private int saleh_mPageMargin;
    private boolean saleh_animationEnabled = true;
    private boolean fadeEnabled = false;
    private float fadeFactor = 0.5f;
   FloatingActionButton saleh_fabSettings2;
    private glory_KKViewPager saleh_mPager;

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }

    ////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(R.anim.from_right, R.anim. to_left);
        finish();
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                full_ad,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd _mInterstitialAd) {

                        saleh_mInterstitialAd = _mInterstitialAd;
                        Log.i("TAG", "onAdLoaded");
                        saleh_mInterstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                        glory_class_settings.the_count_click++;
                                        func_incrmet_clicks(getBaseContext());
                                        if(glory_class_settings.the_count_click >= glory_class_settings.max_count_click)
                                        {
                                            saleh_mInterstitialAd=null;

                                            glory_class_settings.slava_chek_is_user_thift(getBaseContext(), glory_class_settings.the_count_click);
                                            the_ads_active =false;
                                        }

                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        saleh_mInterstitialAd = null;
                                        loadAd();
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {

                                        saleh_mInterstitialAd = null;

                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.

                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        saleh_mInterstitialAd = null;


                    }
                });
    }


    private void createTimer(final long milliseconds) {
        if (saleh_countDownTimer != null) {
            saleh_countDownTimer.cancel();
        }
        txt_wait_ads.setVisibility(View.VISIBLE);
        saleh_countDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {
                txt_wait_ads.setText(getString(R.string.wait_ads) + " " + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                txt_wait_ads.setVisibility(View.GONE);

                saleh_mInterstitialAd.show(glory_second_activity.this);

                saleh_countDownTimer.cancel();
            }
        };
    }

    public void showHistory(View v) {


        AnimatorSet set = new AnimatorSet();
        // Using property animation
        ObjectAnimator animation = ObjectAnimator.ofFloat(v,
                "translationY", 0f, -0.8f);
        animation.setDuration(2000);
        set.play(animation);
        set.start();

    }

    private void doShowInterstitial() {

        if (saleh_mInterstitialAd !=null&& the_ads_active) {

            createTimer(3000);
            saleh_countDownTimer.start();
        } else {
                loadAd();

        }
    }

    public void listRaw()
    {
        saleh_arr_objects.clear();
        saleh_current_index_loaded =1;

        if (saleh_arr_objects.size() == 0)
        {

           // myarr_imgs.clear();
            try {

                saleh_arr_objects.addAll(saleh_myarr_imgs);
                saleh_myadptr.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("erroorrr", e.toString());
            }
        }


        initKKViewPager();
        saleh_myadptr.notifyDataSetChanged();

            Log.e("recived_mg_name=", saleh_recived_mg_name);
            for (int i = 0; i < saleh_arr_objects.size(); i++) {

                if (saleh_arr_objects.get(i) instanceof glory_image_class && ((glory_image_class) saleh_arr_objects.get(i)).getName().equalsIgnoreCase(saleh_recived_mg_name)) {
                    Log.e("img_name=",((glory_image_class) saleh_arr_objects.get(i)).getName());
                    saleh_mPager.setCurrentItem(i);
                    saleh_start_pos_opend_pager =i;
                  //  loadNativeAds(start_pos_opend_pager+2);
                 // return;
                }
            }
        loadNativeAds(saleh_start_pos_opend_pager);

    }

    private void initKKViewPager() {
        saleh_mPager = (glory_KKViewPager) findViewById(R.id.kk_pager);
        saleh_mPager.setAdapter(saleh_myadptr);
        saleh_mPager.setsaleh_animationEnabled(true);
        saleh_mPager.setsaleh_fadeEnabled(true);
        saleh_mPager.setsaleh_fadeFactor(0.9f);

        saleh_myadptr.notifyDataSetChanged();//



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saleh_myadptr =null;
        saleh_mPager.setAdapter(null);
        saleh_mPager.removeCallbacks(null);

    }

    private void loadNativeAds(int startindex) {

        if(startindex< saleh_current_index_loaded)
        {
            return;
        }
        try {
            saleh_mNativeAds.clear();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AdLoader.Builder builder = new AdLoader.Builder(getBaseContext(), native_ad).withAdListener(new AdListener() {
                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();

                            glory_class_settings.the_count_click++;
                            //  Toast.makeText(getActivity(), "ads clicked"+  class_settings.count_click, Toast.LENGTH_SHORT).show();
                            func_incrmet_clicks(getBaseContext());

                            if (glory_class_settings.the_count_click >= glory_class_settings.max_count_click) {
                                glory_class_settings.slava_chek_is_user_thift(getBaseContext(), glory_class_settings.the_count_click);
                                the_ads_active = false;

                                for (Object obja: genrl_objects_sList) {
                                    try{
                                        if(obja instanceof NativeAd)
                                        {
                                            ((NativeAd) obja).destroy();
                                        }
                                    }
                                    catch (Exception ex){}
                                }
                            }
                        }
                    });
                    saleh_adLoader = builder.forNativeAd(
                            new NativeAd.OnNativeAdLoadedListener() {
                                @Override
                                public void onNativeAdLoaded(NativeAd NativeAd) {

                                    if (isDestroyed()) {
                                        NativeAd.destroy();
                                        return;
                                    }

                                    saleh_mNativeAds.add(NativeAd);
                                    if (!saleh_adLoader.isLoading()) {
                                        if(saleh_myadptr !=null) {
                                            saleh_myadptr.notifyDataSetChanged();
                                            insertAdsInMenuItems(startindex);
                                        }
                                    }
                                    //adapter.notifyDataSetChanged();
                                    if(saleh_myadptr !=null) {
                                       // myadptr.notifyDataSetChanged();
                                    }

                                }
                            })

                            .build();

                    if(saleh_myadptr !=null) {
                       // myadptr.notifyDataSetChanged();
                    }
                    saleh_adLoader.loadAds(new AdRequest.Builder().build(), 5);

                }
            });
        } catch (NullPointerException ex) {
            Toast.makeText(getBaseContext(),ex.toString(),Toast.LENGTH_LONG).show();
            Log.e("errr1=",ex.toString());
        } catch (Exception ww) {
            Toast.makeText(getBaseContext(),ww.toString(),Toast.LENGTH_LONG).show();
            Log.e("errr1=",ww.toString());
        }

    }

    private void insertAdsInMenuItems(int startindex) {

        try {

            if (saleh_mNativeAds.size() <= 0) {
                return;
            }
            int offset = saleh_count_img_to_show_native_ads;
            int index = saleh_current_index_loaded +2;
            for (NativeAd ad : saleh_mNativeAds) {
                if(index< saleh_arr_objects.size()) {
                    saleh_arr_objects.add(index, ad);

                }


               // myadptr.notifyDataSetChanged();

                index = index + offset;
                Log.e("ads_inserted", "index=" + index + " ");
            }

            for (NativeAd ad : saleh_mNativeAds) {
                if(index< saleh_arr_objects.size()) {
                    saleh_arr_objects.add(index, ad);

                }
                index = index + offset;
                Log.e("ads_inserted", "index=" + index + " ");
            }

            for (NativeAd ad : saleh_mNativeAds) {
                if(index< saleh_arr_objects.size()) {
                    saleh_arr_objects.add(index, ad);

                }
                index = index + offset;
                Log.e("ads_inserted", "index=" + index + " ");
            }

            for (NativeAd ad : saleh_mNativeAds) {
                if(index< saleh_arr_objects.size()) {
                    saleh_arr_objects.add(index, ad);

                }
                index = index + offset;
                Log.e("ads_inserted", "index=" + index + " ");
            }
            saleh_myadptr.notifyDataSetChanged();


            //adapter.notifyDataSetChanged();
            saleh_myadptr.notifyDataSetChanged();
        } catch (NullPointerException ex) {
            Log.e("ads_error1", ex.toString());
        } catch (Exception ww) {
            Log.e("ads_error2", ww.toString());
        } finally {
            //adapter.notifyDataSetChanged();
            saleh_myadptr.notifyDataSetChanged();
        }
    }


    void aaconvtobitmap(final int imageView) {



        Bitmap Icon = BitmapFactory.decodeResource(getResources(), imageView);
        Palette.generateAsync(Icon, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette paletteImageView) {
                // Do something with colors...
                Palette.Swatch vibrant = paletteImageView.getDarkVibrantSwatch();

                int vibranta = paletteImageView.getDarkVibrantColor(4);//1
                int vibrantDark = paletteImageView.getDarkMutedColor(2);//1
                int vibrantLight = paletteImageView.getDarkVibrantColor(4);
                int muted = paletteImageView.getDarkVibrantColor(1);
                int mutedDark = paletteImageView.getDarkVibrantColor(4);
                int mutedLight = paletteImageView.getDarkVibrantColor(1);



                final GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[] {vibranta,vibrantDark,mutedDark,mutedLight,muted,vibrantLight});//      gd.setCornerRadius(0f);
                gd.setCornerRadius(50f);

                final GradientDrawable gd2 = new GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        new int[] {vibranta,vibrantDark,mutedDark,mutedLight,muted,vibrantLight});//      gd.setCornerRadius(0f);
                gd2.setCornerRadius(0f);

                ((FrameLayout)findViewById(R.id.myframemm)).setBackground(gd2);//groundResource(imageView);





                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TransitionDrawable transition = (TransitionDrawable) ((ImageView)findViewById(R.id.myimggg)).getBackground();
                        transition.startTransition(1000);

                        ((FrameLayout)findViewById(R.id.myframemm)).setBackground(gd);//groundResource(imageView);

                    }
                });


                int colorFrom = getResources().getColor(R.color.colorPrimary);
                int colorTo = getResources().getColor(R.color.gray_btn_bg_color);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(2000); // milliseconds
                colorAnimation.setEvaluator(new ArgbEvaluator());
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        ((FrameLayout)findViewById(R.id.myframemm)).setBackgroundColor((int) animator.getAnimatedValue());

                    }

                });
                colorAnimation.start();
                try {

                    Picasso.get()
                                .load(imageView)
                                .resize(4, 3)
                                // .fit()
                                //.transform(new RoundedTransformation(15, 0))
                                .centerCrop(Gravity.CENTER_VERTICAL)
                                .placeholder( R.drawable.glory_gradintlist)
                                .into((ImageView)findViewById(R.id.myimggg), new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        // myinterface.set_src(pos);
                                    }
                                    @Override
                                    public void onError(Exception e) {
                                    }

                                });

                } catch (IllegalArgumentException ex) {

                }


            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (NullPointerException e) {
        }
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.glory_second_activity);
        // overridePendingTransition(R.anim.from_left, R.anim.to_right);

        if(getIntent().hasExtra("recived_mg_name")) {
            saleh_recived_mg_name = getIntent().getStringExtra("recived_mg_name");
        }
        saleh_view_pager_single_slide = findViewById(R.id.view_pager_single_slide);
        saleh_wpManager = WallpaperManager.getInstance(this);

        //view_pagers = findViewById(R.id.view_pagers);

       // adapter = new slider_adapter(this, arr_objects, this);
         saleh_myadptr = new glory_ViewPagerAdapter(this, saleh_arr_objects, this);
       // view_pagers.setAdapter(myadptr);

       listRaw();
       // view_pagers.setPageMargin(100);


        //view_pagers.setVisibility(View.GONE);
        saleh_mPager = findViewById(R.id.kk_pager);

        saleh_fabSettings2 =findViewById(R.id.fabSettings2);


        saleh_fabSettings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                aaainitView();
            }
        });

        Log.e("startpos=", saleh_recived_mg_name + "");
        saleh_recycler_slider = findViewById(R.id.img_slid_recycl);
        txt_wait_ads=findViewById(R.id.txt_wait_ads);
        // recycler_slider.setHasFixedSize(true);
        saleh_recycler_slider.setItemViewCacheSize(20);
        saleh_recycler_slider.setDrawingCacheEnabled(true);
        saleh_recycler_slider.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        saleh_recylerViewLayoutManager = (new LinearLayoutManager(glory_second_activity.this, LinearLayoutManager.HORIZONTAL, false));
        //recycler_slider.setAdapter(ceatgries_adapter);
        saleh_recycler_slider.setLayoutManager(saleh_recylerViewLayoutManager);
        saleh_recycler_slider.setVisibility(View.GONE);
     
        SnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(saleh_recycler_slider);

        txt_wait_ads.setVisibility(View.GONE);
        //listRaw();

        loadAd();
        //my_frame=findViewById(R.id.my_frame);


        saleh_view_pager_single_slide.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = saleh_view_pager_single_slide.getMeasuredWidth() - saleh_view_pager_single_slide.getPaddingLeft() - saleh_view_pager_single_slide.getPaddingRight();
                int pageHeight = saleh_view_pager_single_slide.getHeight();
                int paddingLeft = saleh_view_pager_single_slide.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (saleh_view_pager_single_slide.getScrollX() + paddingLeft)) / pageWidth;

                if (saleh_mPageMargin <= 0 || !saleh_animationEnabled)
                    return;
                page.setPadding(saleh_mPageMargin / 3, saleh_mPageMargin / 3, saleh_mPageMargin / 3, saleh_mPageMargin / 3);
                if (MAX_SCALE == 0.0f && position > 0.0f && position < 1.0f) {
                    MAX_SCALE = position;
                }
                position = position - MAX_SCALE;
                float absolutePosition = Math.abs(position);
                if (position <= -1.0f || position >= 1.0f) {
                    if (fadeEnabled)
                        page.setAlpha(fadeFactor);
                    // Page is not visible -- stop any running animations

                } else if (position == 0.0f) {

                    // Page is selected -- reset any views if necessary
                    page.setScaleX((1 + MAX_SCALE));
                    page.setScaleY((1 + MAX_SCALE));
                    page.setAlpha(1);
                } else {
                    page.setScaleX(1 + MAX_SCALE * (1 - absolutePosition));
                    page.setScaleY(1 + MAX_SCALE * (1 - absolutePosition));
                    if (fadeEnabled)
                        page.setAlpha(Math.max(fadeFactor, 1 - absolutePosition));
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //doShowInterstitial();
    }
    
    private void aaainitView() {

        saleh_dialog_setwallpaper = new glory_dialog_setting(this, 0, 0,
                R.layout.glory_set_wallpaper, R.style.Theme_dialog, Gravity.BOTTOM, R.style.DialogAnimation);




        final CardView cardview_sett = saleh_dialog_setwallpaper.findViewById(R.id.cardview_sett);
        CardView card_set_wall = saleh_dialog_setwallpaper.findViewById(R.id.card_set_wall);
        CardView card_share = saleh_dialog_setwallpaper.findViewById(R.id.card_share);
        CardView card_download = saleh_dialog_setwallpaper.findViewById(R.id.card_download);
        CardView card_back = saleh_dialog_setwallpaper.findViewById(R.id.card_back);
        CardView card_share_whats = saleh_dialog_setwallpaper.findViewById(R.id.card_share_whats);
        card_share.setOnClickListener(this);
        card_share_whats.setOnClickListener(this);
        cardview_sett.setOnClickListener(this);
        card_set_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardview_sett.setVisibility(View.VISIBLE);
            }
        });
        card_download.setOnClickListener(this);
        card_back.setOnClickListener(this);
        saleh_btn_lock = (Button) saleh_dialog_setwallpaper.findViewById(R.id.btn_lock);
        saleh_btn_lock.setOnClickListener(this);
        saleh_btn_desktop = (Button) saleh_dialog_setwallpaper.findViewById(R.id.btn_desktop);
        saleh_btn_desktop.setOnClickListener(this);
        saleh_btn_all = (Button) saleh_dialog_setwallpaper.findViewById(R.id.btn_all);
        saleh_btn_all.setOnClickListener(this);

        saleh_dialog_setwallpaper.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        saleh_dialog_setwallpaper.show();

    }

    @Override
    public void onClick(View v) {
        saleh_animat(v);
        int id = v.getId();
        if (id == R.id.card_back) {
            saleh_dialog_setwallpaper.dismiss();
        } else if (id == R.id.btn_lock) {
            saleh_setLockScreenWallpaper();
            saleh_dialog_setwallpaper.dismiss();
        } else if (id == R.id.btn_desktop) {
            saleh_setDesktopWallpaper();

            saleh_dialog_setwallpaper.dismiss();
            doShowInterstitial();
        } else if (id == R.id.btn_all) {
            saleh_setAllWallpaper();
            saleh_dialog_setwallpaper.dismiss();
        } else if (id == R.id.card_download) {
            if (saleh_permission_allowed || saleh_isStoragePermissionGranted()) {
                saleh_permission_allowed = true;
                doShowInterstitial();
                save_image_saleh_saleh_to_sd_card();
            }
        } else if (id == R.id.card_share) {
            saleh_share_img(0);
        } else if (id == R.id.card_share_whats) {
            saleh_share_img(1);
        }

    }

    private void saleh_setLockScreenWallpaper() {


        if (saleh_permission_allowed || saleh_isStoragePermissionGranted()) {
            saleh_permission_allowed = true;
            //save_image_to_sd_card();


            Glide.with(this).load(src).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    try {

                        Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), src);
                        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), imgBitmap, "title", null);
                        Uri resourceURI = Uri.parse(imgBitmapPath);
                        Log.e("resourc==", resourceURI.toString());
                        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                        intent.setDataAndType(resourceURI, "image/jpg");//Uri.fromFile(new File(String.valueOf(resourceURI)))
                        intent.putExtra("mimeType", "image/jpg");
                        glory_second_activity.this.startActivity(Intent.createChooser(intent, "تعيين كخلفية:"));
                        //Toast.makeText(second_activity.this, "تم تعيين خلفية بنجاح", Toast.LENGTH_SHORT).show();
                        // }
                    } catch (Throwable e) {
                        Log.e("err", e.toString());
                        // Toast.makeText(com.ammargallary.galarry.second_activity.this, "فشل في تعيين الخلفية", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    void saleh_animat(View view) {
        anim_view(glory_second_activity.this).setInterpolator(glory_public_class.saleh_interpolator);
        view.startAnimation(anim_view(glory_second_activity.this));
    }

    void saleh_share_img(int typ_share)//whats 1
    {
        if (saleh_permission_allowed || saleh_isStoragePermissionGranted()) {
            saleh_permission_allowed = true;
            //save_image_to_sd_card();

            Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), src);
            String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), imgBitmap, "title", null);

            Uri uri = Uri.parse(imgBitmapPath);
            //Uri uri=Uri.parse("R.drawable.mm2");
            Log.e("share_file=", uri.toString());
            Intent shareCaptionIntent = new Intent(Intent.ACTION_SEND);
            if (typ_share == 1) {
                shareCaptionIntent.setPackage("com.whatsapp");

            }
            shareCaptionIntent.setType("image/*");
            shareCaptionIntent.putExtra(Intent.EXTRA_STREAM, uri);


            if (typ_share == 0) {
                shareCaptionIntent.putExtra(Intent.EXTRA_STREAM, uri);
                //set caption
                String shre_txt = "*" + getString(R.string.shar_txt) + "*";
                shre_txt += "\n";
                shre_txt += "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                shareCaptionIntent.putExtra(Intent.EXTRA_TEXT, shre_txt);
            }
            shareCaptionIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareCaptionIntent, getString(R.string.share)));
        }
    }

    void save_image_saleh_saleh_to_sd_card() {

        ByteArrayOutputStream bytearrayoutputstream;
        File file;
        FileOutputStream fileoutputstream;
        Drawable drawable = getResources().getDrawable(src);
        bytearrayoutputstream = new ByteArrayOutputStream();

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytearrayoutputstream);

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/myWallpaper");
        if (!f.isDirectory()) {
            f.mkdirs();
        }
        String filname = getResources().getResourceEntryName(src);
        Log.e("image_name=", filname);
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/myWallpaper/" + filname + ".jpg");

        try {
            file.createNewFile();

            fileoutputstream = new FileOutputStream(file);

            fileoutputstream.write(bytearrayoutputstream.toByteArray());

            fileoutputstream.close();
            saleh_back_ground_file_path = "file://" + file.getAbsolutePath();
        } catch (Exception e) {

            e.printStackTrace();

        }
        Toast.makeText(glory_second_activity.this, " تم حفظ الصورة بنجاح ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            save_image_saleh_saleh_to_sd_card();
        }
    }

    public boolean saleh_isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("", "Permission is granted");
                return true;
            } else {

                AlertDialog dialog = new AlertDialog.Builder(glory_second_activity.this)
                        .setTitle(" السماح للتطبيق")
                        .setMessage("يتطلب منح التطبيق قراءة الصورة ")
                        .setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(glory_second_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                        // return false;
                                    }
                                }).setNegativeButton("no",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                dialog.show();
                Log.e("", "Permission is revoked");
                return false;
            }
        } else {
            Log.e("", "Permission is granted");
            return true;
        }
    }

    private void saleh_setAllWallpaper() {
        Glide.with(this).load(src).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    Class class1 = saleh_wpManager.getClass();
                    Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);
                    setWallPaperMethod.invoke(saleh_wpManager, resource);
                    Toast.makeText(glory_second_activity.this, "Lock screen wallpaper set successfully", Toast.LENGTH_SHORT).show();
                } catch (Throwable e) {
                    Toast.makeText(glory_second_activity.this, "Lock screen wallpaper settings failed", Toast.LENGTH_SHORT).show();
                }
                try {
                    saleh_wpManager.setBitmap(resource);
                    Toast.makeText(glory_second_activity.this, "Desktop wallpaper is set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(glory_second_activity.this, "Desktop wallpaper settings failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saleh_copy2(InputStream src, File dst) throws IOException {
        InputStream in = src;

        OutputStream out = new FileOutputStream(dst);
        //InputStream in = new FileInputStream(src);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        saleh_addImageGallery(dst);
    }

    private void saleh_addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void saleh_myfunc() {
        String filename = getResources().getResourceEntryName(src);

        String destfolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Motivational Quotes";
        createDirIfNotExists(destfolder);

        String destinationPath = destfolder + "/" + filename + ".jpg";
        File destination = new File(destinationPath);

        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier(filename,
                        "raw", getPackageName()));


        try {
            saleh_copy2(ins, destination);

            File externalFile = new File(destinationPath);
            Uri sendUri2 = Uri.fromFile(externalFile);
            Log.d("URI:", sendUri2.toString());

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.setDataAndType(sendUri2, "image/jpg");
            intent.putExtra("mimeType", "image/jpg");
            startActivityForResult(Intent.createChooser(intent, "Set As"), 200);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saleh_setDesktopWallpaper() {
        Glide.with(this).load(src).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                        saleh_wpManager.setBitmap(resource);

                    } else {
                        Toast.makeText(glory_second_activity.this, "هذا الهاتف غير مدعوم", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(glory_second_activity.this, "تم بنجاح", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(glory_second_activity.this, "فشل في تغيير الخلفية", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void saleh_set_src(int pos) {
        Log.e("pos=", pos + "");
        src = getResources().getIdentifier("drawable/" + ((glory_image_class) saleh_arr_objects.get(pos)).getName(), "drawable", getPackageName());

    }

    @Override
    public void saleh_finsh_activity() {
        finish();
    }


    @Override
    public void saleh_show_ads_full() {
        doShowInterstitial();

    }


    @Override
    public void saleh_save_image() {
        if (saleh_permission_allowed || saleh_isStoragePermissionGranted()) {
            saleh_permission_allowed = true;
            doShowInterstitial();
            save_image_saleh_saleh_to_sd_card();
        }
    }

    @Override
    public void saleh_set_wall_clic() {
        saleh_dialog_setwallpaper.show();

    }

    @Override
    public void saleh_shareing_img() {
        saleh_share_img(0);
    }

    @Override
    public void saleh_add_fav_to_db(int pos) {

        aaainitView();


    }

    @Override
    public void saleh_remove_from_db(int pos) {
        Log.e("to_remove=", saleh_myarr_imgs.get(pos).getName());
        glory_DatabaseHelper dbhelper = new glory_DatabaseHelper(glory_second_activity.this);
        dbhelper.remove_data("delete from " + dbhelper.saleh_tbl_setting + " where namecell='" + (saleh_myarr_imgs.get(pos).getName()) + "'");
        // myarr_fav.remove(pos);
    }

    @Override
    public void saleh_load_again_nativ_ads(int start_index) {

       if(saleh_adLoader !=null){
           saleh_adLoader.loadAds(new AdRequest.Builder().build(), 5);
       }
       else
        loadNativeAds(start_index);

    }

    @Override
    public void saleh_change_background(int imgids) {

        if (saleh_arr_objects.get(saleh_mPager.getCurrentItem()) instanceof NativeAd) {

            ((FrameLayout)findViewById(R.id.myframemm)).setBackgroundResource(R.drawable.glory_gradient_live);//groundResource(imageView);
            ((ImageView)findViewById(R.id.myimggg)).setImageResource(R.drawable.glory_gradient_live);

            saleh_fabSettings2.setVisibility(View.GONE);
        }
        else {
            saleh_fabSettings2.setVisibility(View.VISIBLE);
            int indx = saleh_mPager.getCurrentItem();
            if (saleh_arr_objects.get(saleh_mPager.getCurrentItem()) instanceof glory_image_class) {
                int imageKey2 = getResources().getIdentifier("drawable/" + ((glory_image_class) saleh_arr_objects.get(indx)).getName(), "drawable", getPackageName());
                src = imageKey2;
                aaconvtobitmap(imageKey2);
            }
        }
    }

    @Override
    public void onsuccess(String toJson, FrameLayout framcolor) {
        Log.e("frmactivcolor_thif",toJson);
    }
}