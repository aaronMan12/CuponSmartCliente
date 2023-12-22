package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Sucursal;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class SucursalDAO {

    public static RespuestaUsuarioEscritorio buscarTodasLasSucusales() {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "sucursales/buscarSucursales";
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);

        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Hubo un error al intentar obtener las sucursales.");
        }

        return respuesta;
    }
    
    public static RespuestaUsuarioEscritorio buscarSucursalesUsuario(Integer idUsuario) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "sucursales/buscarSucursalesUsuario/" + idUsuario;
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Hubo un error al intentar obtener las sucursales.");
        }

        return respuesta;
    }

    public static RespuestaUsuarioEscritorio registrarSucursal(Sucursal sucursal) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();

        String url = Constantes.URL_WS + "sucursales/registrarSucursal";
        String parametros = String.format(
                "idEmpresa=%s&nombre=%s&colonia=%s&telefono=%s&longitud=%s&latitud=%s&nombreEncargado=%s",
                sucursal.getIdEmpresa(), sucursal.getNombre(), sucursal.getColonia(), sucursal.getTelefono(),
                sucursal.getLongitud(), sucursal.getLatitud(), sucursal.getNombreEncargado());

        CodigoHTTP respuestaWS = ConexionHTTP.peticionPOST(url, parametros);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error en la petición para el registrar la sucursal.");
        }

        return respuesta;
    }

    public static RespuestaUsuarioEscritorio editarSucursal(Sucursal sucursal) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();

        String url = Constantes.URL_WS + "sucursales/editarSucursal";
        String parametros = String.format(
                "idSucursal=%s&idEmpresa=%s&nombre=%s&colonia=%s&telefono=%s&longitud=%s&latitud=%s&nombreEncargado=%s",
                sucursal.getIdSucursal(), sucursal.getIdEmpresa(), sucursal.getNombre(), sucursal.getColonia(), sucursal.getTelefono(),
                sucursal.getLongitud(), sucursal.getLatitud(), sucursal.getNombreEncargado());

        CodigoHTTP respuestaWS = ConexionHTTP.peticionPUT(url, parametros);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), RespuestaUsuarioEscritorio.class);
        } else {
            respuesta.setError(true);
            respuesta.setContenido("Error en la petición para el editar la sucursal.");
        }

        return respuesta;
    }

    public static RespuestaUsuarioEscritorio eliminarSucursal(int idSucursal, int idUbicacion) {
        RespuestaUsuarioEscritorio mensaje = new RespuestaUsuarioEscritorio();

        String url = Constantes.URL_WS + "sucursales/eliminarSucursal";
        String parametros = String.format("idSucursal=%s&idUbicacion=%s", idSucursal, idUbicacion);

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
}
