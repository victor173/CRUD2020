package com.example.crud2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import  android.os.Bundle;
import  java.util.List;
import  java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import adapter.adaptadorart;
import entidades.ConexionSQLite;

public class consultarecy extends AppCompatActivity {
    private RecyclerView recicle;
    private adaptadorart Adapt;
    ConexionSQLite dato = new ConexionSQLite(consultarecy.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarecy);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setNavigationIcon(getResources().getDrawable(R.drawable.bacl));
        toolbar2.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar2.setTitleMargin(0,0,0,0);
        toolbar2.setSubtitle("Recyclear View");
        toolbar2.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar2.setTitle("SIS22B");
        setSupportActionBar(toolbar2);

        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver();
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recicle = (RecyclerView) findViewById(R.id.recyone);
        recicle.setHasFixedSize(true);

        recicle.setLayoutManager(new LinearLayoutManager(this));
        Adapt = new adaptadorart(consultarecy.this,dato.mostrarArticulos());
        recicle.setAdapter(Adapt);
    }

    public  void  volver (){
        onBackPressed();
    }
}