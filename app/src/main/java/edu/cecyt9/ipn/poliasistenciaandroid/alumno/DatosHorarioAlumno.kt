package edu.cecyt9.ipn.poliasistenciaandroid.alumno

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosHorarioAlumno {
    var unidad: String = ""
        get() = field
        set(value){field = value}

    var hora: String = ""
        get() = field
        set(value){field = value}

    constructor(unidad: String, hora: String) {
        this.unidad = unidad
        this.hora = hora
    }
}