package com.selcukokc.flagquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    String query;
    public Database(@Nullable Context context) {
        super(context, "flags.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cflags` (\n" +
                "\t`flag_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`flag_name`\tTEXT,\n" +
                "\t`flag_img`\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cflags");
        onCreate(db);
    }
}
