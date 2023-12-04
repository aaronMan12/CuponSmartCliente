/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author Oscar
 */
public class UsuarioDAO {

    public static RespuestaUsuarioEscritorio buscarPorUserName(String userName) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "usuarios/buscarPorUserName/" + userName;
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if (codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoRespuesta.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido(url);
        }
        return respuesta;
    }

    public static RespuestaUsuarioEscritorio buscarTodosLosUsuarios(Integer idUsuario) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "usuarios/buscarUsuarios/" + idUsuario;
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido(url);
        }

        return respuesta;
    }
    
    public static RespuestaUsuarioEscritorio eliminarUsuario(int idUsuario) {
        RespuestaUsuarioEscritorio mensaje = new RespuestaUsuarioEscritorio();
        
        String url = Constantes.URL_WS + "usuarios/eliminarUsuario";
        String parametros = String.format("idUsuario=%s", idUsuario);
        
        CodigoHTTP respuestaWS = ConexionHTTP.peticionDELETE(url, parametros);
        
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            mensaje = gson.fromJson(respuestaWS.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            mensaje.setError(true);
            mensaje.setContenido("Error en la petición para el eliminar el paciente.");
        }
        
        return mensaje;
    }

    public static RespuestaUsuarioEscritorio editarUsuario(Usuario usuario) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        
        String url = Constantes.URL_WS + "usuarios/editarUsuario";
        String parametros = String.format(
                "nombre=%s&apellidoPaterno=%s&apellidoMaterno=%s&curp=%s&correo=%s&userName=%s&contrasenia=%s&idUsuario=%s", 
                usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno(), usuario.getCurp(), 
                usuario.getCorreo(), usuario.getUserName(), usuario.getContrasenia(), usuario.getIdUsuario());
        CodigoHTTP respuestaWS = ConexionHTTP.peticionPUT(url, parametros);
        
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error en la petición para el editar el usuario.");
        }
        return respuesta;
    }
}
