package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.Promocion;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class PromocionDAO {
    
    public static RespuestaUsuarioEscritorio registrarPromocion(Promocion promocion) {
        RespuestaUsuarioEscritorio mensaje = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/registrarPromocion";
        
        Gson gson = new Gson();
        String parametros = gson.toJson(promocion);
        
        CodigoHTTP respuesta = ConexionHTTP.peticionPOSTJson(url, parametros);
        
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            mensaje.setError(true);
            mensaje.setContenido("Error en la petición para registrar la promoción.");
        }
        
        return mensaje;
    }
    
    public static RespuestaUsuarioEscritorio editarPromocion(Promocion promocion) {
        RespuestaUsuarioEscritorio mensaje = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/editarPromocion";
        
        Gson gson = new Gson();
        String parametros = gson.toJson(promocion);
        
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJson(url, parametros);
        
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            mensaje.setError(true);
            mensaje.setContenido(respuesta.getContenido());
        }
        
        return mensaje;
    }

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
    
    public static RespuestaUsuarioEscritorio buscarSucursalesEmpresa(Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/buscarSucursalesEmpresa/" + idEmpresa;
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener las sucursales");
        }

        return respuesta;
    }
    
    public static RespuestaUsuarioEscritorio buscarSucursalesValidasGeneralRegistro(Integer idPromocion) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/buscarSucursalesValidas/" + idPromocion;
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("No hay sucursales registradas validas.");
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
    
    public static RespuestaUsuarioEscritorio buscarTodasLasCategorias() {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "promociones/buscarCategorias";
        Gson gson = new Gson();
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error al obtener las categorias.");
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
