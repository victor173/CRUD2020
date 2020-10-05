package com.example.crud2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import entidades.ConexionSQLite;
import entidades.DTO;

public class activity_consulta extends AppCompatActivity {
    private Spinner spoptions;
    private TextView tcod,tdec,tpz;

    ConexionSQLite conexion = new ConexionSQLite(this);
    DTO dato = new DTO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        Toolbar toolbar3 = findViewById(R.id.toolbar3);
        toolbar3.setNavigationIcon(getResources().getDrawable(R.drawable.bacl));
        toolbar3.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar3.setTitleMargin(0,0,0,0);
        toolbar3.setSubtitle("Spinner View");
        toolbar3.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar3.setTitle("SIS22B");
        setSupportActionBar(toolbar3);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver();
            }
        });

        spoptions = (Spinner)findViewById(R.id.spioptions);
        tcod = (TextView) findViewById(R.id.codspin);
        tdec = (TextView) findViewById(R.id.descpin);
        tpz = (TextView) findViewById(R.id.pzspin);

        conexion.consultarListaArticulos();

        ArrayAdapter<CharSequence> adap = new ArrayAdapter(this,android.R.layout.simple_spinner_item,conexion.obtenerListaArticulos());
        spoptions.setAdapter(adap);

        spoptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    tcod.setText("Codigo: "+conexion.consultarListaArticulos().get(i-1).getCodigo());
                    tdec.setText("Descripcion: "+conexion.consultarListaArticulos().get(i-1).getDescripcion());
                    tpz.setText("Precio: "+conexion.consultarListaArticulos().get(i-1).getPrecio());

                }else
                    {
                    tcod.setText("Codigo: ");
                    tdec.setText("Descripción: ");
                    tpz.setText("Precio: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  void  volver (){
        onBackPressed();
    }
}