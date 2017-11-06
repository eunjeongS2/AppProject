package kr.ac.ajou.jinaeunjeongbus.alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmModel {
    // API 쏴서 BusAlarmFragment 에 Listener 로 알려준다
    // 알려주면 AlarmAdapter 에 setItems 하고, adapter.notifyDataChanged 호출

    private static List<Alarm> alarmList = new ArrayList<>();

    private OnAlarmListener onAlarmListener;

    public void setOnAlarmListener(OnAlarmListener onAlarmListener) {
        this.onAlarmListener = onAlarmListener;
    }

    public AlarmModel() {

    }

    static {
        List<Bus> busList = new ArrayList<>();
        busList.add(new Bus("3"));
        busList.add(new Bus("2-1"));
        busList.add(new Bus("62-1"));
        busList.add(new Bus("99"));
        busList.add(new Bus("92-1"));
        busList.add(new Bus("720-1"));
        alarmList.add(new Alarm("아주대학교", "영통 sk뷰아파트 1101동 1004호", "센트럴하이츠아파트 정류장",
            "11:00", "11:40", busList));

        List<Bus> busList2 = new ArrayList<>();
        busList2.add(new Bus("3"));
        busList2.add(new Bus("2-1"));
        busList2.add(new Bus("62-1"));
        busList2.add(new Bus("99"));
        busList2.add(new Bus("92-1"));
        busList2.add(new Bus("720-1"));
        alarmList.add(new Alarm("아주대학교", "영통 sk뷰아파트 1101동 1004호", "센트럴하이츠아파트 정류장",
            "12:30", "13:10", busList2));

        List<Bus> busList3 = new ArrayList<>();
        busList3.add(new Bus("720-2"));
        busList3.add(new Bus("730"));
        busList3.add(new Bus("13-4"));
        busList3.add(new Bus("9-2"));
        alarmList.add(new Alarm("수원역", "아주대학교 산학원", "아주대, 아주대병원 입구, 한국자산관리공사",
            "18:00", "18:30", busList3));

        List<Bus> busList4 = new ArrayList<>();
        busList4.add(new Bus("10-5"));
        busList4.add(new Bus("37"));
        alarmList.add(new Alarm("한국민속촌", "아주대학교 팔달관", "아주대입구",
            "20:00", "20:30", busList4));
    }

    public void fetchAlarm() {

        // 완료 시
        if(onAlarmListener != null) {
            onAlarmListener.onFetchAlarm(alarmList);
        }
    }
}
