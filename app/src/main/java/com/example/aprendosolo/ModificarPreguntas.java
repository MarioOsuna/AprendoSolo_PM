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

public class ModificarPreguntas extends AppCompatActivity {
    Button buttonVolver, buttonModificar;
    EditText editTextID, editTextRespuesta, editTextPregunta, editTextIncorrecta1, editTextIncorrecta2;
    ListView listViewModificar;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_preguntas);
        buttonVolver = findViewById(R.id.buttonVolver4);
        buttonModificar = findViewById(R.id.buttonModificarPreg);
        editTextID = findViewById(R.id.editTextIDModificar);
        editTextRespuesta = findViewById(R.id.editTextRespuestaModificar);
        editTextPregunta = findViewById(R.id.editTextPreguntaModificar);
        editTextIncorrecta1 = findViewById(R.id.editTextIncorrectaMod);
        editTextIncorrecta2 = findViewById(R.id.editTextIncorrectaMod2);
        listViewModificar = findViewById(R.id.ListaModificar);
        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(ModificarPreguntas.this);

        mostrar(manejadorBDPREGUNTAS);


        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Menu.NOMBRE, "Regreso de modificar");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejadorBDPREGUNTAS.actualizar(editTextID.getText().toString(), editTextPregunta.getText().toString(), editTextRespuesta.getText().toString(), editTextIncorrecta1.getText().toString(), editTextIncorrecta2.getText().toString());

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
            listViewModificar.setAdapter(adapter);
            cursor.close();
        } else {
            Toast.makeText(ModificarPreguntas.this, "Nada que mostar", Toast.LENGTH_SHORT).show();
        }
    }
}
