package edu.cecyt9.ipn.poliasistenciaandroid.alumno

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosEstatusProfesor {
    var unidad: String = ""
    var profesor: String = ""
    var estatus: String = ""

    constructor(unidad: String, profesor: String, estatus: String) {
        this.unidad = unidad
        this.profesor = profesor
        this.estatus = estatus
    }
}