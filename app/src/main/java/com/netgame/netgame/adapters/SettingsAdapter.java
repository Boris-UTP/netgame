package com.netgame.netgame.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netgame.netgame.R;
import com.netgame.netgame.activities.EditCabinUserActivity;
import com.netgame.netgame.activities.EditGamerUserActivity;
import com.netgame.netgame.activities.LoginActivity;
import com.netgame.netgame.commons.PreferencesEditor;

import java.util.List;

/**
 * Created by arkanay on 23/11/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    List<String> settings;

    public SettingsAdapter(List<String> settings) {
        this.settings = settings;
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setting, parent, false));
    }

    @Override
    public void onBindViewHolder(final SettingsAdapter.ViewHolder holder, final int position) {
        String setting = settings.get(position);

        holder.stringTextView.setText(setting);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (position) {
                    case 0:
                        if (PreferencesEditor.getIntPreference(holder.itemView.getContext(), "typeUser", 1) == 1) {
                            intent = new Intent(view.getContext(), EditGamerUserActivity.class);
                        } else {
                            intent = new Intent(view.getContext(), EditCabinUserActivity.class);
                        }
                        view.getContext().startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        view.getContext().startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stringTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            stringTextView = itemView.findViewById(R.id.stringTextView);

        }
    }
}
