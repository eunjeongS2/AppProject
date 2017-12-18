package kr.ac.ajou.jinaeunjeongbus;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kr.ac.ajou.jinaeunjeongbus.search.BusSearchTabFragment;
import kr.ac.ajou.jinaeunjeongbus.search.BusStopSearchTabFragment;
import kr.ac.ajou.jinaeunjeongbus.search.TabPageAdapter;


public class SearchActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchQueryEditText;
    private ImageView backButton;

    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment);

        tabLayout = findViewById(R.id.search_tab);
        tabLayout.addTab(tabLayout.newTab().setText("버스"));
        tabLayout.addTab(tabLayout.newTab().setText("정류장"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.tab_page);

        rootView = findViewById(R.id.search_root);

        backButton = findViewById(R.id.search_back_button);
        backButton.setOnClickListener(v -> finish());

        searchQueryEditText = findViewById(R.id.search_query);
        searchQueryEditText.setHint(R.string.hint_bus);

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        searchQueryEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_SEARCH:
                    String message = searchQueryEditText.getText().toString();
                    if (!message.equals("")) {
                        if (tabLayout.getSelectedTabPosition() == 0) {
                            BusSearchTabFragment fragment = (BusSearchTabFragment) pageAdapter.getItem(tabLayout.getSelectedTabPosition());
                            fragment.query(message);
                        } else if (tabLayout.getSelectedTabPosition() == 1) {
                            BusStopSearchTabFragment fragment = (BusStopSearchTabFragment) pageAdapter.getItem(tabLayout.getSelectedTabPosition());
                            fragment.query(message);
                        }
                    } else {
                        Snackbar.make(rootView, getString(R.string.no_search_query), Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                default:

                    return false;
            }
            return true;
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    searchQueryEditText.setHint(R.string.hint_bus);
                } else if (tab.getPosition() == 1) {
                    searchQueryEditText.setHint(R.string.hint_bus_stop);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
