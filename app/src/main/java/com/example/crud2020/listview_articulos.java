package com.example.crud2020;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.SearchView;

        import java.util.ArrayList;
        import java.util.List;

        import entidades.ConexionSQLite;
        import entidades.DTO;

public class listview_articulos extends AppCompatActivity {

    ListView listVpersonas;
    ArrayAdapter adaptadar;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;

    String[] ver = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo","GindgerBread","HoneyComb","IceCream Sandwich","Jelly Bean","Kitkat","Lollipop","Marshmallow","Nogaut","Oreo"};

    ConexionSQLite conexion = new ConexionSQLite(this);
    DTO datos = new DTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_articulos);

        listVpersonas = (ListView) findViewById(R.id.lsview);
        searchView = (SearchView) findViewById(R.id.buscador);

        adaptadar = new ArrayAdapter(this, android.R.layout.simple_list_item_1,conexion.consultarListaArticulos1());
        listVpersonas.setAdapter(adaptadar);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                adaptadar.getFilter().filter(text);
                return false;
            }
        });

        listVpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Codigo: "+conexion.consultarListaArticulos().get(i).getDescripcion()+"\n";
                informacion+="Precio: "+conexion.consultarListaArticulos().get(i).getPrecio();

                DTO arti = conexion.consultarListaArticulos().get(i);
                Intent intent = new Intent(listview_articulos.this,activity_detalle_articulos.class);
                Bundle bun = new Bundle();
                bun.putSerializable("articulo",arti);
                intent.putExtras(bun);
                startActivity(intent);

            }
        });
    }

    public  void  vol (View view){
        onBackPressed();
    }
}