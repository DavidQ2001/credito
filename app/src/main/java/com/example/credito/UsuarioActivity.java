package com.example.credito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {

    EditText jetidentificacion, jetnombre, jetprofesion, jetempresa, jetsalario, jetextras, jetgastos;
    CheckBox jcbactivo;

    ClsOpenHelper admin=new ClsOpenHelper(this,"Banco.bd",null,1);

    String identificacion, nombre, profesion, empresa, salario, extras, gastos;
    Long resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        //ocultar nombre app y asociar objetos
        getSupportActionBar().hide();
        jetidentificacion=findViewById(R.id.etidentificacion);
        jetnombre=findViewById(R.id.etnombre);
        jetprofesion=findViewById(R.id.etprofesion);
        jetempresa=findViewById(R.id.etempresa);
        jetsalario=findViewById(R.id.etsalario);
        jetextras=findViewById(R.id.etextras);
        jetgastos=findViewById(R.id.etgastos);
        jcbactivo=findViewById(R.id.cbactivo);
    }
    public void Guardar(View view){
        identificacion=jetidentificacion.getText().toString();
        nombre=jetnombre.getText().toString();
        profesion=jetprofesion.getText().toString();
        empresa=jetempresa.getText().toString();
        salario=jetsalario.getText().toString();
        extras=jetextras.getText().toString();
        gastos=jetgastos.getText().toString();
        if (identificacion.isEmpty()|| nombre.isEmpty() || profesion.isEmpty() || empresa.isEmpty() || salario.isEmpty() || extras.isEmpty() || gastos.isEmpty()){
            Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else {
            SQLiteDatabase fila=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("identificacion",identificacion);
            registro.put("nombre",nombre);
            registro.put("profesion",profesion);
            registro.put("empresa",empresa);
            registro.put("salario",Integer.parseInt(salario));
            registro.put("ingreso_extra",Integer.parseInt(extras));
            registro.put("gastos",gastos);
            resp=fila.insert("TblCliente",null,registro);
            if (resp==0){
                Toast.makeText(this, "Erro guardando registro", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            fila.close();
        }
    }

    public void Consultar(View view){
        identificacion = jetidentificacion.getText().toString();
        if(identificacion.isEmpty()){
            Toast.makeText(this, "Identificacion es requerida por la consulta", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();

        }
        else{
            SQLiteDatabase fila = admin.getWritableDatabase();
            Cursor dato = fila.rawQuery("select * from TblCliente where identificacion= '" + identificacion + "'",null);
            if(dato.moveToNext()){
                jetnombre.setText(dato.getString( 1));
                jetprofesion.setText(dato.getString( 2));
                jetempresa.setText(dato.getString( 3));
                jetsalario.setText(dato.getString( 4));
                jetextras.setText(dato.getString( 5));
                jetgastos.setText(dato.getString( 6));

                if(dato.getString(7).equals("si"))
                    jcbactivo.setChecked(true);

                    else
                        jcbactivo.setChecked(false);





            }
        }
    }
    private void Limpiar_campos(){
        jetidentificacion.setText("");
        jetnombre.setText("");
        jetprofesion.setText("");
        jetempresa.setText("");
        jetsalario.setText("");
        jetextras.setText("");
        jetgastos.setText("");
        jcbactivo.setText("");
    }
}