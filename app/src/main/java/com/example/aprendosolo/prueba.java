package com.example.aprendosolo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class prueba extends AppCompatActivity {

    private static final int PETICION_CAMARA = 1;
    private static final int VENGO_DE_LA_CAMARA_CON_FICHERO = 2;
    Button buttonHaceFoto, buttonAltaResolucion;
    ImageView imageView;
    File fichero = null;
    private static final int PEDI_PERMISO_DE_ESCRITURA = 3;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PETICION_CAMARA && resultCode == RESULT_OK) {

            Bitmap foto = (Bitmap) data.getExtras().get("data"); //es una imagen previa a baja resolución
            imageView.setImageBitmap(foto);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));

        }else if (requestCode == VENGO_DE_LA_CAMARA_CON_FICHERO && resultCode == RESULT_OK){
            imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        buttonHaceFoto = findViewById(R.id.buttonHacerFoto);
        buttonAltaResolucion = findViewById(R.id.buttonAltaResolución);

        imageView = findViewById(R.id.imageView);


        buttonAltaResolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermisoParaEscribirYhacerFoto();
            }
        });


        buttonHaceFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PETICION_CAMARA);
                } else {
                    Toast.makeText(prueba.this, "No tengo cámara", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    private File crearFicheroImagen() throws IOException {

        String fechaYHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreFichero = "misFotos_" + fechaYHora;
        File carpetaDeFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreFichero, ".jpg", carpetaDeFotos);
        return imagen;

    }

    void pedirPermisoParaEscribirYhacerFoto() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PEDI_PERMISO_DE_ESCRITURA);
            }
        }else{
            capturarFoto();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PEDI_PERMISO_DE_ESCRITURA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    capturarFoto();
                } else {
                    Toast.makeText(this, "Sin permiso de escritura no puedo hacer foto en alta resolución.", Toast.LENGTH_LONG).show();
                }
                break;
            }


        }


    }

    private void capturarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        try {
            fichero = crearFicheroImagen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fichero));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, VENGO_DE_LA_CAMARA_CON_FICHERO);
        } else {
            Toast.makeText(this, "No tengo programa para hacer fotos.", Toast.LENGTH_SHORT).show();
        }

    }
}
