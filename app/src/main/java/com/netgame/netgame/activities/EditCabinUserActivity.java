package com.netgame.netgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.adapters.ViewPagerAdapter;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.fragments.EditCabinUserFragment;
import com.netgame.netgame.fragments.EventsFragment;
import com.netgame.netgame.fragments.LocationCabinFragment;
import com.netgame.netgame.fragments.PublicationsFragment;
import com.netgame.netgame.fragments.SettingsFragment;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.UserCabin;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.CABIN_URL;

public class EditCabinUserActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private FloatingActionButton actionFloatingActionButton;

    private EditCabinUserFragment editCabinUserFragment;
    private LocationCabinFragment locationCabinFragment;
    Gson gson;

    private UserCabin userCabin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addBackToolbar();

        gson = new Gson();

        actionFloatingActionButton = findViewById(R.id.actionFloatingActionButton);
        actionFloatingActionButton.setOnClickListener(this);

        getUserCabin();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_cabin_user;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, new Intent(getApplicationContext(), SettingsFragment.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), buildFragments());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Infomacion");
        tabLayout.getTabAt(1).setText("Ubicacion");
    }

    private List<Fragment> buildFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        editCabinUserFragment = new EditCabinUserFragment().newInstance(userCabin);
        locationCabinFragment = new LocationCabinFragment().newInstance(userCabin);
        fragmentList.add(editCabinUserFragment);
        fragmentList.add(locationCabinFragment);
        return fragmentList;
    }

    @Override
    public void onClick(View view) {
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                editCabinUserFragment.putInfoCabin();
                break;
            case 1:
                locationCabinFragment.putLocationCabin();
                break;
        }
    }

    private void getUserCabin() {
        showProgressDialog();
        AndroidNetworking
                .get(CABIN_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<UserCabin> objUserCabin = gson.fromJson(response.toString(), new TypeToken<Base<UserCabin>>() {
                        }.getType());

                        if (objUserCabin.getStatusBody().getCode().equals("0")) {
                            userCabin = objUserCabin.getData();
                            setupViewPager();
                            setupTabLayout();
                        } else {
                            Log.d(tag, objUserCabin.getStatusBody().getMessage());
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                        dismissProgressDialog();
                    }
                });
    }

}
