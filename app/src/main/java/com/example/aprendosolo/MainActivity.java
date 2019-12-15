package com.example.aprendosolo;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    static String PRIMERO = "NOMBRE";
    private int MENU = 1;
    private int GALERIA = 3;
    static String CONTRASENA = "CONTRASEÑA";
    Button buttonEntrar, buttonGaleria, buttonFoto;
    EditText editTextPassword;
    public static final String SHARED_PREFS = "sharedPrefs";

    int PEDIR_PERMISO_GPS = 1;
    private static final int PEDI_PERMISO_DE_ESCRITURA = 3;
    private static final int VENGO_DE_LA_CAMARA_CON_FICHERO = 2;
    ImageView imageView;
    ManejadorBDENTRADAS manejadorBDENTRADAS;
    LocationManager locationManager;
    LocationListener locationListener;
    int tiempoRefresco = 500;
    private double Longitud = 0;
    private double Latitud = 0;


    String ID_CANAL = "mi canal favorito";
    int CODIGO_RESPUESTA = 1;
    SensorManager sensorManager;
    File fichero = null;


    Bitmap btmap;


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

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences sharedpreferencesImage=getSharedPreferences("Preferencias", MODE_PRIVATE);;

        manejadorBDENTRADAS = new ManejadorBDENTRADAS(MainActivity.this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorManager.SENSOR_DELAY_NORMAL);

        if (!sharedPreferences.getString(CONTRASENA, " ").equals(" ")) {
            String u = sharedpreferencesImage.getString("Imagen", "");
            Uri myUri = Uri.parse(u);
            Toast.makeText(this, ""+myUri, Toast.LENGTH_SHORT).show();

          //  imageView.setImageURI(myUri);
            lanzarNotificacionConTextoLargo();
        }
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Introduzca una contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    if (imageView.getDrawable() == null) {
                        Toast.makeText(MainActivity.this, "Introduzca una foto", Toast.LENGTH_SHORT).show();
                    } else {

                        if (editTextPassword.getText().toString().equals(sharedPreferences.getString(CONTRASENA, " "))) {

                            Log.i("Contraseña", " contraseña igual");
                            Intent intento = new Intent(MainActivity.this, Menu.class);
                            startActivityForResult(intento, MENU);
                        } else {
                            if (sharedPreferences.getString(CONTRASENA, " ").equals(" ")) {
                                //conservarImagen();
                                Log.i("Contraseña", "guardar datos");
                                saveData();
                                Intent intento = new Intent(MainActivity.this, Menu.class);
                                startActivityForResult(intento, MENU);
                            } else {

                                Toast.makeText(MainActivity.this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                                // Toast.makeText(MainActivity.this, "La contraseña que tiene es " + sharedPreferences.getString(CONTRASENA, " ")+" y ha introducido "+editTextPassword.getText().toString(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    }
                }

            }
        });
        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        onNewIntent(getIntent());
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("mensaje1")) {
                String msg = extras.getString("mensaje1");

                //tvNotify.setText(msg) ;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void lanzarNotificacionConTextoLargo() {

        int notifyId = 2;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Ejemplo de notificación con más texto")
                        .setAutoCancel(true)
                        .setContentText("Texto inicial.");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Este mensaje tiene más cosas");

        inboxStyle.addLine("una línea nueva");
        inboxStyle.addLine("Otro mensajito de mi amiguito");
        inboxStyle.addLine("Pedro estate atento");
        inboxStyle.addLine("Pedro deja de bostezar");
        inboxStyle.addLine("Sergio te estoy vigilando");

        builder.setStyle(inboxStyle);

        Intent intent = getIntent();
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "2";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(notifyId, builder.build());
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), GALERIA);

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CONTRASENA, editTextPassword.getText().toString());
        editor.apply();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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
        if(requestCode==PEDI_PERMISO_DE_ESCRITURA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                capturarFoto();
            } else {
                Toast.makeText(this, "Sin permiso de escritura no puedo hacer foto en alta resolución.", Toast.LENGTH_LONG).show();
            }
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
        if (resultCode == RESULT_OK && requestCode == GALERIA) {
            Uri path = data.getData();

            imageView.setImageURI(path);
            Toast.makeText(this, ""+path, Toast.LENGTH_SHORT).show();

            conservarImagen(path);



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
    public void conservarImagen(Uri path){
        SharedPreferences preferences = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Imagen", path.toString());
        editor.apply();
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
