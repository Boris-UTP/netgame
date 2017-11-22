package com.netgame.netgame.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.activities.DetailPublicationActivity;
import com.netgame.netgame.activities.PublicationActivity;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Publication;

import org.json.JSONObject;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.netgame.netgame.network.NetGameApiService.FAVORITE_PUBLICATION;
import static com.netgame.netgame.network.NetGameApiService.LIKE_PUBLICATION;

/**
 * Created by arkanay on 10/10/17.
 */

public class PublicationsAdapter extends RecyclerView.Adapter<PublicationsAdapter.ViewHolder> {

    List<Publication> publications;

    public PublicationsAdapter(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_publication, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Publication publication = publications.get(position);
        holder.titleTextView.setText(publication.getTitle());
        holder.descriptionTextView.setText(publication.getDescription());
        holder.dateTextView.setText(publication.getDateRegister());
        holder.setIconStartImageView(publication.getFavorite());
        holder.setIconThumbUpImageView(publication.getLike());

        holder.iconStartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int favorite = publications.get(position).getFavorite();
                if (favorite == 0) {
                    publications.get(position).setFavorite(1);
                    holder.setIconStartImageView(1);
                    postFavorite(view, publication.getId(), "1");
                } else {
                    publications.get(position).setFavorite(0);
                    holder.setIconStartImageView(0);
                    postFavorite(view, publication.getId(), "0");
                }
            }
        });

        holder.iconThumbUpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int like = publications.get(position).getLike();
                if (like == 0) {
                    publications.get(position).setLike(1);
                    holder.setIconThumbUpImageView(1);
                    postLike(view, publication.getId(), "1");
                } else {
                    publications.get(position).setLike(0);
                    holder.setIconThumbUpImageView(0);
                    postLike(view, publication.getId(), "0");
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailPublicationActivity.class);
                intent.putExtras(publication.toBundle());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }


    public List<Publication> getPublications() {
        return publications;
    }

    public PublicationsAdapter setPublications(List<Publication> publications) {
        this.publications = publications;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        ImageView iconStartImageView;
        ImageView iconThumbUpImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            iconStartImageView = itemView.findViewById(R.id.iconStartImageView);
            iconThumbUpImageView = itemView.findViewById(R.id.iconThumbUpImageView);

        }

        public void setIconStartImageView(int state) {
            if (state == 0) {
                iconStartImageView.setColorFilter(Color.rgb(0, 0, 0));
            } else {
                iconStartImageView.setColorFilter(Color.rgb(0, 150, 136));
            }
        }

        public void setIconThumbUpImageView(int state) {
            if (state == 0) {
                iconThumbUpImageView.setColorFilter(Color.rgb(0, 0, 0));
            } else {
                iconThumbUpImageView.setColorFilter(Color.rgb(0, 150, 136));
            }
        }
    }


    private void postLike(final View view, String idPublication, String like) {

        AndroidNetworking
                .post(LIKE_PUBLICATION)
                .addBodyParameter("idPublication", idPublication)
                .addBodyParameter("like", like)
                .addHeaders("token", PreferencesEditor.getStringPreference(view.getContext(), "token", ""))
                .setTag(view.getResources().getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (!responseObject.getStatusBody().getCode().equals("0")) {
                            Log.d(view.getResources().getString(R.string.app_name), responseObject.getStatusBody().getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(view.getResources().getString(R.string.app_name), anError.getMessage());
                    }
                });
    }

    private void postFavorite(final View view, String idPublication, String favorite) {
        AndroidNetworking
                .post(FAVORITE_PUBLICATION)
                .addBodyParameter("idPublication", idPublication)
                .addBodyParameter("favorite", favorite)
                .addHeaders("token", PreferencesEditor.getStringPreference(view.getContext(), "token", ""))
                .setTag(view.getResources().getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (!responseObject.getStatusBody().getCode().equals("0")) {
                            Log.d(view.getResources().getString(R.string.app_name), responseObject.getStatusBody().getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(view.getResources().getString(R.string.app_name), anError.getMessage());
                    }
                });
    }

}
