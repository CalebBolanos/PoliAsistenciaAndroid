package edu.cecyt9.ipn.poliasistenciaandroid

/**
 * Created by Caleb on 29/03/2018.
 */
class DatosNotificacion {
    var tipoNotificacion: Int = 0
    var usuario: String = ""
    var imagenUsuario: String = ""
    var titulo: String = ""
    var descripcion: String = ""
    var imagen: String = ""
    var url: String = ""
    var borrar: Boolean = false
    var idNoti : Int = 0

    constructor(tipoNotificacion: Int, usuario: String, imagenUsuario: String, titulo: String, descripcion: String, imagen: String, url: String, borrar: Boolean, idNoti : Int) {
        this.tipoNotificacion = tipoNotificacion
        this.usuario = usuario
        this.imagenUsuario = imagenUsuario
        this.titulo = titulo
        this.descripcion = descripcion
        this.imagen = imagen
        this.url = url
        this.borrar = borrar
        this.idNoti = idNoti
    }


}