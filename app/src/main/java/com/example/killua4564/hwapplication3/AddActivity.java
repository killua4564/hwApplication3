package com.example.killua4564.hwapplication3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.killua4564.hwapplication3.Contract;

public class AddActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private Spinner peopleSpinner;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add);
        this.contentResolver = (ContentResolver) this.getContentResolver();
        this.nameEditText = (EditText) this.findViewById(R.id.nameEditText);
        this.phoneEditText = (EditText) this.findViewById(R.id.phoneEditText);
        this.peopleSpinner = (Spinner) this.findViewById(R.id.peopleSpinner);
        this.peopleSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"1", "2", "3", "4"}));
    }

    public void addTolist(View view) {
        if (this.nameEditText.getText().length() == 0 || this.phoneEditText.getText().length() == 0) return;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_NAME, this.nameEditText.getText().toString());
        contentValues.put(Contract.Entry.COLUMN_PHONE, this.phoneEditText.getText().toString());
        contentValues.put(Contract.Entry.COLUMN_PEOPLE, Integer.parseInt(this.peopleSpinner.getSelectedItem().toString()));
        this.contentResolver.insert(Contract.Entry.CONTENT_URI , contentValues);
        this.nameEditText.clearFocus();
        this.phoneEditText.getText().clear();
        this.peopleSpinner.setSelection(0);
        this.finish();
    }
}
