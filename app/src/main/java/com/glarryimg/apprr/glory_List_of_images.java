package com.glarryimg.apprr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.glarryimg.modulads.adsa.glory_app.glory_BaseallActivity;
import com.glarryimg.modulads.adsa.glory_app.glory_class_settings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kobakei.ratethisapp.RateThisApp;

import java.util.ArrayList;
import java.util.List;

import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_ads_active;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_check_ads;
import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_incrmet_clicks;
import static com.glarryimg.modulads.adsa.glory_app.glory_my_ads_unit.fires_bnr_ads;

import static com.glarryimg.apprr.glory_public_class.curent_count;
import static com.glarryimg.apprr.glory_public_class.curet_prefix;
import static com.glarryimg.apprr.glory_public_class.saleh_myarr_imgs;


public class glory_List_of_images extends glory_BaseallActivity implements glory_myinterface {

    private BottomNavigationView bottom_navgation;

    glory_DatabaseHelper saleh_dbhelper;
    RecyclerView saleh_recycl_single_majlaha;
    glory_listImage_of_mjla_adapter saleh_mydpr;
    ArrayList<Integer> saleh_mytemp;
    boolean isLastItemReached;
    FrameLayout saleh_progressBarHolder_vertical;
    AdView saleh_mAdView_bottom;
    boolean saleh_replace_fav_instead_of_all;
    boolean saleh_fabExpanded;
    LinearLayout saleh_layoutFabshare_app, saleh_layoutFab_more_app, saleh_layoutFab_rate;
    private FloatingActionButton saleh_fabSettings;
    ImageView saleh_img_exit, saleh_img_menu;
    TextView txt_current_section,txt_threedquotes,txt_advices, txt_funny_quotes, txt_wallpaper, txt_cute_hands;
    LinearLayout ads_in_bottom;


    void check_for_update() {
        FirebaseFirestore ipadressdb;
        ipadressdb = FirebaseFirestore.getInstance();
        try {

            ipadressdb.collection("com_apps_update")
                    .whereEqualTo("comname", getPackageName()).get().
                    addOnSuccessListener(glory_List_of_images.this, new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if (!queryDocumentSnapshots.isEmpty()) {


                                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                    glory_updatemodel comment_tModel = d.toObject(glory_updatemodel.class);
                                    Log.e("newcom=", comment_tModel.getNew_com());

                                    if (comment_tModel.getNew_com()!=null&&!comment_tModel.getNew_com().equalsIgnoreCase(getPackageName())) {

                                        show_dialog_update(comment_tModel.getNew_com());
                                    }
                                    Log.e("newcom=", comment_tModel.getNew_com());

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (Exception ex) {
        }
    }
    private void saleh_hide_animateion() {

        saleh_progressBarHolder_vertical.setAlpha(0.5f);
        saleh_progressBarHolder_vertical.animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        saleh_progressBarHolder_vertical.setVisibility(View.GONE);
                    }
                });
    }



    private void saleh_my_func_show_pop_up(View myview) {
        PopupMenu still_popuparr = new PopupMenu(glory_List_of_images.this, myview);
        still_popuparr.getMenuInflater()
                .inflate(R.menu.glory_menu_item, still_popuparr.getMenu());

        still_popuparr.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.policy_page) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.policy_url))));
                    }
                } else if (itemId == R.id.rate_menu) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://play.google.com/store/apps/details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                } else if (itemId == R.id.more_menu) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id==" + getString(R.string.devlper_name))));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.devlper_name))));
                    }
                }
                return true;
            }
        });

        still_popuparr.show(); //showing popup menu

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        super.onCreate(savedInstanceState);




        setContentView(R.layout.glory_activity_images);
        ads_in_bottom = findViewById(R.id.ads_in_bottom);

        saleh_mAdView_bottom = new AdView(glory_List_of_images.this);
        saleh_mAdView_bottom.setAdSize(AdSize.FULL_BANNER);
        saleh_mAdView_bottom.setAdUnitId(fires_bnr_ads);
        check_for_update();
        saleh_progressBarHolder_vertical = findViewById(R.id.progressBarHolder_my_products);
        //load_banner(mAdView_top);
        saleh_mytemp = new ArrayList<>();
        saleh_fabSettings = findViewById(R.id.fabSettings);
        txt_advices= findViewById(R.id.txt_romantc);
        txt_current_section= findViewById(R.id.txt_current_section);
        txt_threedquotes= findViewById(R.id.txt_threedquotes);
        txt_funny_quotes = findViewById(R.id.txt_funny_quotes);
        txt_cute_hands = findViewById(R.id.txt_cute_hands);
        txt_wallpaper = findViewById(R.id.txt_wallpaper);
        FirebaseApp.initializeApp(getBaseContext());
        saleh_check_enable_ads();
        saleh_mAdView_bottom.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                glory_class_settings.the_count_click++;
            }
        });

        saleh_img_exit =findViewById(R.id.img_exit);
        saleh_img_menu =findViewById(R.id.img_menu);
        saleh_layoutFabshare_app = findViewById(R.id.layoutFabshare_app);
        saleh_layoutFab_more_app = findViewById(R.id.layoutFab_more_app);
        saleh_layoutFab_rate = findViewById(R.id.layoutFab_rate);
        saleh_img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saleh_img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleh_my_func_show_pop_up(v);
            }
        });

        saleh_mydpr = new glory_listImage_of_mjla_adapter(getBaseContext(), saleh_myarr_imgs, true, this, this);
        saleh_recycl_single_majlaha = findViewById(R.id.recycl_single_majlaha);
        saleh_recycl_single_majlaha.setNestedScrollingEnabled(true);
        saleh_recycl_single_majlaha.setVisibility(View.VISIBLE);
        bottom_navgation =  findViewById(R.id.bottom_navgation);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(Integer.parseInt(getString(R.string.sizeimage_recylc_inrow)), StaggeredGridLayoutManager.VERTICAL);

        saleh_recycl_single_majlaha.setLayoutManager(mLayoutManager);
        saleh_recycl_single_majlaha.setHasFixedSize(true);
        saleh_recycl_single_majlaha.setItemViewCacheSize(20);
        saleh_recycl_single_majlaha.setDrawingCacheEnabled(true);
        saleh_recycl_single_majlaha.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        saleh_recycl_single_majlaha.setAdapter(saleh_mydpr);

        saleh_layoutFab_more_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleh_closeSubMenusFab();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=" + getString(R.string.devlper_name))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.devlper_name))));
                }
            }
        });
        saleh_layoutFab_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleh_closeSubMenusFab();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://play.google.com/store/apps/details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        saleh_layoutFabshare_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleh_closeSubMenusFab();
                //set caption
                String shre_txt = "*" + getString(R.string.shar_txt) + "*";
                shre_txt += "\n";
                shre_txt += "https://play.google.com/store/apps/details?id=" + getPackageName();


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                // shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shre_txt);
                //shareIntent.putExtra(Intent.EXTRA_STREAM, uri2);

                //shareIntent.setType("image/png");

                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(shareIntent, "مشاركة رابط التطبيق "));
            }
        });

        bottom_navgation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.main_page_options) {
                    saleh_replace_fav_instead_of_all = false;
                    saleh_listRaw(curet_prefix, curent_count);
                } else if (itemId == R.id.favorit_menu_options) {
                    saleh_replace_fav_instead_of_all = true;
                    //   get_data_favlist();
                } else if (itemId == R.id.rate_menu_optins) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://play.google.com/store/apps/details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                } else if (itemId == R.id.more_menu_options) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id==" + getString(R.string.devlper_name))));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.devlper_name))));
                    }
                }
                return false;
            }
        });


        saleh_fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saleh_fabExpanded) {
                    saleh_closeSubMenusFab();
                } else {
                    saleh_openSubMenusFab();
                }
            }
        });

        saleh_closeSubMenusFab();

        saleh_show_reat();
        saleh_listRaw("walls",Integer.parseInt(getString(R.string.count_walls)));
       // statr_anim();
        saleh_load_banner(saleh_mAdView_bottom);



        saleh_mAdView_bottom.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {

                super.onAdClicked();

                glory_class_settings.the_count_click++;
                func_incrmet_clicks(getBaseContext());

                if (glory_class_settings.the_count_click >= glory_class_settings.max_count_click) {
                    glory_class_settings.slava_chek_is_user_thift(getBaseContext(), glory_class_settings.the_count_click);
                    the_ads_active = false;
                    saleh_mAdView_bottom.setVisibility(View.GONE);
                    saleh_mAdView_bottom.destroy();

                }
            }
        });




        txt_wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curent_count=Integer.parseInt(getString(R.string.count_walls));
                txt_current_section.setText(getString(R.string.wallpaper_label));
                saleh_listRaw("walls",Integer.parseInt(getString(R.string.count_walls)));

            }
        });
        txt_funny_quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_current_section.setText(getString(R.string.funny_label));

                curent_count=Integer.parseInt(getString(R.string.count_funny));
                saleh_listRaw("funny",curent_count);

            }
        });
        txt_cute_hands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_current_section.setText(getString(R.string.cutes_label));

                curent_count=Integer.parseInt(getString(R.string.count_cutes));
                saleh_listRaw("cute",curent_count);

            }
        });

        txt_threedquotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_current_section.setText(getString(R.string.qutes_label));

                curent_count=Integer.parseInt(getString(R.string.count_quotes));
                saleh_listRaw("quots",curent_count);

            }
        });
        txt_advices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_current_section.setText(getString(R.string.advices_label));

                curent_count=Integer.parseInt(getString(R.string.count_Advices));
                saleh_listRaw("advice",curent_count);

            }
        });

    }

    void saleh_check_enable_ads() {
        Log.e("start_call=", "checking....");


        DatabaseReference secondary = FirebaseDatabase.getInstance()
                .getReference();
        secondary.child("apps_statue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("snapshot=", snapshot.toString());
                try {
                    the_ads_active = snapshot.child("ammar_gallary").getValue(Boolean.class);
                    Log.e("ammar_gallary=", the_ads_active + "");

                } catch (NullPointerException ex) {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("errrrror=", error.toString());
                // Toast.makeText(getBaseContext(),ads_statues+"",Toast.LENGTH_LONG).show();

            }
        });

    }


    void saleh_load_banner(AdView ads) {
        AdRequest adRequest = null;

        adRequest = new AdRequest.Builder()
                .build();
        if (func_check_ads(ads.getAdUnitId())) {
            ads.loadAd(adRequest);
            ads_in_bottom.removeAllViews();
            ads_in_bottom.addView(ads);
        }
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();

        //myarr.clear();
        overridePendingTransition(R.anim.to_left, R.anim.from_right);
        finish();
    }*/

    void saleh_show_reat() {
        RateThisApp.Config config = new RateThisApp.Config(2, 2);
        RateThisApp.init(config);

        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);


        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                Toast.makeText(glory_List_of_images.this, "Yes event", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoClicked() {
                Toast.makeText(glory_List_of_images.this, "No event", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelClicked() {
                Toast.makeText(glory_List_of_images.this, "Cancel event", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void saleh_closeSubMenusFab() {
        saleh_fabSettings.setImageResource(R.drawable.glory_menu_icons);

        findViewById(R.id.fabFrame).setVisibility(View.GONE);

        saleh_fabExpanded = false;
    }

    private void saleh_openSubMenusFab() {
        saleh_fabSettings.setImageResource(R.drawable.glory_go_back);

        findViewById(R.id.fabFrame).setVisibility(View.VISIBLE);

        //Change settings icon to 'X' icon
        // ((FloatingActionButton)findViewById(R.id.fabSettings)).setImageResource(R.drawable.ic_close_black_24dp);
        saleh_fabExpanded = true;
    }


    public void show_dialog_update(String newpathapp) {
         Dialog rateDialog;

        rateDialog = new Dialog(this, R.style.Theme_Dialog);

        rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateDialog.setCancelable(true);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = rateDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        rateDialog.setCancelable(false);
        rateDialog.setContentView(R.layout.glory_update_app);
        final AppCompatRatingBar AppCompatRatingBar_dialog_rating_app = (AppCompatRatingBar) rateDialog.findViewById(R.id.AppCompatRatingBar_dialog_rating_app);
        final LinearLayout linear_layout_feedback = (LinearLayout) rateDialog.findViewById(R.id.linear_layout_feedback);
        final LinearLayout linear_layout_rate = (LinearLayout) rateDialog.findViewById(R.id.linear_layout_rate);
        final Button btn_update_now = (Button) rateDialog.findViewById(R.id.btn_update_now);
        btn_update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + newpathapp)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + newpathapp)));
                }
                rateDialog.dismiss();
            }
        });


        rateDialog.show();

    }

    public List<glory_image_class> saleh_listRaw(String prefixi_name, int countimg) {
        saleh_myarr_imgs.clear();
        if (saleh_myarr_imgs.size() == 0) {
            saleh_myarr_imgs.clear();
            try {

                for (int i = 0; i < countimg; i++) {
                    //String url = "drawable/" + "mm" + i;

                    saleh_myarr_imgs.add(new glory_image_class(prefixi_name + i, false));
                  //  Log.e("mmmmmid=", i + "");
                }

                //load_ten();
                //recycl_single_majlaha.getRecycledViewPool().clear();

                saleh_mydpr.notifyDataSetChanged();
                saleh_hide_animateion();
                //get_product_pagenation();
            } catch (Exception e) {
                saleh_hide_animateion();
                Log.e("erroorrr", e.toString());
            }
        }
       /* else
        {
            //load_ten();
            //get_product_pagenation();
        }
        //mydpr.notifyDataSetChanged();*/
        return (saleh_myarr_imgs);
    }
/*
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        List_of_images.super.onBackPressed();
                    }
                }).create().show();
    }

*/



    @Override
    protected void onResume() {
        super.onResume();
        saleh_load_banner(saleh_mAdView_bottom);
    }




    @Override
    public void add_fav_to_db(int pos) {
        Log.e("to_add_fav=", saleh_myarr_imgs.get(pos).getName());
        saleh_dbhelper = new glory_DatabaseHelper(glory_List_of_images.this);
        saleh_dbhelper.remove_data("delete from " + saleh_dbhelper.saleh_tbl_setting + " where namecell='" + saleh_myarr_imgs.get(pos).getName() + "'");
        ContentValues cv;
        cv = new ContentValues();
        cv.put("namecell", saleh_myarr_imgs.get(pos).getName());
        cv.put("fav", true);
        saleh_dbhelper.add_new_data(cv, saleh_dbhelper.saleh_tbl_setting);


    }

    @Override
    public void remove_from_db(int pos) {
        Log.e("to_remove=", saleh_myarr_imgs.get(pos).getName());
        saleh_dbhelper = new glory_DatabaseHelper(glory_List_of_images.this);
        saleh_dbhelper.remove_data("delete from " + saleh_dbhelper.saleh_tbl_setting + " where namecell='" + (saleh_myarr_imgs.get(pos).getName()) + "'");
    }

    @Override
    public void load_again_nativ_ads() {

    }

    @Override
    public void get_current_typeimg_and_count(String prefix_name, int countimg) {

    }
}
