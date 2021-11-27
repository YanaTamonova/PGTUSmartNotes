package com.example.pgtutodo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.pgtutodo.DatabaseHelper.TABLE_NAME;
import static com.example.pgtutodo.MainActivity.mDb;

public class ChangeDataActivity extends AppCompatActivity {

    Button btnSave, btnClear, btnDelete;
    EditText txtTitle, txtText;
    String title, text;
    String info;

    int lineNumber;
    String idFromLineNumber;

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Change Data");

        arguments = getIntent().getExtras();
        lineNumber = (int) arguments.get("position");
        Log.d("mLog", "Line Number = " + lineNumber + " + 1");

        btnSave = (Button)findViewById(R.id.btnSave);
        btnClear = (Button)findViewById(R.id.btnClear);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtText = (EditText) findViewById(R.id.txtText);

        System.out.println(title);
        System.out.println(text);

        Cursor cursor = mDb.rawQuery("SELECT * FROM '"+ TABLE_NAME + "' LIMIT 1 OFFSET " + lineNumber + " ", null);

        if (cursor != null){
            cursor.moveToFirst();
            info = cursor.getString(cursor.getColumnIndex("_id"));
            Log.d("mLog", "Real _id = " + info);
            try {

                info = cursor.getString(cursor.getColumnIndex("_id"));
                idFromLineNumber = "_id = " + info;
                Log.d("mLog", "id From Line Number = " + idFromLineNumber);
                Log.d("mLog", " ");

                info = cursor.getString(cursor.getColumnIndex("title"));
                txtTitle.setText(info);

                info = cursor.getString(cursor.getColumnIndex("text"));
                txtText.setText(info);

            }catch (Exception e){
                Toast.makeText(this, "Не удалось прочитать запись", Toast.LENGTH_SHORT).show();
                Log.d("mLog", String.valueOf(e));

            }finally {
                cursor.close();
            }
        }
        else
            Log.d("mLog","Cursor is null");
    }

    public void buttonClick(View view) {

        switch (view.getId()){
            case R.id.btnSave:
                title = txtTitle.getText().toString();
                text = txtText.getText().toString();

                if (title.equals("") || text.equals("")){
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {

                    try {
                        mDb.execSQL("UPDATE '"+ TABLE_NAME +"' SET title = ('" + title + "') WHERE (" + idFromLineNumber + ") ");
                        mDb.execSQL("UPDATE '"+ TABLE_NAME +"' SET text = ('" + text + "') WHERE (" + idFromLineNumber + ") ");

                        startActivityMain();
                    } catch (Exception e) {
                        Toast.makeText(this, "Повторите ввод", Toast.LENGTH_SHORT).show();
                        Log.d("mLog", String.valueOf(e));
                    }
                }

                break;

            case R.id.btnClear:
                txtTitle.setText("");
                txtText.setText("");
                break;

            case R.id.btnDelete:
                mDb.execSQL("DELETE FROM '"+ TABLE_NAME +"' WHERE (" + idFromLineNumber + ") ");
                startActivityMain();
                break;
        }
    }

    private void startActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
