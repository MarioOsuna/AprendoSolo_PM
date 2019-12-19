package com.example.aprendosolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManejadorBDPREGUNTAS extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PREGUNTAS.db";

    private static final String TABLE_NAME = "PREGUNTAS";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "Pregunta";
    private static final String COL_3 = "Respuesta_correcta";
    private static final String COL_4 = "Respuesta_incorrecta1";
    private static final String COL_5 = "Respuesta_incorrecta2";
    private static final String COL_6 = "Respuesta_incorrecta3";

    public ManejadorBDPREGUNTAS(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT," + COL_4 + " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertar(String pregunta, String respuesta, String incorrecta1,String incorrecta2,String incorrecta3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, pregunta);
        contentValues.put(COL_3, respuesta);
        contentValues.put(COL_4, incorrecta1);
        contentValues.put(COL_5, incorrecta2);
        contentValues.put(COL_6, incorrecta3);

        long resultado = db.insert(TABLE_NAME, null, contentValues);


        if (resultado == -1) {
            return false;
        } else {
            return true;
        }


        //return (resultado==-1?false:false);

    }

    Cursor listar() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor datos = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return datos;

    }

    boolean borrar(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filas_borradas = db.delete(TABLE_NAME, COL_1 + "=?", new String[]{id});
        db.close();

        return (filas_borradas > 0);

    }

    public boolean actualizar(String id, String pregunta, String respuesta, String incorrecta1,String incorrecta2,String incorrecta3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, pregunta);
        contentValues.put(COL_3, respuesta);
        contentValues.put(COL_4, incorrecta1);
        contentValues.put(COL_5, incorrecta2);
        contentValues.put(COL_6, incorrecta3);

        long resultado = db.update(TABLE_NAME, contentValues, COL_1 + "=?", new String[]{id});

        db.close();

        return (resultado > 0);

    }

}
