package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosNotificacion {
    var tipoNotificacion: Int = 0
        get() = field
        set(value){field = value}

    var imagenUsuario: Int = 0
        get() = field
        set(value){field = value}

    var titulo: String = ""
        get() = field
        set(value){field = value}

    var descripcion: String = ""
        get() = field
        set(value){field = value}

    var imagen: Int = 0
        get() = field
        set(value){field = value}

    var url: String = ""
        get() = field
        set(value){field = value}

    constructor()

    constructor(tipoNotificacion: Int,imagenUsuario: Int, titulo: String, descripcion: String, imagen: Int, url: String) {
        this.tipoNotificacion = tipoNotificacion
        this.imagenUsuario = imagenUsuario
        this.titulo = titulo
        this.descripcion = descripcion
        this.imagen = imagen
        this.url = url
    }


}