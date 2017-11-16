package com.netgame.netgame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.adapters.EventsAdapter;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Event;
import com.netgame.netgame.models.Game;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.EVENTS_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private RecyclerView.LayoutManager eventsLayoutManager;
    private List<Event> events;

    private SwipeRefreshLayout refreshSwipeRefreshLayout;
    private Game game;
    private Gson gson;

    private String tag;


    public static EventsFragment newInstance(Game game) {

        Bundle args = game.toBundle();

        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View context = inflater.inflate(R.layout.fragment_events, container, false);

        game = Game.from(getArguments());
        events = new ArrayList<>();
        gson = new Gson();
        tag = getResources().getString(R.string.app_name);
        eventsRecyclerView = context.findViewById(R.id.eventsRecyclerView);
        refreshSwipeRefreshLayout = context.findViewById(R.id.refreshSwipeRefreshLayout);

        eventsAdapter = new EventsAdapter(events);
        eventsLayoutManager = new LinearLayoutManager(getActivity());

        eventsRecyclerView.setAdapter(eventsAdapter);
        eventsRecyclerView.setLayoutManager(eventsLayoutManager);

        refreshSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshSwipeRefreshLayout.setOnRefreshListener(this);

        refreshSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                // getEvents();
            }
        });

        return context;
    }

    @Override
    public void onRefresh() {
        getEvents();
    }

    private void getEvents() {
        refreshSwipeRefreshLayout.setRefreshing(true);
        AndroidNetworking
                .get(String.format(EVENTS_URL, game.getId()))
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<List<Event>> events = gson.fromJson(response.toString(), new TypeToken<Base<List<Event>>>() {
                        }.getType());

                        if (events.getStatusBody().getCode().equalsIgnoreCase("0")) {
                            eventsAdapter.setEvents(events.getData());
                            eventsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), events.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        refreshSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                    }
                });
    }

}
