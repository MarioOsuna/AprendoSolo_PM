package com.example.aprendosolo;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    static String PRIMERO = "NOMBRE";
    private int MENU = 1;
    static String CONTRASENA = "CONTRASEÑA";
    Button buttonEntrar, buttonGaleria, buttonFoto;
    EditText editTextPassword;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    Uri selectedImageUri = null;
    Uri selectedImage;
    private static final int PETICION_CAMARA = 1;
    private static final int VENGO_DE_LA_CAMARA_CON_FICHERO = 2;
    private static final int PEDI_PERMISO_DE_ESCRITURA = 3;
    int PEDIR_PERMISO_GPS = 1;
    File fichero = null;
    boolean primero = true;
    ImageView imageView;
    ManejadorBDENTRADAS manejadorBDENTRADAS;
    LocationManager locationManager;
    LocationListener locationListener;
    int tiempoRefresco = 500;
    private double Longitud = 0;
    private double Latitud = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        buttonGaleria = findViewById(R.id.buttonGaleria);
        buttonFoto = findViewById(R.id.buttonFoto);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageView = findViewById(R.id.imageView);

        manejadorBDENTRADAS = new ManejadorBDENTRADAS(MainActivity.this);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Introduzca una contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    if (editTextPassword.getText().toString().equals(sharedPreferences.getString(CONTRASENA, " "))) {
                        primero = false;
                        Log.i("Contraseña", " contraseña igual");
                        Intent intento = new Intent(MainActivity.this, Menu.class);
                        startActivityForResult(intento, MENU);
                    } else {
                        if (sharedPreferences.getString(CONTRASENA, " ").equals(" ")) {
                            primero = true;
                            Log.i("Contraseña", "guardar datos");
                            saveData();
                            Intent intento = new Intent(MainActivity.this, Menu.class);
                            startActivityForResult(intento, MENU);
                        } else {
                            primero = false;
                            Toast.makeText(MainActivity.this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(MainActivity.this, "La contraseña que tiene es " + sharedPreferences.getString(CONTRASENA, " ")+" y ha introducido "+editTextPassword.getText().toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                }

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
               /* selectedImage = imageReturnedIntent.getData();
                String selectedPath=selectedImage.getPath();*/
                pedirPermisoParaEscribirYhacerFoto();

            }
        });
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Latitud = location.getLatitude();
                Longitud = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PEDIR_PERMISO_GPS);


            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tiempoRefresco, 0, locationListener);

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CONTRASENA, editTextPassword.getText().toString());
        editor.apply();

    }

    void pedirPermisoParaEscribirYhacerFoto() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PEDI_PERMISO_DE_ESCRITURA);
            }
        } else {
            capturarFoto();
        }

    }

    private File crearFicheroImagen() throws IOException {

        String fechaYHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreFichero = "misFotos_" + fechaYHora;
        File carpetaDeFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreFichero, ".jpg", carpetaDeFotos);
        return imagen;

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
        if (requestCode == PEDIR_PERMISO_GPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tiempoRefresco, 0, locationListener);

                }

            }
        } else {
            Toast.makeText(this, "Debe conceder permiso para usar el GPS", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MENU && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(PRIMERO);
            editTextPassword.setText("");
            Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == PETICION_CAMARA && resultCode == RESULT_OK) {

            Bitmap foto = (Bitmap) data.getExtras().get("data"); //es una imagen previa a baja resolución
            imageView.setImageBitmap(foto);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));

        } else if (requestCode == VENGO_DE_LA_CAMARA_CON_FICHERO && resultCode == RESULT_OK) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));

        }
    }

    public void inicio() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float battery = (level / (float) scale) * 100;
        String gps = "" + Latitud + "," + Longitud;
        boolean resultado = manejadorBDENTRADAS.insertar(fecha, String.valueOf(battery), gps);

        if (resultado == true) {
            Toast.makeText(MainActivity.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Error al insertar", Toast.LENGTH_SHORT).show();
        }

    }
}
