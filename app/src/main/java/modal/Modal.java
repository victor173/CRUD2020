package modal;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud2020.MainActivity;
import com.example.crud2020.R;

import entidades.ConexionSQLite;
import entidades.DTO;

public class Modal {
    Dialog midialogo;
    AlertDialog.Builder diag;
    boolean validarInput = false;
    String codigo, descr, precio;
    SQLiteDatabase db = null;
    DTO datos = new DTO();

    public void  Search(final Context context){
        midialogo = new Dialog(context);
        midialogo.setContentView(R.layout.ventana1);
        midialogo.setCancelable(false);
        final ConexionSQLite conexion = new ConexionSQLite(context);
        final EditText etcodfinal = (EditText)midialogo.findViewById(R.id.edbusc);

        Button btbusc = (Button)midialogo.findViewById(R.id.busc);
        ImageButton cancel = (ImageButton)midialogo.findViewById(R.id.cancelbusc);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                midialogo.dismiss();
            }
        });
        btbusc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etcodfinal.getText().toString().length()== 0){
                    validarInput = false;
                    etcodfinal.setError("Campo obligatorio");
                }else {
                    validarInput = true;
                }

                if (validarInput){
                    String cod = etcodfinal.getText().toString();
                    datos.setCodigo(Integer.parseInt(cod));
                    if (conexion.cosultCod(datos) == true){
                        codigo = String.valueOf(datos.getCodigo());
                        descr = datos.getDescripcion();
                        precio = String.valueOf(datos.getPrecio());

                        Intent ee = new Intent(context, MainActivity.class);
                        ee.putExtra("senale","1");
                        ee.putExtra("codigo",codigo);
                        ee.putExtra("descr","descr");
                        ee.putExtra("pe",precio);


                        Toast.makeText(context,"Enviado",Toast.LENGTH_SHORT).show();

                        midialogo.dismiss();




                    }else {
                        Toast.makeText(context,"No se encuentran resultados",Toast.LENGTH_SHORT).show();
                    }
                }else {

                    Toast.makeText(context,"No has escrito nara bro",Toast.LENGTH_SHORT).show();
                }
            }
        });


        midialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        midialogo.show();
    }

}

