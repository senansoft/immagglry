package com.glarryimg.apprr;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class glory_public_class
{
    public static int saleh_current_index_loaded =1;
    public static int saleh_start_pos_opend_pager =6;
    public static int saleh_count_img_to_show_native_ads =6;
    public static   String curet_prefix="romanc";
    public static   int curent_count=50;

    public  static ArrayList<String> saleh_adarr =new ArrayList<>();
    public static glory_MyBounceInterpolator saleh_interpolator = new glory_MyBounceInterpolator(0.6, 10);
    public static ArrayList<glory_image_class> saleh_myarr_imgs =new ArrayList<>();
    public static ArrayList<Object> saleh_arr_objects =new ArrayList<>();
    public  static Animation anim_view(Context context)
    {
        return  AnimationUtils.loadAnimation(context, R.anim.glory_milkshake);

    }


}
