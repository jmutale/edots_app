package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class ClientDOTCardActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_dotcard_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager =  findViewById(R.id.viewPager);
        FragmentManager fm = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fm,getLifecycle());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        //tabLayout.setupWithViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        // Create a new Tab named "Part A"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Part A"); // set the Text for the first Tab
        // first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
        // Create a new Tab named "Part B"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Part B"); // set the Text for the second Tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
         // perform addOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getClientUuid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("client_uuid");
    }
}