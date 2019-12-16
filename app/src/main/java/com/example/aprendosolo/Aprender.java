package com.example.aprendosolo;

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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Aprender extends AppCompatActivity {
    RadioButton radioButton;

    GridLayout g;
    ManejadorBDPREGUNTAS manejadorBDPREGUNTAS;
    ManejadorBDLOGROS manejadorBDLOGROS;
    int n;
    Button button;
    String[] filaPreg, filaRes, filaInco, filaInco2;
    String ID_CANAL = "mi canal favorito";
    int CODIGO_RESPUESTA = 1;
    int puntos = 0;
    private boolean correcta;
    private boolean siguiente;
    private int acertadas=0;
    int[] alt;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        g = findViewById(R.id.gridLayout);
        button = findViewById(R.id.buttonEnviarRespuesta);
        int preg = 0;

        manejadorBDPREGUNTAS = new ManejadorBDPREGUNTAS(Aprender.this);
        manejadorBDLOGROS = new ManejadorBDLOGROS(Aprender.this);
        n = total();
        //obtengo preguntas
        if (n != 0) {
            //Generar numeros aleatoriamente sin repetir
            alt = aleatorio();
            crear(preg);

            //  Toast.makeText(this, "Acertadas " + acertadas, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se han introducido preguntas", Toast.LENGTH_SHORT).show();
        }


    }

    public void crear(int preg) {
        String res = "";
      final int num=0;
        siguiente = true;
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

        if (preg >= 0 && preg < n) {

            textView[preg] = new TextView(Aprender.this);
            textView[preg].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView[preg].setText(filaPreg[preg]);
            //  textView[preg].setText(filaPreg[alt[preg]]);
            //  System.out.println("Texto: "+filaPreg[alt[preg]]+" alt[preg]= "+alt[preg]+" preg "+preg);
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
            for (int a = 0; a < 3; a++) {

                if (num1 == num2) {
                    numero = (int) (Math.random() * 3) + 1;
                    num2 = numero;
                    a--;
                }
                if (num3 == num2) {
                    numero = (int) (Math.random() * 3) + 1;
                    num3 = numero;
                    a--;
                }
                if (num3 == num1) {
                    numero = (int) (Math.random() * 3) + 1;
                    num1 = numero;
                    a--;
                }
                if (num1 != num2 && num3 != num2 && num3 != num1) {
                    a = 3;
                }

            }
            int respuesta = 0;
            for (int j = 1; j <= 3; j++) {
                final RadioButton rdbtn = new RadioButton(this);

                rdbtn.setId(((j)));
                if (j == num1) {
                    rdbtn.setId(((j)));
                    rdbtn.setText(" " + rdbtn.getId() + " " + filaRes[preg]);
                    // rdbtn.setText(" " + rdbtn.getId() + " " + filaRes[alt[preg]]);
                    respuesta = j;
                    res = filaRes[preg];
                    // res = filaRes[alt[preg]];
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


            if (siguiente) {
                preg++;
                //Toast.makeText(this, "preg: " + preg, Toast.LENGTH_SHORT).show();
            }

            final int fpreg = preg;
            final String finalRes = res;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    siguiente = true;
                    //System.out.println("button " + fpreg);
                    crear(fpreg);
                    textView[fpreg - 1].setVisibility(View.GONE);
                    radioGroup[fpreg - 1].setVisibility(View.GONE);
                    if (correcta) {
                        Toast.makeText(Aprender.this, "La respuesta es correcta ", Toast.LENGTH_SHORT).show();
                        acertadas++;

                        System.out.println("Acertadas1: "+acertadas);
                        sonido(true);
                    } else {
                        Toast.makeText(Aprender.this, "La respuesta correcta es " + finalRes, Toast.LENGTH_SHORT).show();
                        sonido(false);
                    }


                }
            });
            Toast.makeText(this, "preg " + preg + " || n " + n, Toast.LENGTH_SHORT).show();


        } else {
          //  num=acertadas;
            if (preg == 6) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date)+" "+hora();;
                Toast.makeText(this, "preg " + preg + "n " + n, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Finalizado has acertado " + acertadas + " de " + n, Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
                System.out.println("Acertadas3: "+acertadas);
                lanzarNotificacionConImagen(String.valueOf(acertadas));
                manejadorBDLOGROS.insertar(fecha, String.valueOf(acertadas));
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date)+" "+hora();

                Toast.makeText(this, "preg " + preg + "n " + n, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Finalizado has acertado " + acertadas + " de " + n, Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
                System.out.println("Acertadas2: "+ String.valueOf(acertadas));
                lanzarNotificacionConImagen(String.valueOf(acertadas));
                manejadorBDLOGROS.insertar(fecha, String.valueOf(acertadas));


            }
        }

    }

    public int total() {
        Cursor cursor = manejadorBDPREGUNTAS.listar();
        int filas = 0;

        if ((cursor != null) && (cursor.getCount() > 0)) {

            filas = cursor.getCount();
            cursor.close();
            Toast.makeText(this, "Hay un total de " + filas, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Aprender.this, "No hay preguntas introducidas", Toast.LENGTH_SHORT).show();
        }
        return filas;
    }

    public int[] aleatorio() {
        int n1 = 0;
        int[] array = new int[n];

        array[n1] = (int) (Math.random() * n) + 1;
        for (n1 = 1; n1 < n; n1++) {
            array[n1] = (int) (Math.random() * n) + 1;
            for (int j = 0; j < n1; j++) {
                if (array[n1] == array[j]) {

                    n1--;
                }
            }

        }

        return array;
    }

    public void sonido(boolean correct) {

        boolean suena = getIntent().getBooleanExtra("boleano", true);
        if (suena) {
            if (correct) {
                mediaPlayer = MediaPlayer.create(Aprender.this, R.raw.correcto);
               // Toast.makeText(this, "Sonido correcto", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
            } else {
                mediaPlayer = MediaPlayer.create(Aprender.this, R.raw.error);
                //Toast.makeText(this, "Sonido INcorrecto", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

            }
        }
    }
    public String hora(){
        String cadena;
        Calendar calendario = Calendar.getInstance();
         calendario = new GregorianCalendar();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        cadena=(hora+1)+":"+minutos+":"+segundos;
        return cadena;
    }
    private void lanzarNotificacionConImagen(String aciertos) {

        int notifyId = 4;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Resultado del test")
                        .setAutoCancel(true)
                        .setContentText("Aciertos "+aciertos);


        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.test)).build();

        builder.setStyle(bigPictureStyle);

        Intent intent = getIntent();
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(Aprender.this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "4";
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


}
