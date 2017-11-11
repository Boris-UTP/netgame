package com.netgame.netgame.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.netgame.netgame.adapters.GamesAdapter;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Game;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.GAMES_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView gamesRecyclerView;
    private GamesAdapter gamesAdapter;
    private RecyclerView.LayoutManager gameLayoutManager;
    private List<Game> games;

    private String tag;
    private Gson gson;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View context = inflater.inflate(R.layout.fragment_home, container, false);

        tag = getResources().getString(R.string.app_name);

        games = new ArrayList<>();
        gson = new Gson();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        gamesRecyclerView = context.findViewById(R.id.gamesRecyclerView);
        gamesAdapter = new GamesAdapter(games);
        gameLayoutManager = new LinearLayoutManager(getActivity());

        gamesRecyclerView.setLayoutManager(gameLayoutManager);
        gamesRecyclerView.setAdapter(gamesAdapter);

        getGames();

        return context;
    }

    private void getGames() {
        AndroidNetworking
                .get(GAMES_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<List<Game>> games = gson.fromJson(response.toString(), new TypeToken<Base<List<Game>>>() {
                        }.getType());
                        progressDialog.dismiss();
                        if (games.getStatusBody().getCode().equalsIgnoreCase("0")) {
                            gamesAdapter.setGames(games.getData());
                            gamesAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), games.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
