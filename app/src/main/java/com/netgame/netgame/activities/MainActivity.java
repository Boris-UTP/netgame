package com.netgame.netgame.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.netgame.netgame.R;
import com.netgame.netgame.fragments.HomeFragment;
import com.netgame.netgame.fragments.MapFragment;
import com.netgame.netgame.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    private BottomNavigationView navigationBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar();

        navigationBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationBottomNavigationView.setOnNavigationItemSelectedListener(this);

        navigateAccordingTo(R.id.navigation_home);
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private Fragment getFragmentFor(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_map:
                fragment = new MapFragment();
                break;
            case R.id.navigation_setting:
                fragment = new SettingsFragment();
                break;
        }
        return fragment;
    }

    private boolean navigateAccordingTo(int id) {
        try{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, getFragmentFor(id))
                    .commit();

            return true;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return navigateAccordingTo(item.getItemId());
    }

}
