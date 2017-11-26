package com.netgame.netgame.activities;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
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
import com.netgame.netgame.commons.UtilsIO;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Comment;
import com.netgame.netgame.models.Publication;
import com.netgame.netgame.models.PublicationComments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.COMMENTS_URL;
import static com.netgame.netgame.network.NetGameApiService.FAVORITE_PUBLICATION;
import static com.netgame.netgame.network.NetGameApiService.LIKE_PUBLICATION;

public class DetailPublicationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private ImageView iconThumbUpImageView;
    private ImageView iconStartImageView;

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

        addBackToolbar();

        publication = Publication.from(getIntent().getExtras());
        tag = getResources().getString(R.string.app_name);
        gson = new Gson();

        commentFloatingActionButton = findViewById(R.id.commentFloatingActionButton);
        commentFloatingActionButton.setOnClickListener(this);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView = findViewById(R.id.dateTextView);
        iconThumbUpImageView = findViewById(R.id.iconThumbUpImageView);
        iconStartImageView = findViewById(R.id.iconStartImageView);

        iconThumbUpImageView.setOnClickListener(this);
        iconStartImageView.setOnClickListener(this);

        titleTextView.setText(publication.getTitle());
        descriptionTextView.setText(publication.getDescription());
        dateTextView.setText(UtilsIO.formatStringToString(publication.getDateRegister(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"));

        setColorLike();
        setColorFavorite();

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_publication;
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

    private void setColorLike() {
        if (publication.getLike() == 0) {
            iconThumbUpImageView.setColorFilter(Color.rgb(0, 0, 0));
        } else {
            iconThumbUpImageView.setColorFilter(Color.rgb(0, 150, 136));
        }
    }

    private void setColorFavorite() {
        if (publication.getFavorite() == 0) {
            iconStartImageView.setColorFilter(Color.rgb(0, 0, 0));
        } else {
            iconStartImageView.setColorFilter(Color.rgb(0, 150, 136));
        }
    }

    private void getComments() {
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
                        Base<PublicationComments> responseObject = gson.fromJson(response.toString(), new TypeToken<Base<PublicationComments>>() {
                        }.getType());
                        if (responseObject.getStatusBody().getCode().equals("0")) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iconStartImageView:
                if (publication.getFavorite() == 0) {
                    publication.setFavorite(1);
                    postFavorite(publication.getId(), "1");
                } else {
                    publication.setFavorite(0);
                    postFavorite(publication.getId(), "0");
                }
                setColorFavorite();
                break;
            case R.id.iconThumbUpImageView:
                if (publication.getLike() == 0) {
                    publication.setLike(1);
                    postLike(publication.getId(), "1");
                } else {
                    publication.setLike(0);
                    postLike(publication.getId(), "0");
                }
                setColorLike();
                break;
            case R.id.commentFloatingActionButton:
                Intent intent = new Intent(getApplicationContext(), CreateCommentActivity.class);
                intent.putExtras(publication.toBundle());
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (requestCode == RESULT_OK){
                getComments();
            }
        }
    }

    private void postLike(String idPublication, String like) {
        AndroidNetworking
                .post(LIKE_PUBLICATION)
                .addBodyParameter("idPublication", idPublication)
                .addBodyParameter("like", like)
                .addHeaders("token", PreferencesEditor.getStringPreference(getApplicationContext(), "token", ""))
                .setTag(getResources().getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (!responseObject.getStatusBody().getCode().equals("0")) {
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                    }
                });
    }

    private void postFavorite(String idPublication, String favorite) {
        AndroidNetworking
                .post(FAVORITE_PUBLICATION)
                .addBodyParameter("idPublication", idPublication)
                .addBodyParameter("favorite", favorite)
                .addHeaders("token", PreferencesEditor.getStringPreference(getApplicationContext(), "token", ""))
                .setTag(getResources().getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (!responseObject.getStatusBody().getCode().equals("0")) {
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                    }
                });
    }
}
