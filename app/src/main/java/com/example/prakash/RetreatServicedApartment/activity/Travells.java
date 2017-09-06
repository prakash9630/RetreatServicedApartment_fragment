package com.example.prakash.RetreatServicedApartment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.prakash.RetreatServicedApartment.Fragment_adapter;
import com.example.prakash.RetreatServicedApartment.R;

/**
 * Created by prakash on 4/3/2017.
 */

public class Travells extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Fragment_adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.travells_layout);

        toolbar=(Toolbar)findViewById(R.id.travel_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(3);

        tabLayout=(TabLayout)findViewById(R.id.tab_id);

        adapter = new Fragment_adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Landmark"));
        tabLayout.addTab(tabLayout.newTab().setText("Restaurants"));
//        tabLayout.addTab(tabLayout.newTab().setText("Recreational Sites"));


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

    }




}
