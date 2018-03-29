package edu.cecyt9.ipn.poliasistenciaandroid.alumno

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosEstatusProfesor {
    var unidad: String = ""
        get() = field
        set(value){field = value}

    var profesor: String = ""
        get() = field
        set(value){field = value}

    var estatus: String = ""
        get() = field
        set(value){field = value}

    constructor(unidad: String, profesor: String, estatus: String) {
        this.unidad = unidad
        this.profesor = profesor
        this.estatus = estatus
    }
}