package com.example.pgtutodo;

import android.content.Intent;
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

public class NewDataActivity extends AppCompatActivity {

    Button btnSave, btnClear;
    EditText txtTitle, txtText;
    String title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnClear = (Button)findViewById(R.id.btnClear);

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtText = (EditText) findViewById(R.id.txtText);
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

    public void buttonClick(View view) {

        switch (view.getId()){
            case R.id.btnSave:

                title = txtTitle.getText().toString();
                text = txtText.getText().toString();

                System.out.println(title);
                System.out.println(text);

                if (title.equals("") || text.equals("")){
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        MainActivity.mDb.execSQL("INSERT INTO '"+ TABLE_NAME + "' (title, text) " +
                                "VALUES ('" + title + "', '" + text + "')");

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

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
        }
    }
}
