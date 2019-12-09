package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AnadirPreguntas extends AppCompatActivity {
    Button buttonAnadir, buttonVolver;
    EditText editTextID, editTextCorrecta, editTextIncorrecta1, editTextIncorrecta2, editTextPregunta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_preguntas);
        buttonAnadir = findViewById(R.id.buttonAnadirRow);
        buttonVolver = findViewById(R.id.buttonVolver2);
        editTextID = findViewById(R.id.editTextID);
        editTextCorrecta = findViewById(R.id.editTextRespuestaCorrecta);
        editTextIncorrecta1 = findViewById(R.id.editTextIncorrecta1);
        editTextIncorrecta2 = findViewById(R.id.editTextIncorrecta2);
        editTextPregunta = findViewById(R.id.editTextPregunta);

        buttonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Menu.NOMBRE, "Regreso de añadir");
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
