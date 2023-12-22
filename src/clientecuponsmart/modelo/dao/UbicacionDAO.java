package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Ubicacion;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class UbicacionDAO {
    
    public static RespuestaUsuarioEscritorio registrarUbicacionSucursal(Ubicacion ubicacion) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        
        String url = Constantes.URL_WS + "ubicacion/registrarUbicacionSucursal";
        String parametros = String.format(
                "calle=%s&numero=%s&codigoPostal=%s&ciudad=%s&idSucursal=%s", 
                ubicacion.getCalle(), ubicacion.getNumero(), 
                ubicacion.getCodigoPostal(), ubicacion.getCiudad(), ubicacion.getIdSucursal());
        CodigoHTTP respuestaWS = ConexionHTTP.peticionPOST(url, parametros);
        
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error en la petición para el registrar la ubicación a sucursal.");
        }
        
        return respuesta;
    }
    
}
