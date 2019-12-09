package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class BorrarPregunta extends AppCompatActivity {
    Button buttonBorrar, buttonVolver;
    EditText editTextIDBorrar;
    ListView listViewBorrar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_pregunta);
        buttonBorrar = findViewById(R.id.buttonBorrarPreg);
        buttonVolver = findViewById(R.id.buttonVolver3);
        editTextIDBorrar = findViewById(R.id.editTextIDBorrar);
        listViewBorrar = findViewById(R.id.ListBorrar);

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

            }
        });

    }
}
