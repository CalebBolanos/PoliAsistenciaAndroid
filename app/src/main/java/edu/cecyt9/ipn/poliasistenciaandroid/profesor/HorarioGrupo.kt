package edu.cecyt9.ipn.poliasistenciaandroid.profesor

/**
 * Created by Caleb on 30/03/2018.
 */
class HorarioGrupo {

    var grupo: String = ""
        get() = field
        set(value){field = value}

    var unidad: String = ""
        get() = field
        set(value){field = value}

    var hora: String = ""
        get() = field
        set(value){field = value}

    constructor(grupo: String, unidad: String, hora: String) {
        this.grupo = grupo
        this.unidad = unidad
        this.hora = hora
    }
}