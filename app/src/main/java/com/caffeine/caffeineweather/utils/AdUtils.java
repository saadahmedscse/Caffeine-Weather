package com.caffeine.caffeineweather.utils;

/*

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.caffeine.theworldtoday.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdUtils {

    private static final AdRequest adRequest = new AdRequest.Builder().build();
    private static RewardedAd mRewardedAd;
    @SuppressLint("StaticFieldLeak")
    private static AdUtils adUtils;
    private Context context;

    private AdUtils(Context context){
        MobileAds.initialize(context, initializationStatus -> {});
        this.context = context;
    }

    public static AdUtils getInstance(Context context){
        if(adUtils == null) {
            adUtils = new AdUtils(context);
        }
        return adUtils;
    }

    public void loadVideoAd(){
        RewardedAd.load(context, context.getString(R.string.rewarded_ad_id),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }

    public void showVideoAd(Activity activity){
        if (mRewardedAd != null){
            mRewardedAd.show(activity, rewardItem -> {
                //reward item
            });
        }
    }
}

 */
