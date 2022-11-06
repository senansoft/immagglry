package com.glarryimg.modulads.glory_high_projct.vedio_pro.glory_aid_ramthan;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.glarryimg.apprr.R;


public class glory_CustomDialog_settings extends Dialog {

    public glory_CustomDialog_settings(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, layout, style,
                Gravity.BOTTOM);
    }

    public glory_CustomDialog_settings(Context context, int width, int height, int layout,
                                       int style, int gravity, int anim) {
        super(context, style);

        setContentView(layout);


        Window windowsa = getWindow();
        WindowManager.LayoutParams params = windowsa.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;

        windowsa.setAttributes(params);


        windowsa.setWindowAnimations(anim);
    }

    public glory_CustomDialog_settings(Context context, int width, int height, int layout,
                                       int style, int gravity) {
        this(context, width, height, layout, style, R.style.DialogAnimation, gravity
                );
    }
}
