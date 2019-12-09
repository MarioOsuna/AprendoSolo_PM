package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ModificarPreguntas extends AppCompatActivity {
    Button buttonVolver, buttonModificar;
    EditText editTextID,editTextRespuesta,editTextPregunta,editTextIncorrecta1,editTextIncorrecta2;
    ListView listViewModificar;
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
        listViewModificar=findViewById(R.id.ListaModificar);



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

            }
        });
    }
}
