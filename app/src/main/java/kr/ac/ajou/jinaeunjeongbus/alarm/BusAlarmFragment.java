package kr.ac.ajou.jinaeunjeongbus.alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusLocationFinder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;

public class BusAlarmFragment extends Fragment implements OnLocationLoadListener{

    private RecyclerView alarmListView;
    private DBHelper dbHelper;

    private AlarmRecyclerAdapter recyclerAdapter;

    private OnAlarmListener onAlarmListener;
//    private AlarmModel alarmModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_alarm_fragment, container, false);
        dbHelper = new DBHelper(getActivity());

        //dbHelper.clearList();
        alarmListView = view.findViewById(R.id.bus_alarm_list_view);

        setUpRecyclerView();

        onAlarmListener = alarm -> {
            recyclerAdapter.addItem(alarm);
            recyclerAdapter.notifyDataSetChanged();
            alarmOnOff(getContext(), alarm);
        };

//        alarmModel = new AlarmModel();
//        alarmModel.setOnAlarmListener(this);
//
//        alarmModel.fetchAlarm();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setBusLocation();
            }
        };
        timer.schedule(timerTask, 0, 1000);

        return view;
    }

    private void setBusLocation(){
        for(int i=0; i<recyclerAdapter.getItemCount(); i++){
            try {
                new BusLocationFinder(this, i, "200000112", "120000059").execute();
//                new BusLocationFinder(this, recyclerAdapter.getItem(i).getBusId(), recyclerAdapter.getItem(i).getDepartureStopId()).execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        alarmListView.setLayoutManager(manager);

        recyclerAdapter = new AlarmRecyclerAdapter(getContext(), position -> {
            Alarm alarm = recyclerAdapter.getItem(position);
            AddAlarmDialogFragment dialog = new AddAlarmDialogFragment();
            dialog.show(getActivity().getFragmentManager(), "a");
            String id = alarm.getArriveTime();
            dialog.setAlarm(alarm);
            dialog.setDialogResult(() -> {
                recyclerAdapter.setItems(dbHelper.findAll());
                recyclerAdapter.notifyDataSetChanged();
            });
        });

        alarmListView.setAdapter(recyclerAdapter);

        recyclerAdapter.setItems(dbHelper.findAll());
        recyclerAdapter.notifyDataSetChanged();

    }

    public void alarmOnOff(Context context, Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager == null)
            return;

        //       Intent alarmIntent = new Intent("android.intent.action.ALARM_START");
        Intent alarmIntent = new Intent(this.getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent;

        System.out.println(alarm.getArriveTime());

        if (!alarm.getOn()) {
            pendingIntent = PendingIntent.getBroadcast(context, alarm.getAlarmId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            System.out.println("cancel");
        } else {
            String hour = alarm.getArriveTime().substring(0, 2);
            String min = alarm.getArriveTime().substring(2, 4);

            System.out.println(hour);
            System.out.println(min);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(min));
            calendar.set(Calendar.SECOND, 0);

            int diff = (int) (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());

            // TERM = 알람 주기
            //alarmIntent.putExtra("TERM", 0);
            pendingIntent = PendingIntent.getBroadcast(context, alarm.getAlarmId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (diff >= 0) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (24 * 60 * 60 * 1000), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

        }

    }

//    @Override
//    public void onFetchAlarm(List<Alarm> alarmList) {
//
//        alarmList.clear();
//        alarmList.addAll(dbHelper.findAll());
//
//        recyclerAdapter.setItems(alarmList);
//        recyclerAdapter.notifyDataSetChanged();
//    }

    public OnAlarmListener getOnAlarmListener() {
        return onAlarmListener;
    }

    @Override
    public void onLocationLoadListener(int alarmPosition, String firstArrive, String secondArrive) {
        recyclerAdapter.getItem(alarmPosition).setFirstArrive(firstArrive);
        recyclerAdapter.getItem(alarmPosition).setSecondArrive(secondArrive);
        System.out.println(firstArrive +","+secondArrive);
        recyclerAdapter.notifyDataSetChanged();

    }
}
