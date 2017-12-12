package kr.ac.ajou.jinaeunjeongbus.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusStopFinder;

public class BusStopSearchTabFragment extends Fragment implements OnBusStopLoadListener, OnBusAlarmCheckListener{

    private static final String TAG = "StopSearchTabFragment";

    private RecyclerView busStopSearchListView;

    private TextView emptySearchResultText;
    //    private SearchModel searchModel;
    private BusStopSearchResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bus_stop_search_tab_fragment, container, false);
        busStopSearchListView = view.findViewById(R.id.search_bus_stop_result_list_view);
        emptySearchResultText = view.findViewById(R.id.text_no_search_bus_stop_result);

        emptySearchResultText.setVisibility(View.GONE);

        setUpSearchListView();

//        searchModel.fetchBusList();


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    public void setSearchModel(SearchModel searchModel) {
//        this.searchModel = searchModel;
//    }

    private void setUpSearchListView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        busStopSearchListView.setLayoutManager(manager);

        adapter = new BusStopSearchResultAdapter();
        adapter.setOnBusAlarmCheckListener(this);

        busStopSearchListView.setAdapter(adapter);
    }

    public void query(String message) {

        try {
            new BusStopFinder(this, message).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        searchModel.query(message);
        emptySearchResultText.setVisibility(View.GONE);


    }

    @Override
    public void onLoad(List<BusStop> busStopList) {
        if(busStopList.size() == 0) {
            emptySearchResultText.setVisibility(View.VISIBLE);
            return;
        }

        adapter.setItems(busStopList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchComplete(List<BusStop> searchResult) {
        Log.d(TAG, "onSearchComplete: " + searchResult.size());

        if(searchResult.size() == 0) {
            emptySearchResultText.setVisibility(View.VISIBLE);
            return;
        }

        adapter.setItems(searchResult);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBusAlarmChecked(Bus bus, int position) {

    }

    @Override
    public void onBusStopAlarmChecked(BusStop busStop, int position) {

    }
}
