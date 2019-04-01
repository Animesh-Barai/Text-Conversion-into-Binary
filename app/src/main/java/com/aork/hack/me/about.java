package com.aork.hack.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class about extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        AdView firstAd = findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-6575183577141875~2941641073");
        AdRequest adRequest = new AdRequest.Builder().build();
        firstAd.loadAd(adRequest);




        /*INTERISTIA; ADS*/
        prepareAD();
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }
                        else {
                            Log.d("TAG", "interistial ad not loaded");
                        }
                        prepareAD();
                    }
                });
            }
        },30, 30, TimeUnit.SECONDS);



    }

    /*ADMOB ADS*/

    public void prepareAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6575183577141875/5463862632");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}


    /*interistail*/




