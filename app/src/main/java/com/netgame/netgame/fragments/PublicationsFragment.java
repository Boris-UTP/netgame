package com.netgame.netgame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.netgame.netgame.adapters.PublicationsAdapter;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Game;
import com.netgame.netgame.models.Publication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.PUBLICATIONS_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicationsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView publicationsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PublicationsAdapter publicationsAdapter;

    private SwipeRefreshLayout refreshSwipeRefreshLayout;

    private List<Publication> publications;

    private Game game;
    private String tag;
    private Gson gson;

    public static PublicationsFragment newInstance(Game game){
        Bundle args = game.toBundle();
        PublicationsFragment fragment = new PublicationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View context = inflater.inflate(R.layout.fragment_publications, container, false);

        gson = new Gson();
        game = Game.from(getArguments());
        tag = getResources().getString(R.string.app_name);
        publications = new ArrayList<>();

        publicationsRecyclerView = context.findViewById(R.id.publicationsRecyclerView);
        publicationsAdapter = new PublicationsAdapter(publications);
        layoutManager = new LinearLayoutManager(getActivity());
        publicationsRecyclerView.setAdapter(publicationsAdapter);
        publicationsRecyclerView.setLayoutManager(layoutManager);

        refreshSwipeRefreshLayout = context.findViewById(R.id.refreshSwipeRefreshLayout);
        refreshSwipeRefreshLayout.setOnRefreshListener(this);
        refreshSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        refreshSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getPublications();
            }
        });

        return context;
    }

    public void getPublications(){
        refreshSwipeRefreshLayout.setRefreshing(true);
        AndroidNetworking
                .get(String.format(PUBLICATIONS_URL, String.valueOf(game.getId())))
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<List<Publication>> publications = gson.fromJson(response.toString(), new TypeToken<Base<List<Publication>>>() {
                        }.getType());

                        if (publications.getStatusBody().getCode().equalsIgnoreCase("0")){
                            publicationsAdapter.setPublications(publications.getData());
                            publicationsAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getActivity(), publications.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        refreshSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getMessage(), Toast.LENGTH_SHORT).show();
                        refreshSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        getPublications();
    }
}
