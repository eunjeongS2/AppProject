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


import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;


public class BusSearchTabFragment extends Fragment implements OnBusAlarmCheckListener, OnBusLoadListener {

    private static final String TAG = "BusSearchTabFragment";

    private RecyclerView busSearchListView;

    private TextView emptySearchResultText;
    private SearchModel searchModel;
    private BusSearchResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bus_search_tab_fragment, container, false);

        busSearchListView = view.findViewById(R.id.search_bus_result_list_view);
        emptySearchResultText = view.findViewById(R.id.text_no_search_result);

        emptySearchResultText.setVisibility(View.GONE);

        setUpSearchListView();

        searchModel.fetchBusList();

        Log.d(TAG, "onCreateView: ");

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    private void setUpSearchListView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        busSearchListView.setLayoutManager(manager);

        adapter = new BusSearchResultAdapter();
        adapter.setOnBusAlarmCheckListener(this);

        busSearchListView.setAdapter(adapter);
    }

    public void query(String message) {
        searchModel.query(message);
    }


    @Override
    public void onBusAlarmChecked(Bus bus, int position) {

    }

    @Override
    public void onLoad(List<Bus> busList) {
        if(busList.size() == 0) {
            emptySearchResultText.setVisibility(View.VISIBLE);
            return;
        }

        adapter.setItems(busList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchComplete(List<Bus> searchResult) {
        Log.d(TAG, "onSearchComplete: " + searchResult.size());
        if(searchResult.size() == 0) {
            emptySearchResultText.setVisibility(View.VISIBLE);
            return;
        }

        adapter.setItems(searchResult);
        adapter.notifyDataSetChanged();
    }
}
