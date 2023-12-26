package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;


public class CuponDAO {

    public static RespuestaUsuarioEscritorio cargarInformacionComercial(Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(Constantes.URL_WS+"cupones/buscarCuponesComercial/"+idEmpresa);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }else{
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener los cupones");
        }
        return respuesta;
    }

    public static RespuestaUsuarioEscritorio cargarInformacionGeneral() {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(Constantes.URL_WS+"cupones/buscarTodosLosCupones");
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }else{
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener los cupones");
        }
        return respuesta;
    }

    public static RespuestaUsuarioEscritorio canjearCupon(String codigo) {
        RespuestaUsuarioEscritorio respuesta  = new RespuestaUsuarioEscritorio();
        Gson gson = new Gson();
        String parametros = String.format("codigo=%s",codigo );
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionPUT(Constantes.URL_WS+"cupones/canjearCupon", parametros);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }else{
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }
        return respuesta;
    }

    public static RespuestaUsuarioEscritorio canjearCuponComercial(String codigo, Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta  = new RespuestaUsuarioEscritorio();
        Gson gson = new Gson();
        String parametros = String.format("codigo=%s&"
                + "idEmpresa=%s",codigo, idEmpresa);
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionPUT(Constantes.URL_WS+"cupones/canjearCuponComercial", parametros);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }else{
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }
        return respuesta;
    }
    
}
