package com.example.mike0.eventsapp.data.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.database.EventReaderContract.EventEntry;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.ItemClickListener;
import com.example.mike0.eventsapp.main.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mike0 on 11/19/2017.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    public static final String TAG = "tag";

    CursorAdapter cursorAdapter;

    private Context context;

    private int lastClick, currentClick, itemSelectState, defaultTextColors;

    private List<Integer> savedList = new ArrayList<>(0);

    private ItemClickListener clickListener;

    public DetailsAdapter(Context context, Cursor cursor) {
        this.context = context;

        itemSelectState = 0;
        cursorAdapter = new CursorAdapter(context, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }
        };
    }

    public void updateDataSet(List<Event> resultList) {
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_events, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursorAdapter.getCursor().moveToPosition(position);
        cursorAdapter.bindView(holder.itemView, context, cursorAdapter.getCursor());
        holder.eventsTitle.setText(cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_TITLE)));

        if (cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_DESCRIPTION)) != null) {
            holder.eventsDesc.setText(cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_DESCRIPTION)).substring(0, 175) + "...");
        } else {
            holder.eventsDesc.setText(cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_DESCRIPTION)));
        }

        holder.eventsTime.setText(cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_TIME)));
        holder.eventsWebpage.setText(cursorAdapter.getCursor().getString(cursorAdapter.getCursor().getColumnIndexOrThrow(EventEntry.COLUMN_NAME_URL)));

        holder.favIcon.setVisibility(View.VISIBLE);

        /*if (eventsList.get(position).getDescription().getText() != null) {
            holder.eventsDesc.setText(eventsList.get(position).getDescription().getText().substring(0, 175) + "...");
        }*/

        /*Date date1 = null;
        String newDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date1 = formatter.parse(eventsList.get(position).getStart().getLocal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        newDate = formatter2.format(date1);*/
        /*holder.eventsTime.setText("Start Date:  " + newDate);

        holder.eventsWebpage.setText(eventsList.get(position).getUrl());*/

        /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalPosition = holder.getAdapterPosition();
                Log.d(TAG, "globalPosition: " + globalPosition);
                if (holder.favIcon.getVisibility() == View.INVISIBLE) {
                    holder.favIcon.setVisibility(View.VISIBLE);
                } else if (holder.favIcon.getVisibility() == View.VISIBLE) {
                    holder.favIcon.setVisibility(View.INVISIBLE);
                }

                currentClick = holder.getAdapterPosition();

                //itemSelectState++;
                //lastClick = currentClick;
                Log.d(TAG, "lastClick: " + lastClick);

                Log.d(TAG, "currentClick: " + currentClick);

                Log.d(TAG, "itemSelectState: " + itemSelectState);

                if (currentClick == lastClick) {
                    itemSelectState++;
                } else if (currentClick != lastClick) {
                    holder.eventsTitle.setTextColor(defaultTextColors);
                    holder.eventsDesc.setTextColor(defaultTextColors);
                    holder.eventsTime.setTextColor(defaultTextColors);
                    holder.eventsWebpage.setTextColor(defaultTextColors);
                    lastClick = currentClick;
                    itemSelectState = 0;
                    itemSelectState++;
                }

                *//*if (itemSelectState == 0) {
                    lastClick = currentClick;
                    holder.eventsTitle.setTextColor(defaultTextColors);
                    holder.eventsDesc.setTextColor(defaultTextColors);
                    holder.eventsTime.setTextColor(defaultTextColors);
                    holder.eventsWebpage.setTextColor(defaultTextColors);
                }*//*

                if (itemSelectState == 1 && currentClick == lastClick) {
                    Log.d(TAG, "lastClick: " + lastClick);

                    Log.d(TAG, "currentClick: " + currentClick);
                    defaultTextColors = holder.eventsTitle.getTextColors().getDefaultColor();

                    holder.eventsTitle.setTextColor(Color.BLUE);
                    holder.eventsDesc.setTextColor(Color.BLUE);
                    holder.eventsTime.setTextColor(Color.BLUE);
                    holder.eventsWebpage.setTextColor(Color.BLUE);

                    currentClick = holder.getAdapterPosition();
                }


                if (holder.eventsTitle.getTextColors().getDefaultColor() != defaultTextColors && itemSelectState == 2 && currentClick == lastClick) {
                    itemSelectState++;
                    Log.d(TAG, "lastClick: " + lastClick);

                    Log.d(TAG, "currentClick: " + currentClick);
                }
                if (itemSelectState == 3 && currentClick == lastClick) {
                    Log.d(TAG, "lastClick: " + lastClick);

                    Log.d(TAG, "currentClick: " + currentClick);
                    new AlertDialog.Builder(context)
                            .setTitle("Save Event")
                            .setMessage("Would you like to save this event to your database?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveEvent = true;
                                    itemSelectState = 0;
                                    holder.eventsTitle.setTextColor(defaultTextColors);
                                    holder.eventsDesc.setTextColor(defaultTextColors);
                                    holder.eventsTime.setTextColor(defaultTextColors);
                                    holder.eventsWebpage.setTextColor(defaultTextColors);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    itemSelectState = 0;
                                    holder.eventsTitle.setTextColor(defaultTextColors);
                                    holder.eventsDesc.setTextColor(defaultTextColors);
                                    holder.eventsTime.setTextColor(defaultTextColors);
                                    holder.eventsWebpage.setTextColor(defaultTextColors);
                                }
                            })
                            .show();
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return cursorAdapter.getCount();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView favIcon;

        TextView eventsTitle;
        TextView eventsDesc;
        TextView eventsTime;
        TextView eventsWebpage;

        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            favIcon = (ImageView) itemView.findViewById(R.id.fav_icon);

            eventsTitle = (TextView) itemView.findViewById(R.id.item_event_title);
            eventsDesc = (TextView) itemView.findViewById(R.id.item_event_desc);
            eventsTime = (TextView) itemView.findViewById(R.id.item_event_time);
            eventsWebpage = (TextView) itemView.findViewById(R.id.item_event_webpage);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_events_list);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }
}
