package com.example.spravochnik_kgz;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ActivityTwoRu extends AppCompatActivity {
    String[] letters = { "А", "Б","Г", "Д", "Ж","И", "К",
            "Л", "М", "Н", "О", "П", "C", "Т", "У",
            "Х","Ч","Ш","Ы","Я"};
    ListView spisok;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    final String LOG_TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_ru);

        Intent intent = getIntent();
        int clickLetter = intent.getIntExtra("OMG", 0);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getSepficItemRU(letters[clickLetter]);

        logCursor(cursor);
        Log.d(LOG_TAG, "Cursor is null22");
        startManagingCursor(cursor);


        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_NAME};
        int[] to = new int[] { R.id.tvText };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        spisok = (ListView) findViewById(R.id.lvCityru);
        spisok.setAdapter(scAdapter);

        spisok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item_content = "";
                cursor.moveToFirst();
                for (int i = 0; i <= position; i++) {
                    item_content = cursor.getString(cursor
                            .getColumnIndex(DB.COLUMN_NAME));
                    cursor.moveToNext();
                }
                Intent intent = new Intent(ActivityTwoRu.this, DetailActivityRu.class);
                intent.putExtra("WOW", item_content);
                Log.d(LOG_TAG, "ОТПРАВЛЯЕМ " + item_content);
                startActivity(intent);

            }
        });

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
