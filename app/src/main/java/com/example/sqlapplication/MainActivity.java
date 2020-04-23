package com.example.sqlapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button DB, Add, GetData;
    private SQLiteDbClass objectSQLDBClass;

    private EditText Name, Address;
    private TextView getValues;

    private ArrayList<DbModelClass> objectDbModelClassArrayList;
    private RecyclerView Rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectXML();
    }

    private void ConnectXML() {
        try {
            DB = findViewById(R.id.FORDB);
            objectSQLDBClass = new SQLiteDbClass(this);

            Name = findViewById(R.id.Name);
            Address = findViewById(R.id.Address);

            Add = findViewById(R.id.EnterData);
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addValuesToDB();
                }
            });

            getValues = findViewById(R.id.Data);
            GetData = findViewById(R.id.GetData);

            DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateDB();
                }
            });
            GetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetValuesFromDB();
                }
            });

            objectDbModelClassArrayList = new ArrayList<>();
            Rv = findViewById(R.id.RV);
        } catch (Exception ex) {
            Toast.makeText(this, "ConnectToXML: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void CreateDB() {
        try {
            objectSQLDBClass.getReadableDatabase();
        } catch (Exception ax) {
            Toast.makeText(this, "Create DB: " + ax.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addValuesToDB() {
        try {
            if (!Name.getText().toString().isEmpty() && !Address.getText().toString().isEmpty()) {
                SQLiteDatabase objectSQLiteDatabase = objectSQLDBClass.getWritableDatabase();
                if (objectSQLiteDatabase != null) {
                    ContentValues objectCV = new ContentValues();
                    objectCV.put("Name", Name.getText().toString());

                    objectCV.put("Address", Address.getText().toString());
                    long check = objectSQLiteDatabase.insert("classtable", null, objectCV);

                    if (check != 1) {
                        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        Name.setText(null);

                        Address.setText(null);
                        Name.requestFocus();
                    } else {
                        Toast.makeText(this, "Failed To Add Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "DB is null", Toast.LENGTH_SHORT).show();
                }
            } else if (Name.getText().toString().isEmpty()) {
                Toast.makeText(this, "Name Filed is empty", Toast.LENGTH_SHORT).show();
            } else if (Address.getText().toString().isEmpty()) {
                Toast.makeText(this, "Address Filed is empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "addValuesToDB:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void GetValuesFromDB() {
        try {
            SQLiteDatabase objectSQLiteDatabase = objectSQLDBClass.getReadableDatabase();
            if (objectSQLiteDatabase != null) {
                Cursor objectCursor = objectSQLiteDatabase.rawQuery("select * from classtable", null);
                StringBuffer objectStringBuffer = new StringBuffer();
                if (objectCursor.getCount() != 0) {
                    while (objectCursor.moveToNext()) {
                        DbModelClass objectDbModelClass = new DbModelClass();
                        objectDbModelClass.setName(objectCursor.getString(0));
                        objectDbModelClass.setAddress(objectCursor.getString(1));
                        objectDbModelClassArrayList.add(objectDbModelClass);
//                        objectStringBuffer.append("Name: " + objectCursor.getString(0) + "\n");
//                        objectStringBuffer.append("Address: " + objectCursor.getString(1) + "\n\n");
                    }
                    Rv.setLayoutManager(new LinearLayoutManager(this));
                    DbAdapterClass objectDbAdapterClass = new DbAdapterClass(objectDbModelClassArrayList);

                    Rv.setAdapter(objectDbAdapterClass);
//                    getValues.setText(objectStringBuffer);
                }
            } else {
                Toast.makeText(this, "No Data is avaliable to Fetch", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "GetValuesFromDB: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
