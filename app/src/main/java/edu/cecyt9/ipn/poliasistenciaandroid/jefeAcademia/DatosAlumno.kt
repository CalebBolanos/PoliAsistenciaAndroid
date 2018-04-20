package edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia

/**
 * Created by Caleb on 19/04/2018.
 */
class DatosAlumno {
    var imagen: Int = 0
    var nombre: String = ""
    var grupo: String = ""
    var boleta: String = ""

    constructor(imagen: Int, nombre: String, grupo: String, boleta: String) {
        this.imagen = imagen
        this.nombre = nombre
        this.grupo = grupo
        this.boleta = boleta
    }
}