package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosNotificacion {
    var tipoNotificacion: Int = 0
    var imagenUsuario: Int = 0
    var titulo: String = ""
    var descripcion: String = ""
    var imagen: Int = 0
    var url: String = ""

    constructor(tipoNotificacion: Int,imagenUsuario: Int, titulo: String, descripcion: String, imagen: Int, url: String) {
        this.tipoNotificacion = tipoNotificacion
        this.imagenUsuario = imagenUsuario
        this.titulo = titulo
        this.descripcion = descripcion
        this.imagen = imagen
        this.url = url
    }


}