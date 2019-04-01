package com.aork.hack.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DecryptedMessage extends AppCompatActivity {
    Button HomeBut;
    TextView decrypted_message;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypted_message);

        HomeBut = (Button)findViewById(R.id.button7);
        decrypted_message = (TextView)findViewById(R.id.textView13);

        Intent intent = getIntent();
        final String enc_msg = intent.getStringExtra("decrypted_message");

        decrypted_message.setText(enc_msg);

        HomeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DecryptedMessage.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
        },25, 25, TimeUnit.SECONDS);



    }

    /*ADMOB ADS*/

    public void prepareAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6575183577141875/5463862632");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}


