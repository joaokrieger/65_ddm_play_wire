package com.ddm.playwire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddm.playwire.R;
import com.ddm.playwire.model.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteGameAdapter extends ArrayAdapter<Game> {

    private List<Game> gameListFull;

    public AutoCompleteGameAdapter(@NonNull Context context, @NonNull List<Game> gameList) {
        super(context, 0, gameList);
        gameListFull = new ArrayList<>(gameList);
    }

    @Override
    public Filter getFilter() {
        return gameFilterFilter;
    }

    public List<Game> getGameListFull() {
        return gameListFull;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.game_autocomplete_row, parent, false
            );
        }

        TextView tvGameTitleItem = convertView.findViewById(R.id.tvGameTitleItem);
        ImageView ivGameImage = convertView.findViewById(R.id.ivGameImage);

        Game game = getItem(position);
        if (game != null) {
            tvGameTitleItem.setText(game.getTitle());
            Picasso.get().load(game.getThumbnail())
                    .placeholder(R.drawable.loading)
                    .into(ivGameImage);
        }

        return convertView;
    }

    private Filter gameFilterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Game> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(gameListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Game item : gameListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Game) resultValue).getTitle();
        }
    };
}
