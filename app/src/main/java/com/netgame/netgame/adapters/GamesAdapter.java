package com.netgame.netgame.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.netgame.netgame.R;
import com.netgame.netgame.activities.PublicationActivity;
import com.netgame.netgame.models.Game;

import java.util.List;

/**
 * Created by arkanay on 9/10/17.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private List<Game> games;

    public GamesAdapter(List<Game> games){
        this.games = games;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.card_game, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Game game = games.get(position);
        holder.gameTitleTextView.setText(game.getName());

        holder.pictureANImageView.setDrawingCacheEnabled(true);
        holder.pictureANImageView.setDefaultImageResId(R.drawable.ic_launcher_background);
        holder.pictureANImageView.setErrorImageResId(R.drawable.ic_launcher_background);
        holder.pictureANImageView.setImageUrl(game.getImage());


        holder.pictureANImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game game = games.get(position);
                Intent intent = new Intent(view.getContext(), PublicationActivity.class);
                intent.putExtras(game.toBundle());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ANImageView pictureANImageView;
        private TextView gameTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            pictureANImageView = itemView.findViewById(R.id.pictureANImageView);
            gameTitleTextView = (TextView) itemView.findViewById(R.id.titleGameTextView);


        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}
