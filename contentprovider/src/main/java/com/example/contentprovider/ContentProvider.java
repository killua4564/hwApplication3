package com.example.contentprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.contentprovider.Contract.Entry.TABLE_NAME;

public class ContentProvider extends android.content.ContentProvider {
    private static final int TASKS = 100;
    private static final int TASK_WITH_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_TASKS, TASKS);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_TASKS + "/#", TASK_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        this.dbHelper = new DbHelper(context);
        this.database = this.dbHelper.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (this.TASKS == this.uriMatcher.match(uri)) {
            long id = this.database.insert(TABLE_NAME, null, values);
            if (id > 0) {
                Uri returnUri = ContentUris.withAppendedId(Contract.Entry.CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            } else throw new android.database.SQLException("Failed to insert row into " + uri);
        } else throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (this.TASKS == this.uriMatcher.match(uri)) {
            Cursor retCursor = this.database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, Contract.Entry.COLUMN_TIMESTAMP);
            retCursor.setNotificationUri(this.getContext().getContentResolver(), uri);
            return retCursor;
        } else throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Nullable
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (this.TASK_WITH_ID == this.uriMatcher.match(uri)) {
            String id = uri.getPathSegments().get(1);
            int tasksDeleted = this.database.delete(TABLE_NAME, "_id=?", new String[]{id});
            if (tasksDeleted != 0) this.getContext().getContentResolver().notifyChange(uri, null);
            return tasksDeleted;
        } else throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Nullable
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

