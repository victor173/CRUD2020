package com.example.crud2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import entidades.DTO;

public class activity_detalle_articulos extends AppCompatActivity {

    private TextView tdcod, tddesc, tdpz;
    private TextView tdcod1,tddesc1,tdpz1, tfcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulos);

        tdcod = (TextView) findViewById(R.id.tv_cod);
        tddesc = (TextView) findViewById(R.id.tv_desc);
        tdpz = (TextView) findViewById(R.id.tv_precio);


        tdcod1 = (TextView) findViewById(R.id.tv_cod1);
        tddesc1 = (TextView) findViewById(R.id.tv_desc1);
        tdpz1 = (TextView) findViewById(R.id.tv_precioc1);
        tfcha = (TextView) findViewById(R.id.tv_fecha);


        Bundle obj = getIntent().getExtras();
        DTO dto = null;
        if (obj != null){
            dto = (DTO) obj.getSerializable("articulo");
            tdcod.setText(""+dto.getCodigo());
            tddesc.setText(dto.getDescripcion());
            tdpz.setText(""+dto.getPrecio());

            tdcod1.setText(""+dto.getCodigo());
            tddesc1.setText(dto.getDescripcion());
            tdpz1.setText(String.valueOf(dto.getPrecio()));
            tfcha.setText("Fecha de creacion: "+getDateTime());




        }

    }

    private String getDateTime(){
        SimpleDateFormat dates = new SimpleDateFormat(
                "yyyy-MM-dd-HH:mm:ss a", Locale.getDefault());
        Date day = new Date();
        return dates.format(day);
    }

    public  void  vol (View view){
        onBackPressed();
    }
}