package day.myfulishe.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import day.myfulishe.R;

public class SplashActivity extends AppCompatActivity {
    final long splashtime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        final long start = System.currentTimeMillis();
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                if (end - start < splashtime) {
                    try {
                        Thread.sleep(splashtime);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        finish();

    }
}
