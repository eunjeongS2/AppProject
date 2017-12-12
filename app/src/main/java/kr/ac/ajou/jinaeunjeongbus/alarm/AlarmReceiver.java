package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("RECEIVE");

        int term = 0;

//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//            term = bundle.getInt("TERM");

        Intent alarmIntent = new Intent(context, AlarmService.class);
        context.startService(alarmIntent);
    }
}
