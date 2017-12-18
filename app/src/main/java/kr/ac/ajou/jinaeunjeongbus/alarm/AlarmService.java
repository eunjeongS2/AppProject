package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import kr.ac.ajou.jinaeunjeongbus.MainActivity;
import kr.ac.ajou.jinaeunjeongbus.R;


public class AlarmService extends Service {

    public AlarmService() {

    }

    NotificationManager notificationManager;
    Notification notification;
    AlarmServiceThread thread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler(intent.getStringExtra("busInfo"));
        thread = new AlarmServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }


    class myServiceHandler extends Handler {
        private String contents;

        public myServiceHandler(String contents){
            this.contents = contents;
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(AlarmService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("알람")
                    .setContentText(contents)
                    .setSmallIcon(R.drawable.bus_alarm_check_button)
                    .setContentIntent(pendingIntent)
                    .build();

            notification.defaults = Notification.DEFAULT_SOUND;
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            notification.flags = Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(777, notification);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
