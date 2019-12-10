package com.example.aprendosolo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Aprender extends AppCompatActivity {
    RadioButton radioButton;
    RadioGroup radioGroup;
    GridLayout gridLayout;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);
        gridLayout = findViewById(R.id.gridLayout);
        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(Aprender.this);

    }
}
