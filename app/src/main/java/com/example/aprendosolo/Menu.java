package com.example.aprendosolo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    static String NOMBRE = "NOMBRE";
    private int ANADIR = 1;
    private int BORRAR = 2;
    private int MODIFICAR = 3;
    private int APRENDER = 4;
    private int LOGROS = 5;
    Button buttonCerrar, buttonAnadir, buttonBorrar, buttonModificar, buttonAprender, buttonSonido, buttonVLogros, buttonBLogros, buttonCompartir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonCerrar = findViewById(R.id.buttonCerrar);
        buttonAnadir = findViewById(R.id.buttonAnadir);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonAprender = findViewById(R.id.buttonAprender);
        buttonSonido = findViewById(R.id.buttonSonido);
        buttonVLogros = findViewById(R.id.buttonVLogros);
        buttonBLogros = findViewById(R.id.buttonBLogros);
        buttonCompartir = findViewById(R.id.buttonCompartir);

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.PRIMERO, "Sesión cerrada");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Menu.this, AnadirPreguntas.class);

                startActivityForResult(intento, ANADIR);

            }
        });
        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Menu.this, BorrarPregunta.class);

                startActivityForResult(intento, BORRAR);

            }
        });
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Menu.this, ModificarPreguntas.class);

                startActivityForResult(intento, MODIFICAR);
            }
        });
        buttonAprender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Menu.this, Aprender.class);

                startActivityForResult(intento, APRENDER);
            }
        });
        buttonSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonVLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Menu.this, VerLogros.class);

                startActivityForResult(intento, LOGROS);

            }
        });
        buttonBLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intentoEmail=new Intent(Intent.ACTION_SEND);

                intentoEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{editTextEmail.getText().toString()});
                intentoEmail.putExtra(Intent.EXTRA_SUBJECT,editTextAsunto.getText().toString());
                intentoEmail.putExtra(Intent.EXTRA_TEXT,editTextcompartir.getText().toString());
                intentoEmail.setType("message/rfc822");

                startActivity(Intent.createChooser(intentoEmail,"Escoge tu app de email favorito"));*/

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ANADIR && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(NOMBRE);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == BORRAR && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(NOMBRE);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == MODIFICAR && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(NOMBRE);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == APRENDER && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(NOMBRE);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == LOGROS && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(NOMBRE);
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }

    }
}
