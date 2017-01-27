package com.example.x.bolusopas;
/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class HistoryAdapter extends ArrayAdapter<HistoryItem> {

    public HistoryAdapter(Context ctx, ArrayList<HistoryItem> history) {
        super(ctx, 0, history);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HistoryItem historyItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        final TextView listViewInfoTextView = (TextView) convertView.findViewById(R.id.listViewInfoTextView);
        final Button listViewEditButton = (Button) convertView.findViewById(R.id.listViewEditButton);
        final Button listViewRemoveButton = (Button) convertView.findViewById(R.id.listViewRemoveButton);

        listViewInfoTextView.setText(historyItem.getResult().toString());
        listViewEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listViewRemoveButton.getVisibility() == View.VISIBLE) {
                    listViewRemoveButton.setVisibility(View.INVISIBLE);
                    listViewRemoveButton.setClickable(false);
                }
                else {
                    listViewRemoveButton.setVisibility(View.VISIBLE);
                    listViewRemoveButton.setClickable(true);
                }
            }
        });
        listViewRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewInfoTextView.setText("Poistettu");
                DatabaseQuery dq = new DatabaseQuery(getContext());
                dq.deleteResult(historyItem.getResult().getId());
                dq.closeDatabaseHelper();
            }
        });

        return convertView;
    }
}
*/


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter {

    private Context context;
    //private boolean useList = true;

    public HistoryAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder {
        //TextView titleText;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final HistoryItem historyItem = (HistoryItem) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        final TextView listViewInfoTextView = (TextView) convertView.findViewById(R.id.listViewInfoTextView);
        final Button listViewEditButton = (Button) convertView.findViewById(R.id.listViewEditButton);
        final Button listViewRemoveButton = (Button) convertView.findViewById(R.id.listViewRemoveButton);

        listViewInfoTextView.setText(historyItem.getResult().toString());
        listViewEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listViewRemoveButton.getVisibility() == View.VISIBLE) {
                    listViewRemoveButton.setVisibility(View.INVISIBLE);
                    listViewRemoveButton.setClickable(false);
                }
                else {
                    listViewRemoveButton.setVisibility(View.VISIBLE);
                    listViewRemoveButton.setClickable(true);
                }
            }
        });
        listViewRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewInfoTextView.setText("Poistettu");
                DatabaseQuery dq = new DatabaseQuery(getContext());
                dq.deleteResult(historyItem.getResult().getId());
                dq.closeDatabaseHelper();
            }
        });

        return convertView;
    }
}
