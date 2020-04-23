package com.example.sqlapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteDbClass extends SQLiteOpenHelper {
    private static final String Data_Base_Name = "Class.db";
    private static final int Data_Base_Version = 1;

    private Context context;
    private String queryToCreate = "create table classtable (Name VARCHAR(255), Address VARCHAR(255))";


    public SQLiteDbClass(@Nullable Context context) {
        super(context, Data_Base_Name, null, Data_Base_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(queryToCreate);
            Toast.makeText(context, "Database and Table Created", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, "onCreateDB: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
