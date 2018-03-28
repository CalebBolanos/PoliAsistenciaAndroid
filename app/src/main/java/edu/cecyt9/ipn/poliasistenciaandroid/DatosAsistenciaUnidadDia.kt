package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 28/03/2018.
 */
class DatosAsistenciaUnidadDia {
    var boleta: String = ""
        get() = field
        set(value){field = value}

    var nombre: String = ""
        get() = field
        set(value){field = value}

    var asistencia: String = ""
        get() = field
        set(value){field = value}

    constructor(boleta: String, nombre: String, asistencia: String) {
        this.boleta = boleta
        this.nombre = nombre
        this.asistencia = asistencia
    }
}