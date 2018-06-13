package com.example.killua4564.hwapplication3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.killua4564.hwapplication3.Contract;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public Adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!this.cursor.moveToPosition(position)) return;
        long id = this.cursor.getLong(this.cursor.getColumnIndex(Contract.Entry._ID));
        String name = this.cursor.getString(this.cursor.getColumnIndex(Contract.Entry.COLUMN_NAME));
        String phone = this.cursor.getString(this.cursor.getColumnIndex(Contract.Entry.COLUMN_PHONE));
        int people = this.cursor.getInt(this.cursor.getColumnIndex(Contract.Entry.COLUMN_PEOPLE));
        holder.itemView.setTag(id);
        holder.nameTextView.setText(name);
        holder.phoneTextView.setText(phone);
        holder.peopleTextView.setText(String.valueOf(people));
    }

    @Override
    public int getItemCount() {
        return this.cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (this.cursor != null) this.cursor.close();
        if (newCursor != null) this.notifyDataSetChanged();
        this.cursor = newCursor;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
        private TextView peopleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nameTextView = (TextView) this.itemView.findViewById(R.id.nameTextView);
            this.phoneTextView = (TextView) this.itemView.findViewById(R.id.phoneTextView);
            this.peopleTextView = (TextView) this.itemView.findViewById(R.id.peopleTextView);
        }
    }
}