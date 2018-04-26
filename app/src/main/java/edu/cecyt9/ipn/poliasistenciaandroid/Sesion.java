package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Caleb on 15/04/2018.
 */

public class Sesion {

    private SharedPreferences datosSesion;
    public static final int GESTION = 1;
    public static final int ALUMNO = 2;
    public static final int PROFESOR = 3;
    public static final int JEFE_ACADEMIA = 4;
    public static final int PREFECTO = 6;

    public Sesion(Context contexto) {
        datosSesion = PreferenceManager.getDefaultSharedPreferences(contexto);
    }

    public void setDatos(int idPer, int idTipo, String nombre, String paterno, String materno, String genero, String num, String nacimiento, String urlImagen){
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putInt("idper", idPer);
        usr.putInt("idtipo", idTipo);
        usr.putString("nombre", nombre);
        usr.putString("paterno", paterno);
        usr.putString("materno", materno);
        usr.putString("genero", genero);
        usr.putString("num", num);
        usr.putString("nacimiento", nacimiento);
        usr.putString("urlImagen", urlImagen);

        usr.apply();
    }

    public void clearDatos(){
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putInt("idper", 0);
        usr.putInt("idtipo", 0);
        usr.putString("nombre", "");
        usr.putString("paterno", "");
        usr.putString("materno", "");
        usr.putString("genero", "");
        usr.putString("num", "");
        usr.putString("nacimiento", "");
        usr.putString("urlImagen", "");
        usr.apply();
    }

    public void setIdPer(int idPer) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putInt("idper", idPer);
        usr.apply();
    }

    public void setIdTipo(int IdTipo) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putInt("idtipo", IdTipo);
        usr.apply();
    }

    public void setNombre(String nombre) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("nombre", nombre);
        usr.apply();
    }

    public void setPaterno(String paterno) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("paterno", paterno);
        usr.apply();
    }

    public void setMaterno(String materno) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("materno", materno);
        usr.apply();
    }

    public void setGenero(String genero) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("genero", genero);
        usr.apply();
    }

    public void setNum(String num) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("num", num);
        usr.apply();
    }

    public void setNacimiento(String nacimiento) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("nacimiento", nacimiento);
        usr.apply();
    }

    public void setUrlImagen(String urlImagen){
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("urlImagen", urlImagen);
        usr.apply();
    }


    public int getIdPer() {
        int idPer = datosSesion.getInt("idper",0);
        return idPer;
    }

    public int getIdTipo() {
        int idTipo = datosSesion.getInt("idtipo",0);
        return idTipo;
    }

    public String getNombre() {
        String nombre = datosSesion.getString("nombre","");
        return nombre;
    }

    public String getPaterno() {
        String paterno = datosSesion.getString("paterno","");
        return paterno;
    }

    public String getMaterno() {
        String materno = datosSesion.getString("materno","");
        return materno;
    }

    public String getGenero() {
        String genero = datosSesion.getString("genero","");
        return genero;
    }

    public String getNum() {
        String num = datosSesion.getString("num","");
        return num;
    }

    public String getNacimiento() {
        String nacimiento = datosSesion.getString("nacimiento","");
        return nacimiento;
    }

    public String urlImagen(){
        String urlImagen = datosSesion.getString("urlImagen","");
        return urlImagen;
    }

}
