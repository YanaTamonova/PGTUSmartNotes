package com.example.pgtutodo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import static com.example.pgtutodo.DatabaseHelper.TABLE_NAME;

import java.io.IOException;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    private Menu optMenu;

    ListView listView;
    SimpleAdapter adapter;
    DBList dbList;

    private DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);
        dbList = new DBList(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDataActivity();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeDataActivity(position);
            }
        });

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        dbListM();
    };

    public void newDataActivity(){
        Intent intent = new Intent(this, NewDataActivity.class);
        startActivity(intent);
    }

    public void changeDataActivity(int position){
        Intent intent = new Intent(this, ChangeDataActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void dbListM(){
        adapter = dbList.dbList();
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbListM();
    }
}