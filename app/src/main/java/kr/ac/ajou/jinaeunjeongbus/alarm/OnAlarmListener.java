package kr.ac.ajou.jinaeunjeongbus.alarm;

import java.util.List;

public interface OnAlarmListener {
    void onFetchAlarm(List<Alarm> alarmList);
}
