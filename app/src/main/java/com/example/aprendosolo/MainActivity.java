package com.example.aprendosolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static String PRIMERO = "NOMBRE";
    private int MENU = 1;

    Button buttonEntrar, buttonGaleria, buttonFoto;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        buttonGaleria = findViewById(R.id.buttonGaleria);
        buttonFoto = findViewById(R.id.buttonFoto);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this, Menu.class);

                startActivityForResult(intento, MENU);
            }
        });
        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MENU && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(PRIMERO);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
    }
}
