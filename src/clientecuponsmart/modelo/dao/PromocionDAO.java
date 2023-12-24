package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class PromocionDAO {

    public static RespuestaUsuarioEscritorio buscarPromocionesEmpresa(Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/buscarPromocionesEmpresa/" + idEmpresa;
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener las promociones de la empresa asociada al usuario");
        }

        return respuesta;
    }

    public static RespuestaUsuarioEscritorio buscarTodasLasPromociones() {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/buscarPromociones";
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener las promociones");
        }

        return respuesta;
    }

    public static RespuestaUsuarioEscritorio eliminarPromocion(Integer idPromocion) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS+"promociones/eliminarPromocion";
        String parametros = String.format("idPromocion=%s", idPromocion);
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionDELETE(url, parametros);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        }else{
            respuesta.setError(true);
            respuesta.setContenido("Error al eliminar la promoción");
        }
        return respuesta;
    }

}
