package kr.ac.ajou.jinaeunjeongbus.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.ajou.jinaeunjeongbus.R;

public class BusStopSearchTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.bus_stop_search_tab_fragment, container, false);
    }


}
