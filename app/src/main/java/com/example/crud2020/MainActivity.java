package com.example.crud2020;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import  android.widget.Toast;
import  android.widget.Button;

import  android.database.Cursor;
import  android.database.sqlite.SQLiteDatabase;
import  android.content.Intent;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import entidades.ConexionSQLite;
import entidades.DTO;
import modal.Modal;

public class MainActivity extends AppCompatActivity {

    private EditText etcod,etdesc,etpz;
    private Button btng,btnbcod,btndes,btnmod,btndell;
    private TextView result;

    boolean inputET = false;
    boolean inputED = false;
    boolean input1 = false;
    int resultInsert = 0;

   // "Despues se activa"
   Modal ventanas = new Modal();
    ConexionSQLite conexion = new ConexionSQLite(this);
    DTO dato = new DTO();
    AlertDialog.Builder wiuwiu;

    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if (KeyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.cancel)
                    .setTitle("Advertencia")
                    .setMessage("¿Quieres salir?")
                    .setNegativeButton(android.R.string.cancel,null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .show();

            return true;
        }

        return  super.onKeyDown(KeyCode,event);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.close));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD 2020 SQLite ");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("SIS22B");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comfirmacion();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });

        etcod = (EditText) findViewById(R.id.et1);
        etdesc = (EditText) findViewById(R.id.et2);
        etpz = (EditText) findViewById(R.id.et3);

        btnbcod = (Button) findViewById(R.id.concod);
        btndes = (Button) findViewById(R.id.condes);
        btng = (Button) findViewById(R.id.btgrd);
        btnmod = (Button) findViewById(R.id.btnmod);
        btndell = (Button) findViewById(R.id.btdell);

        String signal = "";
        String codigo = "";
        String desc = "";
        String prezio = "";

        try {

            Bundle bun = getIntent().getExtras();
            if (bun != null){

               String a = (String) bun.get("codigo");
                String b = (String) bun.get("descr");
                String c = (String) bun.get("codigo");
                signal = bun.getString("pe");
                desc = bun.getString("de");
                prezio = bun.getString("pe");

                    etcod.setText(a);
                    etdesc.setText(b);
                    etpz.setText(c);

            }

        }catch (Exception o){
            //AQUI SAMPARE UN TOAST CUANDO ME ACUERDE SINO ME AVISAN
        }


    }


    private void comfirmacion(){
        String msm = "¿Quieres salir";
        wiuwiu = new AlertDialog.Builder(MainActivity.this);
        wiuwiu.setIcon(R.drawable.cancel);
        wiuwiu.setTitle("Advertencia");
        wiuwiu.setMessage(msm);
        wiuwiu.setCancelable(false);
        wiuwiu.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });

        wiuwiu.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        wiuwiu.show();

    }

    public void limpiardat(){

        etcod.setText(null);
        etdesc.setText(null);
        etpz.setText(null);
    }

    public void limpiardat2(View view){

        etcod.setText(null);
        etdesc.setText(null);
        etpz.setText(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_listaArt){
            Intent spinnerAct = new Intent(MainActivity.this,activity_consulta.class);
            startActivity(spinnerAct);
            return true;
        }else if (id == R.id.action_listaArt1){
            Intent listVAct = new Intent(MainActivity.this,listview_articulos.class);
            startActivity(listVAct);
            return  true;

        }else if (id == R.id.action_recylist){
            Intent listVAct = new Intent(MainActivity.this,consultarecy.class);
            startActivity(listVAct);
            return  true;

        }



        return super.onOptionsItemSelected(item);
    }

    public void guardar (View view){
        if (etcod.getText().toString().length()== 0){
            etcod.setError("Campo obligatorio");
            inputET = false;
        }else {
            inputET = true;
        }


        if (etdesc.getText().toString().length()==0){
            etdesc.setError("Campo obligatorio");
            inputED = false;
        }else {
            inputED = true;
        }


        if (etpz.getText().toString().length()== 0){
            etpz.setError("Campo obligatorio");
            input1 = false;
        }else{
            input1 = true;
        }


        if (inputET && inputED && input1){
            try {
                dato.setCodigo(Integer.parseInt(etcod.getText().toString()));
                dato.setDescripcion(etdesc.getText().toString());
                dato.setPrecio(Double.parseDouble(etpz.getText().toString()));

                if (conexion.InsertTradicional(dato)){
                    Toast.makeText(this,"Registro Agregado ",Toast.LENGTH_SHORT).show();
                    limpiardat();
                }else {
                    Toast.makeText(this,"Ya existe el registro"+etcod.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }catch (Exception o){

                Toast.makeText(this,"Hubo un Error el algo",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void mensaje(String msm){
        Toast.makeText(this," "+msm,Toast.LENGTH_SHORT).show();
    }

    public  void consultcod (View view){
        if (etcod.getText().toString().length()== 0){
            etcod.setError("Campo obligatorio");
            inputET = false;
        }else {
            inputET = true;
        }

        if (inputET){
            String codigo = etcod.getText().toString();
            dato.setCodigo(Integer.parseInt(codigo));

            if (conexion.consultArt(dato)){
                etdesc.setText(dato.getDescripcion());
                etpz.setText(""+dato.getPrecio());
            }else{

                Toast.makeText(this,"No existe el articulo ese",Toast.LENGTH_SHORT).show();
                limpiardat();
            }
        }else{
            Toast.makeText(this,"Ingrese el articulo por favor",Toast.LENGTH_SHORT).show();

        }

    }

    public  void consuldesc (View view){
        if (etdesc.getText().toString().length()== 0){
            etdesc.setError("Campo obligatorio");
            inputED = false;
        }else {
            inputED = true;
        }

        if (inputED){
            String desc = etdesc.getText().toString();
            dato.setDescripcion(desc);
            if (conexion.cosultDesc(dato)){
                etcod.setText(""+dato.getCodigo());
                etdesc.setText(dato.getDescripcion());
                etpz.setText(""+dato.getPrecio());

            }else {
                Toast.makeText(this,"No existe tal articulo",Toast.LENGTH_SHORT).show();
                limpiardat();

            }
        }else {
            Toast.makeText(this,"Ingrese el articulo por descripcion por favor",Toast.LENGTH_SHORT).show();
        }


    }

    public  void bajacod(View view){
        if (etcod.getText().toString().length()== 0){
            etcod.setError("Campo obligatorio");
            inputET = false;
        }else {
            inputET = true;
        }

        if (inputET){
            String codmw = etcod.getText().toString();
            dato.setCodigo(Integer.parseInt(codmw));
            if (conexion.delCod(MainActivity.this,dato)){
                limpiardat();

            }else {
                Toast.makeText(this,"Ingrese el articulo ",Toast.LENGTH_SHORT).show();
                limpiardat();
            }
        }

    }


    public void modi (View view){
        if (etcod.getText().toString().length()== 0){
            etcod.setError("Campo obligatorio");
            inputET = false;
        }else {
            inputET = true;
        }

        if (inputET){
            String cod = etcod.getText().toString();
            String desc = etdesc.getText().toString();
            double prezio = Double.parseDouble(etpz.getText().toString());

            dato.setCodigo(Integer.parseInt(cod));
            dato.setDescripcion(desc);
            dato.setPrecio(prezio);

            if (conexion.mod(dato)){
                Toast.makeText(this,"Editado",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"No se encotro el art a modificar el articulo ",Toast.LENGTH_SHORT).show();

            }
        }
    }
}