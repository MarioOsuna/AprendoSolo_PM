package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class VerLogros extends AppCompatActivity {
    Button buttonVolver;
    TextView textViewMetros;
    ListView ListaLogros;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_logros);
        buttonVolver = findViewById(R.id.buttonVolver);
        textViewMetros = findViewById(R.id.textViewMetros);
        ListaLogros=findViewById(R.id.ListaLogros);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Menu.NOMBRE, "Regreso de Ver logros");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
