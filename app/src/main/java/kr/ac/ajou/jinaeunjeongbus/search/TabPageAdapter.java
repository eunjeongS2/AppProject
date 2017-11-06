package kr.ac.ajou.jinaeunjeongbus.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BusSearchTabFragment busSearchTabFragment = new BusSearchTabFragment();
                busSearchTabFragment.setSearchModel(new SearchModel(busSearchTabFragment));
                return busSearchTabFragment;
            case 1:
                BusStopSearchTabFragment busStopSearchTabFragment = new BusStopSearchTabFragment();
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
