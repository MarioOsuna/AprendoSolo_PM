package com.example.aprendosolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManejadorBDLOGROS extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alumnos.db";

    private static final String TABLE_NAME = "alumnos";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NOMBRE";
    private static final String COL_3 = "APELLIDOS";
    private static final String COL_4 = "NOTA";

    public ManejadorBDLOGROS(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT," + COL_4 + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertar(String nombre, String apellidos, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nombre);
        contentValues.put(COL_3, apellidos);
        contentValues.put(COL_4, nota);

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

    public boolean actualizar(String id, String nombre, String apellidos, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nombre);
        contentValues.put(COL_3, apellidos);
        contentValues.put(COL_4, nota);

        long resultado = db.update(TABLE_NAME, contentValues, COL_1 + "=?", new String[]{id});

        db.close();

        return (resultado > 0);

    }

}
