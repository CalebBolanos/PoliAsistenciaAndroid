package edu.cecyt9.ipn.poliasistenciaandroid.profesor

/**
 * Created by Caleb on 30/03/2018.
 */
class DatosGrupos {

    var grupo: String = ""
        get() = field
        set(value){field = value}

    var unidad: String = ""
        get() = field
        set(value){field = value}

    var alumnos: String = ""
        get() = field
        set(value){field = value}

    var especialidad: String = ""
        get() = field
        set(value){field = value}

    constructor(grupo: String, unidad: String, alumnos: String, especialidad: String) {
        this.grupo = grupo
        this.unidad = unidad
        this.alumnos = alumnos
        this.especialidad = especialidad
    }
}