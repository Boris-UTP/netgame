package com.netgame.netgame.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netgame.netgame.R;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.gameTitleTextView.setText(game.getName());
        // holder.setGameImageView(game.getId());
        holder.gameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Hola", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView  gameImageView;
        private TextView gameTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            gameImageView = (ImageView) itemView.findViewById(R.id.gameImageView);
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
