package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class EmpresaDAO {

    public static RespuestaUsuarioEscritorio eliminarEmpresa(int idEmpresa, int idUbicacion) {
        RespuestaUsuarioEscritorio mensaje = new RespuestaUsuarioEscritorio();

        String url = Constantes.URL_WS + "empresas/eliminarEmpresa";
        String parametros = String.format("idEmpresa=%s&idUbicacion=%s", idEmpresa, idUbicacion);

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

    public static RespuestaUsuarioEscritorio buscarTodasLasEmpresas() {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "empresas/buscarEmpresas";
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
}
