package com.example.spravochnik_kgz;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;

public class MainActivityRu extends AppCompatActivity {

    String[] letters = { "А", "Б", "В", "Г", "Д", "Ж", "З", "И", "К",
            "Л", "М", "Н", "О", "П", "C", "Т", "У","Ф", "Х", "Ц", "Ч", "Ш", "Ы", "Э", "Ю"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ru);

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvMainRu);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, letters);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivityRu.this, ActivityTwoRu.class);
                intent.putExtra("OMG", position);
                startActivity(intent);

            }
        });
    }
}
