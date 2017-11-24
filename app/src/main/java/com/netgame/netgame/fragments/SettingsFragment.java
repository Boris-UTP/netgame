package com.netgame.netgame.fragments;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.netgame.netgame.R;
import com.netgame.netgame.activities.EditGamerUserActivity;
import com.netgame.netgame.activities.EditCabinUserActivity;
import com.netgame.netgame.activities.LoginActivity;
import com.netgame.netgame.adapters.SettingsAdapter;
import com.netgame.netgame.commons.PreferencesEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private RecyclerView settingsRecyclerView;
    private SettingsAdapter settingsAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View context = inflater.inflate(R.layout.fragment_settings, container, false);

        List<String> settings = new ArrayList<>();
        settings.add("Editar usuario");
        settings.add("Salir");

        settingsRecyclerView = context.findViewById(R.id.settingsRecyclerView);
        settingsAdapter = new SettingsAdapter(settings);
        layoutManager = new LinearLayoutManager(getActivity());

        settingsRecyclerView.setAdapter(settingsAdapter);
        settingsRecyclerView.setLayoutManager(layoutManager);
        settingsRecyclerView.addItemDecoration(getItemDecoration());

        return context;
    }

    private DividerItemDecoration getItemDecoration() {
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int position = parent.getChildAdapterPosition(view);
                        // hide the divider for the last child
                        if (position == parent.getAdapter().getItemCount() - 1) {
                            outRect.setEmpty();
                        } else {
                            super.getItemOffsets(outRect, view, parent, state);
                        }
                    }
                };

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_division));
        return itemDecoration;
    }
}
