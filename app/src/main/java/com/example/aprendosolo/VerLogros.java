package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VerLogros extends AppCompatActivity {
    Button buttonVolver;
    TextView textViewMetros;
    ListView ListaLogros;

ManejadorBDLOGROS manejadorBDLOGROS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_logros);
        buttonVolver = findViewById(R.id.buttonVolver);
        textViewMetros = findViewById(R.id.textViewMetros);
        ListaLogros=findViewById(R.id.ListaLogros);
        manejadorBDLOGROS = new ManejadorBDLOGROS(VerLogros.this);
        mostrar(manejadorBDLOGROS);
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
    public void mostrar(ManejadorBDLOGROS manejadorBDLOGROS) {
        Cursor cursor = manejadorBDLOGROS.listar();

        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();

        if ((cursor != null) && (cursor.getCount() > 0)) {
            while (cursor.moveToNext()) {
                String fila = "";
                fila += "ID " + cursor.getString(0);
                fila += " FyH " + cursor.getString(1);
                fila += " PUNT: " + cursor.getString(2);
                list.add(fila);

            }

            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            ListaLogros.setAdapter(adapter);
            cursor.close();
        } else {
            Toast.makeText(VerLogros.this, "Nada que mostar", Toast.LENGTH_SHORT).show();
        }
    }
}
