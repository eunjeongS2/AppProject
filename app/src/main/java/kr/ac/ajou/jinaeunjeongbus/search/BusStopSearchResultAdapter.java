package kr.ac.ajou.jinaeunjeongbus.search;

import android.view.ViewGroup;

import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractRecyclerAdapter;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;

class BusStopSearchResultAdapter extends AbstractRecyclerAdapter<BusStop> {

    private OnBusAlarmCheckListener onBusAlarmCheckListener;

    public void setOnBusAlarmCheckListener(OnBusAlarmCheckListener onBusAlarmCheckListener) {
        this.onBusAlarmCheckListener = onBusAlarmCheckListener;
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusStopSearchResultViewHolder(parent, onBusAlarmCheckListener);
    }
}
