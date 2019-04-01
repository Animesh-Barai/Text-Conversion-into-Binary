package com.aork.hack.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Decrypt extends AppCompatActivity {
    EditText dec_msg;
    Button nextBut;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        nextBut = (Button)findViewById(R.id.button4);
        dec_msg = (EditText)findViewById(R.id.editText3);

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                msg = dec_msg.getText().toString();
                if(msg.length() > 0)
                {
                    Intent intent = new Intent(Decrypt.this, decPIN.class);
                    intent.putExtra("message", msg);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Decrypt.this,"Text Field Is Blank!", Toast.LENGTH_LONG).show();
                }
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
        },30, 30, TimeUnit.SECONDS);



    }

    /*ADMOB ADS*/

    public void prepareAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6575183577141875/5463862632");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}


