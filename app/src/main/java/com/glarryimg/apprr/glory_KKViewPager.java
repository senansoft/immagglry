package com.glarryimg.apprr;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class glory_KKViewPager extends ViewPager implements ViewPager.PageTransformer {
    public static final String TAG = "KKViewPager";
    private float saleh_MAX_SCALE = 0.2f;
    private int saleh_mPageMargin;
    private boolean saleh_animationEnabled =true;
    private boolean saleh_fadeEnabled =false;
    private  float saleh_fadeFactor =0.5f;


    public glory_KKViewPager(Context context) {
        this(context, null);
    }

    public glory_KKViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClipChildren(false);
        setClipToPadding(false);
        // to avoid fade effect at the end of the page
        setOverScrollMode(2);
        setPageTransformer( false,this);
        setOffscreenPageLimit(0);
        saleh_mPageMargin = dp2px(context.getResources(), 30);
        setPadding(saleh_mPageMargin, saleh_mPageMargin, saleh_mPageMargin, saleh_mPageMargin);
    }

    public int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }
    public void setsaleh_animationEnabled(boolean enable) {
        this.saleh_animationEnabled = enable;
    }

    public void setsaleh_fadeEnabled(boolean saleh_fadeEnabled) {
        this.saleh_fadeEnabled = saleh_fadeEnabled;
    }

    public void setsaleh_fadeFactor(float saleh_fadeFactor) {
        this.saleh_fadeFactor = saleh_fadeFactor;
    }

    @Override
    public void setPageMargin(int marginPixels) {
        saleh_mPageMargin = marginPixels;
        setPadding(saleh_mPageMargin, saleh_mPageMargin, saleh_mPageMargin, saleh_mPageMargin);
    }

    @Override
    public void transformPage(View page, float position) {
        if (saleh_mPageMargin <= 0|| !saleh_animationEnabled)
            return;
        page.setPadding(saleh_mPageMargin / 3, saleh_mPageMargin / 3, saleh_mPageMargin / 3, saleh_mPageMargin / 3);

        if (saleh_MAX_SCALE == 0.0f && position > 0.0f && position < 1.0f) {
            saleh_MAX_SCALE = position;
        }
        position = position - saleh_MAX_SCALE;
        float absolutePosition = Math.abs(position);
        if (position <= -1.0f || position >= 1.0f) {
            if(saleh_fadeEnabled)
                page.setAlpha(saleh_fadeFactor);
            // Page is not visible -- stop any running animations

        } else if (position == 0.0f) {

            // Page is selected -- reset any views if necessary
            page.setScaleX((1 + saleh_MAX_SCALE));
            page.setScaleY((1 + saleh_MAX_SCALE));
            page.setAlpha(1);
        } else {
            page.setScaleX(1 + saleh_MAX_SCALE * (1 - absolutePosition));
            page.setScaleY(1 + saleh_MAX_SCALE * (1 - absolutePosition));
            if(saleh_fadeEnabled)
                page.setAlpha( Math.max(saleh_fadeFactor, 1 - absolutePosition));
        }
    }
}