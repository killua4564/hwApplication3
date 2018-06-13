package com.example.killua4564.hwapplication3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.killua4564.hwapplication3.Contract;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    private ContentResolver contentResolver;
    private RecyclerView recyclerView;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.contentResolver = (ContentResolver) this.getContentResolver();
        this.adapter = new Adapter(this, getAllData());
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                contentResolver.delete(Contract.Entry.CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build(), null, null);
                adapter.swapCursor(getAllData());
            }

        }).attachToRecyclerView(this.recyclerView);

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapter.swapCursor(getAllData());
    }

    private Cursor getAllData() {
        Cursor cursor = this.contentResolver.query(Contract.Entry.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}

