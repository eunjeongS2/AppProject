package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, AlarmService.class);
        Bundle bundle = intent.getExtras();
        if (bundle != null)
            alarmIntent.putExtra("busInfo", bundle.getInt("BusInfo"));
        context.startService(alarmIntent);
    }
}
