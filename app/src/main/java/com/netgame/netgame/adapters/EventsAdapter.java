package com.netgame.netgame.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netgame.netgame.R;
import com.netgame.netgame.models.Event;

import java.util.List;

/**
 * Created by arkanay on 11/11/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public EventsAdapter setEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    public EventsAdapter (List<Event> events){
        this.events = events;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event,parent,false));
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);

        holder.titleTextView.setText(event.getTitle());
        holder.descriptionTextView.setText(event.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
