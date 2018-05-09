package edu.cecyt9.ipn.poliasistenciaandroid.baseDeDatos;

import android.provider.BaseColumns;

public class PoliasitenciaContract {
    public static abstract class PersonaEntry implements BaseColumns {
        public static final String TABLE_NAME ="personas";
        public static final String IDPER = "idPer";
        public static final String TIPO= "tipo";
        public static final String GENERO = "genero";
        public static final String NOMBRE = "nombre";
        public static final String PATERNO = "paterno";
        public static final String MATERNO = "materno";
        public static final String FECHA = "fecha";
        public static final String NUMERO = "numero";
    }
    public static abstract class HorarioEntry implements BaseColumns {
        public static final String TABLE_NAME ="horario";
        public static final String IDMATERIA = "idMateria";
        public static final String MATERIA = "materia";
        public static final String HORA = "hora";
        public static final String DIA = "dia";
        public static final String PROFESORGRUPO = "profesorGrupo";
    }

}
