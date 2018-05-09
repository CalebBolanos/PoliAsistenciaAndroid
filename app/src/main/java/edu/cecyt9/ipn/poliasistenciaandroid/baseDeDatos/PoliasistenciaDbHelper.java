package edu.cecyt9.ipn.poliasistenciaandroid.baseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;

public class PoliasistenciaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Poliasistencia.db";
    private static Context cont;

    public PoliasistenciaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cont = context;
    }
    public PoliasistenciaDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Persona
        db.execSQL("CREATE TABLE " + PoliasitenciaContract.PersonaEntry.TABLE_NAME + " ("
                + PoliasitenciaContract.PersonaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PoliasitenciaContract.PersonaEntry.IDPER + " INT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.TIPO + " INT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.GENERO + " INT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.NOMBRE + " TEXT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.PATERNO + " TEXT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.MATERNO + " TEXT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.FECHA + " TEXT NOT NULL,"
                + PoliasitenciaContract.PersonaEntry.NUMERO + " TEXT NOT NULL,"
                + "UNIQUE (" + PoliasitenciaContract.PersonaEntry.IDPER + "))");
        //Horario
        db.execSQL("CREATE TABLE "+ PoliasitenciaContract.HorarioEntry.TABLE_NAME + "("
                + PoliasitenciaContract.HorarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PoliasitenciaContract.HorarioEntry.MATERIA + " TEXT NOT NULL, "
                + PoliasitenciaContract.HorarioEntry.HORA + " INT NOT NULL, "
                + PoliasitenciaContract.HorarioEntry.DIA + " INT NOT NULL, "
                + PoliasitenciaContract.HorarioEntry.PROFESORGRUPO + " TEXT)"
        );
        // Contenedor de valores
        ContentValues values = new ContentValues();
        Sesion ses = new Sesion(cont);
        // Pares clave-valor
        values.put(PoliasitenciaContract.PersonaEntry.IDPER, ses.getIdPer());
        values.put(PoliasitenciaContract.PersonaEntry.TIPO, ses.getIdTipo());
        values.put(PoliasitenciaContract.PersonaEntry.GENERO, ses.getIdTipo());
        values.put(PoliasitenciaContract.PersonaEntry.NOMBRE, ses.getNombre());
        values.put(PoliasitenciaContract.PersonaEntry.PATERNO, ses.getPaterno());
        values.put(PoliasitenciaContract.PersonaEntry.MATERNO, ses.getMaterno());
        values.put(PoliasitenciaContract.PersonaEntry.FECHA, ses.getNacimiento());
        values.put(PoliasitenciaContract.PersonaEntry.NUMERO, ses.getNum());

        // Insertar...
        db.insert(PoliasitenciaContract.PersonaEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
