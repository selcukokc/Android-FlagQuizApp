package com.selcukokc.flagquiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FlagsDao { //this class is for database access

    public ArrayList<Flags> randomQuestions (Database database){
        ArrayList<Flags> flagsArrayList = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT* FROM countryflags ORDER BY RANDOM()",null);

        while(c.moveToNext()){ //this loop will turn 196 times
            Flags f = new Flags(c.getInt(c.getColumnIndex("flag_id")),
                    c.getString(c.getColumnIndex("flag_name")),
                    c.getString(c.getColumnIndex("flag_img")));
            flagsArrayList.add(f);

        }
        return flagsArrayList;

    }


    public ArrayList<Flags> falseOptions (Database database,int flag_id){ //this method is for inserting 3 false options
        ArrayList<Flags> flagsArrayList = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT* FROM countryflags WHERE flag_id !=" + flag_id + " ORDER BY RANDOM() LIMIT 3",null);

        while(c.moveToNext()){
            Flags f = new Flags(c.getInt(c.getColumnIndex("flag_id")),
                    c.getString(c.getColumnIndex("flag_name")),
                    c.getString(c.getColumnIndex("flag_img")));
            flagsArrayList.add(f);

        }
        return flagsArrayList;

    }


}
