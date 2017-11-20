package com.example.mike0.eventsapp.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.EventsAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mike0 on 11/19/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    public static final String TAG = "tag";

    private List<Event> eventsList;

    public EventsAdapter(List<Event> resultList) {
        this.eventsList = resultList;
    }

    public void updateDataSet(List<Event> resultList) {
        this.eventsList = resultList;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.eventsTitle.setText(eventsList.get(position).getName().getText());
        holder.eventsDesc.setText(eventsList.get(position).getDescription().getText().substring(0, 175) + "...");

        Date date1 = null;
        String newDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date1 = formatter.parse(eventsList.get(position).getStart().getLocal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        newDate = formatter2.format(date1);
        holder.eventsTime.setText("Start Date:  " + newDate);

        holder.eventsWebpage.setText(eventsList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    private String buildEvents(EventsAPI result) {
        StringBuilder eventsBuilder = new StringBuilder();
        eventsBuilder.append(result.getEvents());
        return eventsBuilder.toString();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventsTitle;
        TextView eventsDesc;
        TextView eventsTime;
        TextView eventsWebpage;

        public ViewHolder(View itemView) {
            super(itemView);
            eventsTitle = (TextView) itemView.findViewById(R.id.item_event_title);
            eventsDesc = (TextView) itemView.findViewById(R.id.item_event_desc);
            eventsTime = (TextView) itemView.findViewById(R.id.item_event_time);
            eventsWebpage = (TextView) itemView.findViewById(R.id.item_event_webpage);
        }

        /*@Override
        public void onClick(View view) {
            Context context = view.getContext();
            Books result = eventsList.get(getAdapterPosition());
            context.startActivity(DetailActivity.getDetailActivityIntent(context, result));
        }*/
    }
}
