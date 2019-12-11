package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Aprender extends AppCompatActivity {
    /*  RadioButton radioButton;
      RadioGroup radioGroup;*/
    GridLayout g;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;
    int n, i;
    String[] filaPreg, filaRes, filaInco, filaInco2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);
        g = findViewById(R.id.gridLayout);
        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(Aprender.this);
        n = total();
        if (n != 0) {
            final EditText editText[] = new EditText[n];
            final EditText editText2[] = new EditText[n];
            final RadioGroup radioGroup[] = new RadioGroup[n];
            final RadioButton radioButtonCorrecto[] = new RadioButton[n];
            final RadioButton radioButton2[] = new RadioButton[n];
            final RadioButton radioButton3[] = new RadioButton[n];
            final Button button[]=new Button[n];


                editText[i] = new EditText(Aprender.this);
                editText[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                editText[i].setHint("Pregunta " + (i + 1));
                editText[i].setInputType(InputType.TYPE_CLASS_TEXT);
                editText[i].setId(View.generateViewId());

                g.addView(editText[i]);

                mostrar(manejadorBDPREGUNTAS, n);
                String pregunta = filaPreg[i];
                String respuesta = filaRes[i];
                String incorrecta1 = filaInco[i];
                String incorrecta2 = filaInco2[i];

                editText2[i] = new EditText(Aprender.this);
                editText2[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                editText2[i].setHint((i + 1) + ": " + pregunta);
                editText2[i].setInputType(InputType.TYPE_CLASS_TEXT);
                editText2[i].setId(View.generateViewId());

                g.addView(editText2[i]);

                radioGroup[i] = new RadioGroup(Aprender.this);
                radioGroup[i].setOrientation(LinearLayout.HORIZONTAL);
                radioGroup[i].setId(View.generateViewId());

                for (int i = 1; i <= n; i++) {
                    RadioButton rdbtn = new RadioButton(this);
                    rdbtn.setId(View.generateViewId());
                    rdbtn.setText("Radio " + rdbtn.getId());
                    radioGroup[i].addView(rdbtn);
                }
                g.addView(radioGroup[i]);

                button[i] = new Button(Aprender.this);
                button[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button[i].setText("Enviar");
                button[i].setId(View.generateViewId());

                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g.removeAllViewsInLayout();

                    }
                });
                g.addView(button[i]);
                i++;
                // ((ViewGroup) findViewById(R.id.radioGroup[i])).addView(radioGroup[i]);


        }


    }

    public int total() {
        Cursor cursor = manejadorBDPREGUNTAS.listar();
        int filas = 0;

        if ((cursor != null) && (cursor.getCount() > 0)) {

            filas = cursor.getCount();
            cursor.close();

        } else {
            Toast.makeText(Aprender.this, "No hay preguntas introducidas", Toast.LENGTH_SHORT).show();
        }
        return filas;
    }

    public void mostrar(ManejadorBDPREGUNTAS manejadorBDPREGUNTAS, int n) {
        Cursor cursor = manejadorBDPREGUNTAS.listar();
        filaPreg = new String[n];
        filaRes = new String[n];
        filaInco = new String[n];
        filaInco2 = new String[n];
        int i = 0;
        while (cursor.moveToNext()) {

            cursor.getString(0);
            filaPreg[i] = cursor.getString(1);
            filaRes[i] = cursor.getString(2);
            filaInco[i] = cursor.getString(3);
            filaInco2[i] = cursor.getString(4);
            i++;

        }

        cursor.close();

    }


}
