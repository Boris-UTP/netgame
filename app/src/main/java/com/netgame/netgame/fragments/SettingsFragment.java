package com.netgame.netgame.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.netgame.netgame.R;
import com.netgame.netgame.activities.EditGamerUserActivity;
import com.netgame.netgame.activities.EditCabinUserActivity;
import com.netgame.netgame.activities.LoginActivity;
import com.netgame.netgame.commons.PreferencesEditor;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Button logOutButton;
    private Button editButton;
    private Switch optionSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View context = inflater.inflate(R.layout.fragment_settings, container, false);
        logOutButton = context.findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                    }
                });
        editButton = context.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Boolean isGamer = PreferencesEditor.getBooleanPreference(getContext(),"typeUser",false) ;
                        Intent intent = null;
                        if(isGamer){
                        intent = new Intent(getActivity(), EditGamerUserActivity.class);
                        }else {
                         intent = new Intent(getActivity(), EditCabinUserActivity.class);
                        }
                        startActivity(intent);
                    }
                });

        optionSwitch = context.findViewById(R.id.optionSwitch);
        optionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getActivity(),String.valueOf(b), Toast.LENGTH_SHORT).show();
                PreferencesEditor.savePreference(getActivity(),"typeUser",b);
            }
        });

        return context;
    }
}
