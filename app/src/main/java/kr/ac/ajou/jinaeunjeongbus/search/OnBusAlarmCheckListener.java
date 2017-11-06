package kr.ac.ajou.jinaeunjeongbus.search;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;

/**
 * Created by ijina on 2017. 11. 6..
 */

public interface OnBusAlarmCheckListener {
    void onBusAlarmChecked(Bus bus, int position);
}
