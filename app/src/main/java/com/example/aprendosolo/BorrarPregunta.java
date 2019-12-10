package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BorrarPregunta extends AppCompatActivity {
    Button buttonBorrar, buttonVolver;
    EditText editTextIDBorrar;
    ListView listViewBorrar;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_pregunta);
        buttonBorrar = findViewById(R.id.buttonBorrarPreg);
        buttonVolver = findViewById(R.id.buttonVolver3);
        editTextIDBorrar = findViewById(R.id.editTextIDBorrar);
        listViewBorrar = findViewById(R.id.ListBorrar);
        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(BorrarPregunta.this);
        mostrar(manejadorBDPREGUNTAS);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Menu.NOMBRE, "Regreso de Borrar");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean resultado = manejadorBDPREGUNTAS.borrar(editTextIDBorrar.getText().toString());

                if (resultado) {
                    Toast.makeText(BorrarPregunta.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    mostrar(manejadorBDPREGUNTAS);
                } else {
                    Toast.makeText(BorrarPregunta.this, "Nada fue borrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void mostrar(ManejadorBDPREGUNTAS manejadorBDPREGUNTAS) {
        Cursor cursor = manejadorBDPREGUNTAS.listar();

        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();

        if ((cursor != null) && (cursor.getCount() > 0)) {
            while (cursor.moveToNext()) {
                String fila = "";
                fila += "ID: " + cursor.getString(0);
                fila += " PREGUNTA: " + cursor.getString(1);
                fila += " RESPUESTA: " + cursor.getString(2);
                fila += " R_INC 1: " + cursor.getString(3);
                fila += " R_INC 2: " + cursor.getString(4);
                list.add(fila);

            }

            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            listViewBorrar.setAdapter(adapter);
            cursor.close();
        } else {
            Toast.makeText(BorrarPregunta.this, "Nada que mostar", Toast.LENGTH_SHORT).show();
        }
    }
}
