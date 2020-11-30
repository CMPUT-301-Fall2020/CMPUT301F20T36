package com.example.book_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.book_master.adapter.*;
import com.example.book_master.fragment.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity is for controlling my request button page.
 * It is able to view requests the user sent and received.
 */
public class request_navigator extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // All bottom navigator share the same xml, so it is necessary to change menu items' titles
        // and icons according to specific activities
        setContentView(R.layout.search_bottom_nav);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.getMenu().getItem(0).setTitle("Sender");
        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.icon_send);
        bottomNavigationView.getMenu().getItem(1).setTitle("Receiver");
        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.icon_receive);
        // The view pager is to display the fragment view
        viewPager = findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        // Add two fragments for sent requests and received requests
        List<Fragment> list = new ArrayList<>();
        list.add(request_menu.newInstance("SENT"));
        list.add(request_menu.newInstance("RECEIVED"));

        viewPagerAdapter.setList(list);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        // setting up the navigator listener
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_book:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };
}
