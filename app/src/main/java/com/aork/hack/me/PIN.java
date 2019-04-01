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

public class PIN extends AppCompatActivity {
    Button encBut;
    EditText enc_pin_tf;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        encBut = (Button)findViewById(R.id.button3);
        enc_pin_tf = (EditText)findViewById(R.id.editText2);

        Intent intent = getIntent();
        final String msg = intent.getStringExtra("message");

        encBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enc_msg = "";
                int pin_int;
                int i;
                String pin_str = enc_pin_tf.getText().toString();
                if(pin_str.length() == 0)
                {
                    Toast.makeText(PIN.this,"You must enter a 4 digit PIN", Toast.LENGTH_LONG).show();
                }
                else if (pin_str.length() != 4)
                {
                    Toast.makeText(PIN.this,"PIN must be number & exactly 4 digit long", Toast.LENGTH_LONG).show();
                }
                else if(pin_str.length() == 4)
                {
                    pin_int = (int) pin_str.charAt(2);
                    //Toast.makeText(PIN.this, pin_int, Toast.LENGTH_LONG).show();
                    int msg_ascii;
                    for (i=0; i<msg.length(); i++) {
                        msg_ascii = (int) msg.charAt(i);
                        msg_ascii += pin_int;
                        StringBuilder rev_result = new StringBuilder();
                        int j = 0;
                        while (msg_ascii > 0) {
                            rev_result.append(msg_ascii % 2);
                            j++;
                            msg_ascii = msg_ascii / 2;
                        }
                        String result = rev_result.reverse().toString();
                        enc_msg = enc_msg + result + " ";
                    }
                    Intent intent = new Intent(PIN.this, EncryptedMessage.class);
                    intent.putExtra("encrypted_message", enc_msg);
                    startActivity(intent);
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
        },25, 25, TimeUnit.SECONDS);



    }

    /*ADMOB ADS*/

    public void prepareAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6575183577141875/5463862632");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
