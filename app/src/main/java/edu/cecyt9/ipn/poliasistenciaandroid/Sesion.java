package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Caleb on 15/04/2018.
 */

public class Sesion {

    private SharedPreferences datosSesion;
    public static final int ALUMNO = 2;
    public static final int PROFESOR = 3;
    public static final int JEFE_ACADEMIA = 4;

    public Sesion(Context contexto) {
        datosSesion = PreferenceManager.getDefaultSharedPreferences(contexto);
    }

    public void setDatos(String usuario, String contrasena, int tipoUsr){
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("usuario", usuario);
        usr.putString("contrasena", contrasena);
        usr.putInt("tipo", tipoUsr);
        usr.apply();
    }

    public void clearDatos(){
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("usuario", "");
        usr.putString("contrasena", "");
        usr.putInt("tipo", 0);
        usr.apply();
    }

    public void setUsuario(String usuario) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("usuario", usuario);
        usr.apply();
    }

    public void setContrasena(String contrasena) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putString("contrasena", contrasena);
        usr.apply();
    }

    public void setTipoUsr(int tipoUsr) {
        SharedPreferences.Editor usr = datosSesion.edit();
        usr.putInt("tipo", tipoUsr);
        usr.apply();
    }

    public String getUsuario() {
        String usuario = datosSesion.getString("usuario","");
        return usuario;
    }

    public String getContrasena() {
        String contrasena = datosSesion.getString("contrasena","");
        return contrasena;
    }

    public int getTipoUsr() {
        int tipo = datosSesion.getInt("tipo", 0);
        return tipo;
    }
}
