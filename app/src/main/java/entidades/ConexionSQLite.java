package entidades;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import  java.text.SimpleDateFormat;
import  java.util.ArrayList;
import  java.util.Calendar;
import java.util.List;
import com.example.crud2020.R;


public class ConexionSQLite extends SQLiteOpenHelper {

    boolean statusdel = true;

    ArrayList<String> listaArticulos;
    //informacion que se representara en la vista de lista
    ArrayList<DTO> articuloslist;
    //Entidad que representara los datos de la tabla articulos
    boolean estadoDell = true;

    public ConexionSQLite(Context context) {
        super(context, "administracion.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table articulos(codigo integer not null primary key," +
                "descripcion text, precio real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists articulos");
        onCreate(db);
    }

    public SQLiteDatabase bd() {
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd;

    }

    public boolean InsertTradicional(DTO datos){
        boolean status = true;
        int result;
        try {
            int cod = datos.getCodigo();
            String desc = datos.getDescripcion();
            double prezio = datos.getPrecio();

            Cursor fila = bd().rawQuery("select codigo from articulos where codigo= '"+cod+"'",null);

            if (fila.moveToFirst()==true){

                status = false;
            }else {

                String SQL = "INSERT INTO articulos \n "+"(codigo,descripcion,precio)\n"+"VALUES\n"+"('"+String.valueOf(cod)+"','"+desc+"','"+String.valueOf(prezio)+"');";
                bd().execSQL(SQL);
                bd().close();

                status = true;

            }

        }catch (Exception esta){
            status = false;
            Log.e("Error de vinculo SQL",esta.toString());

        }

        return  status;
    }

    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

    public  boolean insertDatos(DTO datos){
        boolean estadounido = true;
        int resultado;

        ContentValues reg = new ContentValues();

        try {
            reg.put("codigo",datos.codigo);
            reg.put("descripcion",datos.descripcion);
            reg.put("precio",datos.precio);

            Cursor fila = bd().rawQuery("select codigo from articulos where  codigo='"+datos.getCodigo()+"'",null );

            if (fila.moveToFirst()== true){

                estadounido = false;

            }else {

                resultado = (int)bd().insert("articulos",null,reg);
                if (resultado > 0) estadounido = true;
                else estadounido = false;
            }

        }catch (Exception ex){
            estadounido = false;
            Log.e("Error de Insertar datos",ex.toString());
        }
        return  estadounido;
    }


    public  boolean  InsertReg(DTO datos){

        boolean estado = true ;
        int resultado;

        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdfshow = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String fechaone = sdfshow.format(cal.getTime());

            Cursor fila = bd().rawQuery("select codigo from articulos where  codigo='"+datos.getCodigo()+"'",null );

            if (fila.moveToFirst()== true){

                estado = false;

            }else {

                String SQLAZO = "INSERT INTO articulos \n"+"(codigo,descripcion,precio)\n"+"VALUES \n"+"(?,?,?);";
                bd().execSQL(SQLAZO, new String[]{String.valueOf(codigo),descripcion,String.valueOf(precio)});

                estado = true;
            }

        }catch (Exception e){
            estado = false;
            Log.e("error en inser registro",e.toString());
        }

        return estado;
    }

    public boolean consultArt(DTO dat){

        boolean estado = true;
        int results;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String[]    parametros = {String.valueOf(dat.getCodigo())};
            String[]    campos = {"codigo","descripcion","precio"};

            Cursor fila = db.query("articulos",campos,"codigo=?",parametros,null,null,null);

            if (fila.moveToFirst()){

                dat.setCodigo(Integer.parseInt(fila.getString(0)));
                dat.setDescripcion(fila.getString(1));
                dat.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;

            }else {

                estado = false;
            }
            fila.close();
            db.close();



        }catch (Exception e){
            estado = false;
            Log.e("error en cons art",e.toString());
        }

        return estado;

    }

    public boolean cosultCod(DTO dat){

        boolean estado = true;
        int results;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int cod = dat.getCodigo();

            Cursor fila = db.rawQuery("select codigo, descripcion, precio from articulos where codigo="+cod,null);

            if (fila.moveToFirst()){

                dat.setCodigo(Integer.parseInt(fila.getString(0)));
                dat.setDescripcion(fila.getString(1));
                dat.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;

            }else {

                estado = false;
            }
            db.close();


        }catch (Exception e){
            estado = false;
            Log.e("error en cons cod",e.toString());
        }

        return estado;

    }

    public  boolean cosultDesc(DTO datos){

        boolean estatus = true;
        int resulto;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String descrp = datos.getDescripcion();
            Cursor fila = db.rawQuery("select * from articulos where descripcion='"+descrp+"'",null);

            if (fila.moveToFirst()){

                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estatus = true;

            }else {

                estatus = false;
            }
            db.close();


        }catch (Exception e){
            estatus = false;
            Log.e("error en cons desc",e.toString());
        }

        return estatus;

    }


    public  boolean delCod(final Context context, final  DTO datos){

        estadoDell = true;

        try {

            int cod = datos.getCodigo();
            Cursor fila = bd().rawQuery("select * from articulos where codigo="+cod,null);

            if (fila.moveToFirst()){

                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));

                AlertDialog.Builder build = new AlertDialog.Builder(context);
                build.setIcon(R.drawable.cancel);
                build.setTitle("eRRoR");
                build.setMessage("Esta seguro de eliminar este dato?\nCódigo:"+ datos.getCodigo()+"\nDescripcion: "+datos.getDescripcion());
                build.setCancelable(false);
                build.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = datos.getCodigo();
                        int cant = bd().delete("articulos","codigo="+codigo,null);

                        if (cant > 0 ){

                            estadoDell = true;
                            Toast.makeText(context,"Registro borrado :') ",Toast.LENGTH_SHORT).show();

                        }else {

                            estadoDell = false;
                        }
                        bd().close();

                    }
                });

                build.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = build.create();
                dialog.show();
            }

        }catch (Exception thiss){

            estadoDell = false;
            Log.e("Error",thiss.toString());

        }

        return estadoDell;
    }


    public boolean mod(DTO datos){

        boolean estado = true;
        int result;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int code = datos.getCodigo();
            String desc = datos.getDescripcion();
            double precio = datos.getPrecio();

            ContentValues reg = new ContentValues();
            reg.put("codigo",code);
            reg.put("descripcion",desc);
            reg.put("precio",precio);

            int cont = (int) db.update("articulos",reg,"codigo="+code,null);

            db.close();
            if (cont>0) estado = true;
            else estado = false;
        }catch (Exception e){
            Log.e("error en modif",e.toString());
        }


        return  estado;

    }

    public ArrayList<DTO> consultarListaArticulos() {
        boolean estado = false;

        SQLiteDatabase db = this.getReadableDatabase();

        DTO articulos = null;
        articuloslist = new ArrayList<DTO>();

        try {
            Cursor fila = db.rawQuery("select * from articulos", null);
            while (fila.moveToNext()) {
                articulos = new DTO();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));

                articuloslist.add(articulos);
                Log.i("codigo", String.valueOf(articulos.getCodigo()));
                Log.i("codigo", articulos.getDescripcion().toString());
                Log.i("precio", String.valueOf(articulos.getPrecio()));
            }
            obtenerListaArticulos();
        } catch (Exception e) {

        }
        return articuloslist;

    }

    public ArrayList<String> consultarListaArticulos1() {
        boolean estado = false;

        SQLiteDatabase db = this.getReadableDatabase();

        DTO articulos = null;
        articuloslist = new ArrayList<DTO>();

        try {
            Cursor fila = db.rawQuery("select * from articulos", null);
            while (fila.moveToNext()) {
                articulos = new DTO();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));

                articuloslist.add(articulos);

            }

            listaArticulos = new ArrayList<String>();

            for (int i = 0;i < articuloslist.size() ; i++){

                listaArticulos.add(articuloslist.get(i).getCodigo()+" >> "+articuloslist.get(i).getDescripcion());
            }

        } catch (Exception e) {

        }
        return listaArticulos;

    }
    public ArrayList<String> obtenerListaArticulos () {
        listaArticulos = new ArrayList<String>();
        listaArticulos.add("Seleccione");

        for (int i = 0; i < articuloslist.size(); i++) {
            listaArticulos.add(articuloslist.get(i).getCodigo() + " >> " + articuloslist.get(i).getDescripcion());
        }
        return listaArticulos;
    }

    //Array agregado

    public List<DTO> mostrarArticulos(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curso  = db.rawQuery("SELECT * FROM articulos order by codigo desc",null);
        List<DTO> articulos = new ArrayList<>();
        if (curso.moveToFirst())
        {
            do {
                articulos.add(new DTO (curso.getInt(0),curso.getString(1),curso.getDouble(2)));
            }while (curso.moveToNext());
        }
        return articulos;
    }




}
