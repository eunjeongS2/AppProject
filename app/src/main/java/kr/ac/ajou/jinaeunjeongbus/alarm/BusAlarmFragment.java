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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusLocationFinder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;

public class BusAlarmFragment extends Fragment implements OnLocationLoadListener{

    private RecyclerView alarmListView;
    private DBHelper dbHelper;

    private AlarmRecyclerAdapter recyclerAdapter;

    private OnAlarmListener onAlarmListener;
    private Timer timer;

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

        setBusLocation();

//        timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                setBusLocation();
//            }
//        };
//        timer.schedule(timerTask, 2000, 3000);

        return view;
    }

    private void setBusLocation(){
        for(int i=0; i<recyclerAdapter.getItemCount(); i++){
            try {
//                new BusLocationFinder(this, i, "200000112", "120000059").execute();
                new BusLocationFinder(this, i, recyclerAdapter.getItem(i).getBusId(), recyclerAdapter.getItem(i).getDepartureStopId()).execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        alarmListView.setLayoutManager(manager);

        recyclerAdapter = new AlarmRecyclerAdapter(getContext(), position -> {
//            System.out.println("cancel");
//            timer.cancel();

            Alarm alarm = recyclerAdapter.getItem(position);
            AddAlarmDialogFragment dialog = new AddAlarmDialogFragment();

            dialog.show(getActivity().getFragmentManager(), "a");
            dialog.setAlarm(alarm);
            dialog.setDialogResult(() -> {
                recyclerAdapter.setItems(dbHelper.findAll());
                recyclerAdapter.notifyDataSetChanged();
            });
        });
        recyclerAdapter = new AlarmRecyclerAdapter(getContext(), new OnAlarmClickListener() {
            @Override
            public void onClick(int position) {
                Alarm alarm = recyclerAdapter.getItem(position);
                AddAlarmDialogFragment dialog = new AddAlarmDialogFragment();
                dialog.show(getActivity().getFragmentManager(), "a");
                dialog.setAlarm(alarm);
                dialog.setDialogResult(() -> {
                    recyclerAdapter.setItems(dbHelper.findAll());
                    recyclerAdapter.notifyDataSetChanged();
                });
            }
        });

        alarmListView.setAdapter(recyclerAdapter);

        List<Alarm> alarms = dbHelper.findAll();
        recyclerAdapter.setItems(alarms);
        recyclerAdapter.notifyDataSetChanged();

        recyclerAdapter.setOnAlarmCheckedChangeListener((position, isChecked) -> {
            Toast.makeText(getContext(),"Alarm Update "+position+isChecked,Toast.LENGTH_SHORT).show();
            System.out.println(position);
            Alarm curAlarm = alarms.get(position);
            //1:true 0:false
            curAlarm.setOn(isChecked ? 1 : 0);
            System.out.println(curAlarm.getOn());
            dbHelper.updateAlarm(curAlarm.getArriveTime(),curAlarm);
            alarmOnOff(getContext(),curAlarm);
        });
    }

    public void alarmOnOff(Context context, Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager == null)
            return;

        Intent alarmIntent = new Intent(this.getActivity(),AlarmReceiver.class);
        PendingIntent pendingIntent;

        System.out.println(alarm.getArriveTime());

        if (alarm.getOn() == 0) {
            pendingIntent = PendingIntent.getBroadcast(context, (int) Long.parseLong(alarm.getAlarmId()), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            System.out.println("cancel");
        } else {
            String hour = alarm.getArriveTime().substring(0, 2);
            String min = alarm.getArriveTime().substring(2, 4);

            int requiredTime = Integer.parseInt(alarm.getBusRequiredTime().split(" ")[0])
                    + Integer.parseInt(alarm.getDepartureRequiredTime().split(" ")[0])
                    + Integer.parseInt(alarm.getDestinationRequiredTime().split(" ")[0]);
            int curHour = Integer.parseInt(hour) - requiredTime/60;
            int curMin = Integer.parseInt(min) - requiredTime%60;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, curHour);
            calendar.set(Calendar.MINUTE, curMin - Integer.parseInt(alarm.getAlarmTerm()));
            calendar.set(Calendar.SECOND, 0);

            alarmIntent.putExtra("BusInfo", alarm.getFirstArrive());

            int diff = (int) (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());
            pendingIntent = PendingIntent.getBroadcast(context, (int) Long.parseLong(alarm.getAlarmId()), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (diff >= 0) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (24 * 60 * 60 * 1000), pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() + (24 * 60 * 60 * 1000), Integer.parseInt(alarm.getAlarmTerm()),pendingIntent);

            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), Integer.parseInt(alarm.getAlarmTerm()),pendingIntent);
            }

        }

    }

    public OnAlarmListener getOnAlarmListener() {
        return onAlarmListener;
    }

    @Override
    public void onLocationLoadListener(int alarmPosition, String firstArrive, String secondArrive) {
        recyclerAdapter.getItem(alarmPosition).setFirstArrive(firstArrive);
        recyclerAdapter.getItem(alarmPosition).setSecondArrive(secondArrive);
//        System.out.println("timer");
        recyclerAdapter.notifyDataSetChanged();

    }
}
