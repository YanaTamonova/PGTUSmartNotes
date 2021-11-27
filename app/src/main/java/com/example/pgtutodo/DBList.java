package com.example.pgtutodo;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.pgtutodo.DatabaseHelper.TABLE_NAME;
import static com.example.pgtutodo.MainActivity.mDb;

class DBList {

    Context mContext;

    DBList(Context context){
        mContext = context;
    }

    SimpleAdapter dbList(){
        ArrayList<HashMap<String, Object>> clients = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> client;

        Cursor cursor = mDb.rawQuery("SELECT * FROM ('" + TABLE_NAME + "')", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            client = new HashMap<String, Object>();

            client.put("title",  cursor.getString(1));
            client.put("text",  cursor.getString(2));

            clients.add(client);
            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "title", "text" };
        int[] to = { R.id.titleView };

        SimpleAdapter adapter = new SimpleAdapter(mContext, clients, R.layout.adapter_item, from, to);

        return adapter;
    }
}
