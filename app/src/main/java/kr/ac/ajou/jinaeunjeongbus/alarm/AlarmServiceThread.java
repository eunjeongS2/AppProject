package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.os.Handler;

public class AlarmServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public AlarmServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        handler.sendEmptyMessage(0);
//        while(isRun){
//            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
//            try{
//                Thread.sleep(10000); //10초씩 쉰다.
//            }catch (Exception e) {}
//        }
    }


}
