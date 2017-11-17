package com.netgame.netgame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netgame.netgame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationCabinFragment extends Fragment {


    public LocationCabinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_cabin, container, false);
    }

}
