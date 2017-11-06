package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.R;

public class BusAlarmFragment extends Fragment implements OnAlarmListener {

    private RecyclerView alarmListView;

    private AlarmRecyclerAdapter recyclerAdapter;
    private AlarmModel alarmModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_alarm_fragment, container, false);

        alarmListView = view.findViewById(R.id.bus_alarm_list_view);

        setUpRecyclerView();

        alarmModel = new AlarmModel();
        alarmModel.setOnAlarmListener(this);

        alarmModel.fetchAlarm();

        return view;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        alarmListView.setLayoutManager(manager);
        alarmListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        recyclerAdapter = new AlarmRecyclerAdapter();
        alarmListView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onFetchAlarm(List<Alarm> alarmList) {
        recyclerAdapter.setItems(alarmList);
        recyclerAdapter.notifyDataSetChanged();
    }
}
