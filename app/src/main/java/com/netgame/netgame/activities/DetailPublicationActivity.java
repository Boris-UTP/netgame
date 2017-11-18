package com.netgame.netgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.adapters.CommentsAdapter;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Comment;
import com.netgame.netgame.models.Publication;
import com.netgame.netgame.models.PublicationComments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.COMMENTS_URL;

public class DetailPublicationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView titleTextView;
    private TextView descriptionTextView;

    private List<Comment> comments;
    private RecyclerView commentsRecyclerView;
    private RecyclerView.LayoutManager commentsLayoutManager;
    private CommentsAdapter commentsAdapter;

    private FloatingActionButton commentFloatingActionButton;

    private SwipeRefreshLayout commentsSwipeRefreshLayout;

    private Publication publication;
    private String tag;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_publication);

        addToolbar();

        publication = Publication.from(getIntent().getExtras());
        tag = getResources().getString(R.string.app_name);
        gson = new Gson();

        commentFloatingActionButton = findViewById(R.id.commentFloatingActionButton);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        titleTextView.setText(publication.getTitle());
        descriptionTextView.setText(publication.getDescription());

        comments = new ArrayList<>();

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsLayoutManager = new LinearLayoutManager(this);
        commentsAdapter = new CommentsAdapter(comments);
        commentsRecyclerView.setAdapter(commentsAdapter);
        commentsRecyclerView.setLayoutManager(commentsLayoutManager);

        commentsSwipeRefreshLayout = findViewById(R.id.commentsSwipeRefreshLayout);
        commentsSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        commentsSwipeRefreshLayout.setOnRefreshListener(this);

        commentsSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getComments();
            }
        });
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                setResult(RESULT_OK, new Intent(getApplicationContext(), PublicationActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getComments(){
        commentsSwipeRefreshLayout.setRefreshing(true);
        AndroidNetworking
                .get(String.format(COMMENTS_URL, String.valueOf(publication.getId())))
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .setPriority(Priority.LOW)
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<PublicationComments> responseObject = gson.fromJson(response.toString(), new TypeToken<Base<PublicationComments>>(){}.getType());
                        if (responseObject.getStatusBody().getCode().equals("0")){
                            commentsAdapter.setComments(responseObject.getData().getComments());
                            commentsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        commentsSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                        commentsSwipeRefreshLayout.setRefreshing(false);
                    }
                });

    }


    @Override
    public void onRefresh() {
        getComments();
    }
}
