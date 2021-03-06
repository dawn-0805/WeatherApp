package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.fragment.FragmentListener;
import com.example.weather.fragment.detail.DetailFragment;
import com.example.weather.fragment.settings.SettingsFragment;
import com.example.weather.fragment.today.TodayFragment;
import com.example.weather.fragment.weekly.WeeklyFragment;
import com.example.weather.util.Constants;
import com.example.weather.util.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    private ViewPager2 mViewPager2;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.nav_menu);
        mViewPager2 = findViewById(R.id.view_pager_2);
        navigationView = findViewById(R.id.navView);


        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager2.setAdapter(myViewPagerAdapter);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings:

                        SettingsFragment fragment = new SettingsFragment();
                        fragment.show(getSupportFragmentManager(),"dialog");
                        System.out.println("Settings is clicked");
                        return true;
                    case R.id.about:
                        System.out.println("About is clicked");
                        return true;
                }
                return true;
            }
        });



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.navigation_today){
                   mViewPager2.setCurrentItem(0);
                }else if (id==R.id.navigation_weekly){
                    mViewPager2.setCurrentItem(1);
                }else if (id==R.id.navigation_detail){
                    mViewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_today).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_weekly).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_detail).setChecked(true);
                        break;
                }
            }
        });

    }




    @Override
    public void applyTexts(String cityName, String numberDays) {
        TodayFragment todayFragment = new TodayFragment();
        todayFragment.receiveDataFromSettings(cityName,numberDays);

        WeeklyFragment weeklyFragment = new WeeklyFragment();
        weeklyFragment.receiveDataFromSettings(cityName,numberDays);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.receiveDataFromSettings(cityName,numberDays);

    }


}