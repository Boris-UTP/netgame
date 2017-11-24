package com.netgame.netgame.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.netgame.netgame.R;
import com.netgame.netgame.fragments.HomeFragment;
import com.netgame.netgame.fragments.MapFragment;
import com.netgame.netgame.fragments.SettingsFragment;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    private BottomNavigationView navigationBottomNavigationView;
    private static final int PERMISSIONS_ACCESS_LOCATION_TASK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addHomeToolbar();

        navigationBottomNavigationView = findViewById(R.id.navigation);
        navigationBottomNavigationView.setOnNavigationItemSelectedListener(this);

        navigateAccordingTo(R.id.navigation_home);

        validatePermissionLocation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private Fragment getFragmentFor(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.navigation_home:
                setTitle("Juegos");
                fragment = new HomeFragment();
                break;
            case R.id.navigation_map:
                setTitle("Mapa");
                fragment = new MapFragment();
                break;
            case R.id.navigation_setting:
                setTitle("Configuración");
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

    private boolean validatePermissionLocation() {
        boolean status = true;
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content), "Solicitar permisos de Localización",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_ACCESS_LOCATION_TASK);
                            }
                        })
                        .show();
                status = false;
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_ACCESS_LOCATION_TASK);
                status = false;
            }
        }
        return status;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_ACCESS_LOCATION_TASK:
                break;
        }
    }

}
