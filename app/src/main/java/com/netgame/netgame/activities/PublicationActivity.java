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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.netgame.netgame.R;
import com.netgame.netgame.adapters.ViewPagerAdapter;
import com.netgame.netgame.fragments.EventsFragment;
import com.netgame.netgame.fragments.PublicationsFragment;
import com.netgame.netgame.models.Game;

import java.util.ArrayList;
import java.util.List;

public class PublicationActivity extends AppCompatActivity implements View.OnClickListener {

    private Game game;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private FloatingActionButton actionFloatingActionButton;

    // OnFloatingActionButtonClickListener onFloatingActionButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publication);

        actionFloatingActionButton = findViewById(R.id.actionFloatingActionButton);
        actionFloatingActionButton.setOnClickListener(this);


        game = Game.from(getIntent().getExtras());

        addToolbar();

        setupViewPager();
        setupTabLayout();

    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), buildFragments());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        actionFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_rate_review_black_24dp));
                        break;
                    case 1:
                        actionFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_event_note_black_24dp));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).setText("Publications");
        tabLayout.getTabAt(1).setText("Events");
    }

    private List<Fragment> buildFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PublicationsFragment().newInstance(game));
        fragmentList.add(new EventsFragment().newInstance(game));
        return fragmentList;
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(game.getName());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public interface OnFloatingActionButtonClickListener{
        void OnFloatingActionButtonClickListener(TabLayout tabLayout, View view);
    }

    public void SetOnFloatingActionButtonClickListener(OnFloatingActionButtonClickListener onFloatingActionButtonClickListener){
        this.onFloatingActionButtonClickListener = onFloatingActionButtonClickListener;
    }*/

    @Override
    public void onClick(View view) {
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                Intent intentPublication = new Intent(getApplicationContext(), CreatePublicationActivity.class);
                intentPublication.putExtras(game.toBundle());
                startActivityForResult(intentPublication, 0);
                break;
            case 1:
                Intent intentEvent = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivityForResult(intentEvent, 0);
                break;
        }
        /* if (onFloatingActionButtonClickListener != null)
            onFloatingActionButtonClickListener.OnFloatingActionButtonClickListener(tabLayout, view);*/
    }
}
