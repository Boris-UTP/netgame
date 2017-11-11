package com.netgame.netgame.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netgame.netgame.R;
import com.netgame.netgame.activities.DetailPublicationActivity;
import com.netgame.netgame.models.Publication;

import java.util.List;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Publication publication = publications.get(position);
        holder.titleTextView.setText(publication.getTitle());
        holder.descriptionTextView.setText(publication.getDescription());
        holder.setIconStartImageView(publication.getFavorite());
        holder.setIconThumbUpImageView(publication.getLike());

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

        public void setIconStartImageView(Boolean state) {
            if (state) {
                iconStartImageView.setColorFilter(Color.argb(255, 255, 255, 0));
            }
        }

        public void setIconThumbUpImageView(Boolean state){
            if (state) {
                iconThumbUpImageView.setColorFilter(Color.argb(0, 0, 255, 0));
            }
        }
    }
}
