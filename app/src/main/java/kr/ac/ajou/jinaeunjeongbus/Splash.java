package kr.ac.ajou.jinaeunjeongbus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by ijina on 2017. 11. 6..
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);

    }


}
