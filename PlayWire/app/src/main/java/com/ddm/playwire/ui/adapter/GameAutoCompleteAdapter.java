package com.ddm.playwire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.Game;

import java.util.List;

public class GameAutoCompleteAdapter extends BaseAdapter {

    private Context context;
    private List<Game> games;

    public GameAutoCompleteAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return games.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.game_autocomplete_item, viewGroup, false);
        Game game = games.get(position);

        TextView tvGameTitleItem = item.findViewById(R.id.tvGameTitleItem);
        ImageView ivGameImage = item.findViewById(R.id.ivGameImage);

        tvGameTitleItem.setText(game.getTitle());

        return null;
    }
}
