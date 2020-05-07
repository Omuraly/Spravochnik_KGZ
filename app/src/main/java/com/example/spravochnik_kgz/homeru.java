package com.example.spravochnik_kgz;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;


public class homeru extends AppCompatActivity implements View.OnClickListener {

    Button btnru;

    ImageView searchIv;
    AutoCompleteTextView searchForm;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    DB db;
    final String LOG_TAG="myLogs";
    List<String>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeru);

        btnru = (Button) findViewById(R.id.btnru);
        btnru.setOnClickListener(this);


        list = new ArrayList<String>();

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getAllItemRU();
        cursorToList(cursor);

        searchForm = (AutoCompleteTextView) findViewById(R.id.inputSearch);

        final String[] mGeo = list.toArray(new String[0]);



        searchForm.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, mGeo));



        searchIv = (ImageView) findViewById(R.id.search_button_ru);
        searchIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnru:
                Intent intent = new Intent(this, MainActivityRu.class);
                startActivity(intent);
                break;


            case   R.id.search_button_ru:
                String geoText = searchForm.getText().toString();
                Intent searchIntent = new Intent(this, DetailActivityRu.class);
                searchIntent.putExtra("WOW", geoText);
                startActivity(searchIntent);
            default:
                break;

        }
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }
    // вывод в лог данных из курсора
    void cursorToList(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                int counter = 0;
                do {
                    //str = "";
                    for (String cn : c.getColumnNames()) {
                        if (counter%2 == 1) {
                            String geo = c.getString(c.getColumnIndex(cn));
                            list.add(geo);
                            Log.d(LOG_TAG, geo);
                        }
                        counter++;
                    }
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }
}
