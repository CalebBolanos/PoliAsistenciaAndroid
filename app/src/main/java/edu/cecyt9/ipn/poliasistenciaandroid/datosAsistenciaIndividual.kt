package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 27/03/2018.
 */
class datosAsistenciaIndividual {

    var aisistido: String = ""
        get() = field
        set(value){field = value}

    var dia: String = ""
        get() = field
        set(value){field = value}

    var entrada: String = ""
        get() = field
        set(value){field = value}

    var salida: String = ""
        get() = field
        set(value){field = value}

    constructor(aisistido: String, dia: String, entrada: String, salida: String) {
        this.aisistido = aisistido
        this.dia = dia
        this.entrada = entrada
        this.salida = salida
    }
}