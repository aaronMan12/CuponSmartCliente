/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaLogin;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author aaron
 */
public class LoginDAO {

    public static RespuestaLogin iniciarSesion(String correo, String contrasenia) {
        RespuestaLogin respuestaLogin = new RespuestaLogin();
        
        String url = Constantes.URL_WS+"autenticacion/iniciarSesionEscritorio";
        String parametros = String.format("userName=%s&contrasenia=%s", correo,contrasenia);
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionPOST(url, parametros);
        
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuestaLogin = gson.fromJson(respuestaConexion.getContenido(), RespuestaLogin.class);
            
        }else{
            respuestaLogin.setError(Boolean.TRUE);
            respuestaLogin.setContenido("Hubo un error al realizar la petición intentelo mas tarde");
           
        }
        return respuestaLogin;
    }
    
}
