package kr.ac.ajou.jinaeunjeongbus.search;

import android.view.ViewGroup;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractRecyclerAdapter;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;


public class BusSearchResultAdapter extends AbstractRecyclerAdapter<Bus> {

    private OnBusAlarmCheckListener onBusAlarmCheckListener;

    public void setOnBusAlarmCheckListener(OnBusAlarmCheckListener onBusAlarmCheckListener) {
        this.onBusAlarmCheckListener = onBusAlarmCheckListener;
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusSearchResultViewHolder(parent, onBusAlarmCheckListener);
    }
}
