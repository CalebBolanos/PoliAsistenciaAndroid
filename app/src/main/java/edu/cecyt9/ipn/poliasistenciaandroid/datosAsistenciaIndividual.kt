package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 27/03/2018.
 */
class datosAsistenciaIndividual {

    var aisistido: String = ""
    var dia: String = ""
    var entrada: String = ""
    var salida: String = ""

    constructor(aisistido: String, dia: String, entrada: String, salida: String) {
        this.aisistido = aisistido
        this.dia = dia
        this.entrada = entrada
        this.salida = salida
    }
}