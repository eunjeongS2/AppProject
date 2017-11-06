package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.view.ViewGroup;

import kr.ac.ajou.jinaeunjeongbus.base.AbstractRecyclerAdapter;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;


public class AlarmRecyclerAdapter extends AbstractRecyclerAdapter<Alarm> {

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlarmViewHolder(parent);
    }
}
