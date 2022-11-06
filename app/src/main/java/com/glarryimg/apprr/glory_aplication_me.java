package com.glarryimg.apprr;


import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.the_ads_active;
import static com.glarryimg.modulads.adsa.glory_app.glory_my_ads_unit.open_ad;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Date;


public class glory_aplication_me extends Application
        implements ActivityLifecycleCallbacks, LifecycleObserver {

    private AppOpenAdManager saleh_appOpenAdManager;
    private Activity saleh_currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this);

        this.registerActivityLifecycleCallbacks(this);
        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(
                            @NonNull InitializationStatus initializationStatus) {
                    }
                });

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        saleh_appOpenAdManager = new AppOpenAdManager();
    }


    @OnLifecycleEvent(Event.ON_START)
    protected void onMoveToForeground() {
        // Show the ad (if available) when the galarry moves to foreground.
        saleh_appOpenAdManager.showAdIfAvailable(saleh_currentActivity);
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

        if (!saleh_appOpenAdManager.saleh_isShowingAd) {
            saleh_currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }


    public void showAdIfAvailable(
            @NonNull Activity activity,
            @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {

        saleh_appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }


    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    private class AppOpenAdManager {

        private static final String LOG_TAG = "AppOpenAdManager";

        private AppOpenAd saleh_appOpenAd = null;
        private boolean saleh_isLoadingAd = false;
        private boolean saleh_isShowingAd = false;

        private long saleh_loadTime = 0;

        public AppOpenAdManager() {
        }


        private void loadAd(Context context) {
            // Do not load ad if there is an unused ad or one is already loading.
            if (saleh_isLoadingAd || isAdAvailable()) {
                return;
            }

            saleh_isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(
                    context,
                    open_ad,
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAdLoadCallback() {

                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            saleh_appOpenAd = ad;
                            saleh_isLoadingAd = false;
                            saleh_loadTime = (new Date()).getTime();

                            Log.d(LOG_TAG, "onAdLoaded.");
                           // Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            saleh_isLoadingAd = false;
                            Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                            //Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - saleh_loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        private boolean isAdAvailable() {

            return saleh_appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }


        private void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(
                    activity,
                    new OnShowAdCompleteListener() {
                        @Override
                        public void onShowAdComplete() {
                            // Empty because the user will go back to the activity that shows the ad.
                        }
                    });
        }


        private void showAdIfAvailable(
                @NonNull final Activity activity,
                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            // If the galarry open ad is already showing, do not show the ad again.
            if (saleh_isShowingAd) {
                Log.d(LOG_TAG, "The galarry open ad is already showing.");
                return;
            }

            if (!isAdAvailable()) {
                Log.d(LOG_TAG, "The galarry open ad is not ready yet.");
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
                return;
            }

            Log.d(LOG_TAG, "Will show ad.");

            saleh_appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        /** Called when full screen content is dismissed. */
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            saleh_appOpenAd = null;
                            saleh_isShowingAd = false;

                            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
                           // Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT).show();

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            saleh_appOpenAd = null;
                            saleh_isShowingAd = false;

                            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                            //Toast.makeText(activity, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT).show();

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
                            //Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT).show();
                        }
                    });

            saleh_isShowingAd = true;
            if(the_ads_active) {
                saleh_appOpenAd.show(activity);
            }
        }
    }
}
