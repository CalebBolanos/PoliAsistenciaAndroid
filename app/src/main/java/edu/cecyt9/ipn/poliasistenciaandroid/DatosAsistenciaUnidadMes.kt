package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 28/03/2018.
 */
class DatosAsistenciaUnidadMes {

    var boleta: String = ""
    var nombre: String = ""
    var diasAsistido: String = ""
    var diasFaltado: String = ""

    constructor(boleta: String, nombre: String, diasAsistido: String, diasFaltado: String) {
        this.boleta = boleta
        this.nombre = nombre
        this.diasAsistido = diasAsistido
        this.diasFaltado = diasFaltado
    }
}