package com.example.aprendosolo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    static String NOMBRE = "NOMBRE";
    private int ANADIR = 1;
    private int BORRAR = 2;
    private int MODIFICAR = 3;
    private int APRENDER = 4;
    private int LOGROS = 5;
    int CODIGO_RESPUESTA = 1;
    String ID_CANAL = "mi canal favorito";
    boolean suena=true;
    Button buttonCerrar, buttonAnadir, buttonBorrar, buttonModificar, buttonAprender, buttonSonido, buttonVLogros, buttonBLogros, buttonCompartir;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;
    ManejadorBDLOGROS manejadorBDLOGROS;
    ManejadorBDENTRADAS manejadorBDENTRADAS;
    MediaPlayer mediaPlayer;

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

        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(Menu.this);
        manejadorBDLOGROS = new ManejadorBDLOGROS(Menu.this);
        manejadorBDENTRADAS = new ManejadorBDENTRADAS(Menu.this);

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
                mediaPlayer = MediaPlayer.create(Menu.this, R.raw.cambio);
                if(suena){
                    //suena=!suena;
                    suena=false;
                    Toast.makeText(Menu.this, "Sonido desactivado", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }else{
                    suena=true;
                    Toast.makeText(Menu.this, "Sonido activado ", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }
                Intent intent=  new Intent(Menu.this, Aprender.class);
                intent.putExtra("boleano", suena);

             //   startActivity(intent);
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
                boolean delete=manejadorBDLOGROS.borrar();               
                boolean delete2=manejadorBDENTRADAS.borrar();
                

                    lanzarNotificacion("Borrado de las tablas logros y entradas");



            }
        });
        buttonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String compartir = "\nTABLA LOGROS\n\n"+mostrarTablaLogros(manejadorBDLOGROS);

                Intent intentoEmail = new Intent(Intent.ACTION_SEND);

                intentoEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                intentoEmail.putExtra(Intent.EXTRA_SUBJECT, "Datos de lo aprendido");
                intentoEmail.putExtra(Intent.EXTRA_TEXT, compartir);
                intentoEmail.setType("message/rfc822");

               startActivity(Intent.createChooser(intentoEmail, "Escoge tu app de email favorito"));

            }
        });
    }

    private void lanzarNotificacion(String cadena) {

        //Id para la notificación
        int notifyId = 1;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Esto
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent, 0);
        //Hasta aquí he metido yo

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Borrado de tablas")
                        .setAutoCancel(true)
                        .setContentText(cadena)
                        .setContentIntent(pendingIntent2);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "1";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
        taskStackBuilder.addNextIntent(getIntent());
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(CODIGO_RESPUESTA, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);


        notificationManager.notify(notifyId, builder.build());
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

    public String mostrarTablaPreguntas(ManejadorBDPREGUNTAS manejadorBDPREGUNTAS) {
        Cursor cursor = manejadorBDPREGUNTAS.listar();
        String fila = "";


        while (cursor.moveToNext()) {
            fila = "";
            fila += "ID: " + cursor.getString(0) + "\n";
            fila += " PREGUNTA: " + cursor.getString(1) + "\n";
            fila += " RESPUESTA: " + cursor.getString(2) + "\n";
            fila += " R_INC 1: " + cursor.getString(3) + "\n";
            fila += " R_INC 2: " + cursor.getString(4) + "\n\n\n";


        }


        cursor.close();
        return fila;

    }

    public String mostrarTablaLogros(ManejadorBDLOGROS manejadorBDLOGROS) {
        Cursor cursor = manejadorBDLOGROS.listar();
        String fila = "";


        while (cursor.moveToNext()) {
            fila = "";
            fila += "Hora_fecha: " + cursor.getString(1) + "\n";
            fila += "Puntuación: " + cursor.getString(2) + "\n";



        }


        cursor.close();
        return fila;

    }

    public String mostrarTablaEntradas(ManejadorBDENTRADAS manejadorBDENTRADAS) {
        Cursor cursor = manejadorBDENTRADAS.listar();
        String fila = "";


        while (cursor.moveToNext()) {
            fila = "";
            fila += "ID: " + cursor.getString(0) + "\n";


        }


        cursor.close();
        return fila;

    }
}
