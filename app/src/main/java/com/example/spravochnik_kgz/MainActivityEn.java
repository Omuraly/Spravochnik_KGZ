package com.example.spravochnik_kgz;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;

public class MainActivityEn extends AppCompatActivity {

    String[] letters = { "A", "B","C", "D","E", "F","G", "H","I", "J","K",
            "L","M", "N","O", "P","Q", "R","S", "T","U", "V","W", "X","Y", "Z", };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_en);

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvMainEn);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, letters);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivityEn.this, ActivityTwoEn.class);
                intent.putExtra("OMG", position);
                startActivity(intent);

            }
        });

    }
}
