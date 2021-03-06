package com.example.spravochnik_kgz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivityEn extends AppCompatActivity {

    DB db;
    Cursor cursor;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_en);
        TextView tv_detail = findViewById(R.id.tv_detailEN);

        Intent intent = getIntent();
        String descText = intent.getStringExtra("WOW");

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getDetailItemEn(descText);
        logCursor(cursor);

        // startManagingCursor(cursor);

        if(cursor != null && cursor.moveToFirst()) {
            tv_detail.setText(cursor.getString(cursor
                    .getColumnIndex(DB.COLUMN_DESC)));
        }

    }
    // вывод в лог данных из курсора
    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }
}
