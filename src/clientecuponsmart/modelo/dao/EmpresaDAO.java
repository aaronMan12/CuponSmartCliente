package clientecuponsmart.modelo.dao;

import clientecuponsmart.modelo.ConexionHTTP;
import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Constantes;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;

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

    public static RespuestaUsuarioEscritorio buscarEmpresaPorId(int idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "empresas/buscarEmpresa/"+idEmpresa;
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
            
        }else{
            respuesta.setError(true);
            respuesta.setContenido(url);
        }
        return respuesta;
    }

    public static Empresa obtenerFotoEmpresa(int idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        String url = Constantes.URL_WS + "empresas/buscarLogoEmpresa/"+idEmpresa;
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionGET(url);
        
        if (codigoHTTP.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.setError(false);
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
            
        }else{
            respuesta.setError(true);
            respuesta.setContenido(url);
        
        }
        return respuesta.getEmpresa();
    }

    public static RespuestaUsuarioEscritorio actualizarLogoEmpresa(File logo, Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"empresas/registrarLogo/"+idEmpresa;
        
        try {
        byte[] imagen = Files.readAllBytes(logo.toPath());
        CodigoHTTP codigoHTTP = ConexionHTTP.peticionPUTLogo(url, imagen);
        
        if (codigoHTTP.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta = gson.fromJson(codigoHTTP.getContenido(), RespuestaUsuarioEscritorio.class);
            
        }else{
            respuesta.setContenido("Error al subir la foto intentelo más tarde");
        }
            
        } catch (IOException e) {
          respuesta.setContenido("El archivo selecionado no puede ser eviado para su almacenamiento");
        }
        return respuesta;
    }
    
    
}
