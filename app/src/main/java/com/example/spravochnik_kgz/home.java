package com.example.spravochnik_kgz;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class home extends AppCompatActivity  implements View.OnClickListener{

    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(this, homekg.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                intent = new Intent(this, homeru.class);
                startActivity(intent);
                break;

            case R.id.btn3:
                intent = new Intent(this, homeeng.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //Меню для приложения
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.o_nas:
                Intent intent = new Intent(this, oNas.class);
                startActivity(intent);
                break;


            case R.id.o_prog:
                intent = new Intent(this, infoAct.class);
                startActivity(intent);
                break;

            case R.id.obrat:
                intent = new Intent(this, obratSviaz.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

