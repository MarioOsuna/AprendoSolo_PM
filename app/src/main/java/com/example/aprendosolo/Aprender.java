package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Aprender extends AppCompatActivity {
    RadioButton radioButton;

    GridLayout g;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;
    int n;
    Button button;
    String[] filaPreg, filaRes, filaInco, filaInco2;

    int puntos = 0;
    private boolean correcta;
    private boolean siguiente;
    private int acertadas = 0;
     int preg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        g = findViewById(R.id.gridLayout);
        button = findViewById(R.id.buttonEnviarRespuesta);
        int preg=0;

        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(Aprender.this);
        n = total();
        //obtengo preguntas
        if (n != 0) {
            crear(preg);
            /*Cursor cursor = manejadorBDPREGUNTAS.listar();
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
           final TextView[] textView = new TextView[n];
            final RadioGroup[] radioGroup = new RadioGroup[n];
           // g.setRowCount(n *);

            for (int preg = 0; preg < n; preg++) {

                textView[preg] = new TextView(Aprender.this);
                textView[preg].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView[preg].setText(filaPreg[preg]);
                textView[preg].setInputType(InputType.TYPE_CLASS_TEXT);
                textView[preg].setId(View.generateViewId());

                g.addView(textView[preg]);

                radioGroup[preg] = new RadioGroup(Aprender.this);
                radioGroup[preg].setOrientation(LinearLayout.VERTICAL);
                radioGroup[preg].setId(View.generateViewId());

                int num1;
                int num2 = 1;
                int num3 = 1;

                int numero = (int) (Math.random() * 3) + 1;
                num1 = numero;
                for (int n = 0; n < 3; n++) {

                    if (num1 == num2) {
                        numero = (int) (Math.random() * 3) + 1;
                        num2 = numero;
                        n--;
                    }
                    if (num3 == num2) {
                        numero = (int) (Math.random() * 3) + 1;
                        num3 = numero;
                        n--;
                    }
                    if (num3 == num1) {
                        numero = (int) (Math.random() * 3) + 1;
                        num1 = numero;
                        n--;
                    }
                    if (num1 != num2 && num3 != num2 && num3 != num1) {
                        n = 3;
                    }

                }
                int respuesta = 0;
                for (int j = 1; j <= 3; j++) {
                    final RadioButton rdbtn = new RadioButton(this);

                    rdbtn.setId(((j)));
                    if (j == num1) {
                        rdbtn.setId(((j)));
                        rdbtn.setText(" " + rdbtn.getId() + " " + filaRes[preg]);
                        respuesta = j;
                    }
                    if (j == num2) {
                        rdbtn.setId(((j)));
                        rdbtn.setText(" " + rdbtn.getId() + " " + filaInco[preg]);
                    }
                    if (j == num3) {
                        rdbtn.setId(((j)));
                        rdbtn.setText(" " + rdbtn.getId() + " " + filaInco2[preg]);
                    }
                    final int finalRespuesta = respuesta;
                    rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (finalRespuesta == rdbtn.getId()) {
                                correcta = true;
                            } else {
                                correcta = false;
                            }
                        }
                    });

                    radioGroup[preg].addView(rdbtn);
                }
                g.addView(radioGroup[preg]);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         siguiente = true;
                         acertadas++;
                        Toast.makeText(Aprender.this, "Es " + correcta, Toast.LENGTH_SHORT).show();


                    }
                });
                }
*/


            Toast.makeText(this, "Acertadas " + acertadas, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se han introducido preguntas", Toast.LENGTH_SHORT).show();
        }


    }

    public void crear(int preg) {
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
        final TextView[] textView = new TextView[n];
        final RadioGroup[] radioGroup = new RadioGroup[n];
        // g.setRowCount(n *);

        if( preg >= 0 && preg < n) {

            textView[preg] = new TextView(Aprender.this);
            textView[preg].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView[preg].setText(filaPreg[preg]);
            textView[preg].setInputType(InputType.TYPE_CLASS_TEXT);
            textView[preg].setId(View.generateViewId());

            g.addView(textView[preg]);

            radioGroup[preg] = new RadioGroup(Aprender.this);
            radioGroup[preg].setOrientation(LinearLayout.VERTICAL);
            radioGroup[preg].setId(View.generateViewId());

            int num1;
            int num2 = 1;
            int num3 = 1;

            int numero = (int) (Math.random() * 3) + 1;
            num1 = numero;
            for (int n = 0; n < 3; n++) {

                if (num1 == num2) {
                    numero = (int) (Math.random() * 3) + 1;
                    num2 = numero;
                    n--;
                }
                if (num3 == num2) {
                    numero = (int) (Math.random() * 3) + 1;
                    num3 = numero;
                    n--;
                }
                if (num3 == num1) {
                    numero = (int) (Math.random() * 3) + 1;
                    num1 = numero;
                    n--;
                }
                if (num1 != num2 && num3 != num2 && num3 != num1) {
                    n = 3;
                }

            }
            int respuesta = 0;
            for (int j = 1; j <= 3; j++) {
                final RadioButton rdbtn = new RadioButton(this);

                rdbtn.setId(((j)));
                if (j == num1) {
                    rdbtn.setId(((j)));
                    rdbtn.setText(" " + rdbtn.getId() + " " + filaRes[preg]);
                    respuesta = j;
                }
                if (j == num2) {
                    rdbtn.setId(((j)));
                    rdbtn.setText(" " + rdbtn.getId() + " " + filaInco[preg]);
                }
                if (j == num3) {
                    rdbtn.setId(((j)));
                    rdbtn.setText(" " + rdbtn.getId() + " " + filaInco2[preg]);
                }
                final int finalRespuesta = respuesta;
                rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (finalRespuesta == rdbtn.getId()) {
                            correcta = true;
                        } else {
                            correcta = false;
                        }
                    }
                });

                radioGroup[preg].addView(rdbtn);
            }
            g.addView(radioGroup[preg]);

        final int fpreg=preg;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    siguiente = true;
                    acertadas++;
                    crear(fpreg);

                    Toast.makeText(Aprender.this, "Es " + correcta, Toast.LENGTH_SHORT).show();


                }
            });
            if(siguiente){
                preg++;
            }
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


}
