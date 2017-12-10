package com.example.mike0.eventsapp.details;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.adapter.DetailsAdapter;
import com.example.mike0.eventsapp.data.database.EventReaderContract.EventEntry;
import com.example.mike0.eventsapp.data.database.EventReaderDBHelper;
import com.example.mike0.eventsapp.data.model.Event;
import com.example.mike0.eventsapp.data.model.ItemClickListener;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener, DetailsView {

    private static final String TAG = "tag";

    DetailsPresenterImpl presenter;

    private RecyclerView eventsRecyclerView;
    private DetailsAdapter detailsAdapter;

    Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        presenter = new DetailsPresenterImpl(this, this);
        presenter.init();

        btnDetails = (Button) findViewById(R.id.btn_show_details);
        btnDetails.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "onClick: " + position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_details:
                presenter.getEvents();
                break;
        }
    }

    @Override
    public void showEvents(Cursor cursor) {
        eventsRecyclerView = (RecyclerView) findViewById(R.id.recycler_events);
        eventsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventsRecyclerView.setLayoutManager(linearLayoutManager);

        detailsAdapter = new DetailsAdapter(this, cursor);
        eventsRecyclerView.setAdapter(detailsAdapter);

        detailsAdapter.setClickListener(this);
    }
}
