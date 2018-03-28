package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 28/03/2018.
 */
class DatosAsistenciaUnidadMes {

    var boleta: String = ""
        get() = field
        set(value){field = value}

    var nombre: String = ""
        get() = field
        set(value){field = value}

    var diasAsistido: String = ""
        get() = field
        set(value){field = value}

    var diasFaltado: String = ""
        get() = field
        set(value){field = value}

    constructor(boleta: String, nombre: String, diasAsistido: String, diasFaltado: String) {
        this.boleta = boleta
        this.nombre = nombre
        this.diasAsistido = diasAsistido
        this.diasFaltado = diasFaltado
    }
}