package com.mycabbages.teamavatar.ido;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Preston on 2/24/2018.
 */

public class MainActivityListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] list_items;

    public MainActivityListAdapter(Activity context, String[] list_items) {
        super(context, R.layout.list_item, list_items);
        this.context = context;
        this.list_items = list_items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item_title);
        txtTitle.setText(list_items[position]);
        return rowView;
    }
}
