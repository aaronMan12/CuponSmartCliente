/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author Oscar
 */
public class UsuarioDAO {
    
    public static RespuestaUsuarioEscritorio buscarPorUserName(String userName){
    RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
    String url = Constantes.URL_WS +"usuarios/buscarPorUserName/" + userName;
    CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
    if(codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
    respuesta.setError(false);
    Gson gson = new Gson();
    respuesta = gson.fromJson(codigoRespuesta.getContenido(), RespuestaUsuarioEscritorio.class);
    
    }else{
        
    respuesta.setError(true);
    respuesta.setContenido(url);
    
    }
    return respuesta;
    }
}
