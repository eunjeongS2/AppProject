package kr.ac.ajou.jinaeunjeongbus.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    private BusSearchTabFragment busSearchTabFragment;
    private BusStopSearchTabFragment busStopSearchTabFragment;


    public TabPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        busSearchTabFragment = new BusSearchTabFragment();
        busStopSearchTabFragment = new BusStopSearchTabFragment();

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return busSearchTabFragment;
            case 1:
                return busStopSearchTabFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
