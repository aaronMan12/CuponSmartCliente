package clientecuponsmart.modelo;

import clientecuponsmart.modelo.pojo.CodigoHTTP;
import clientecuponsmart.utils.Constantes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionHTTP {
    
    
      public static CodigoHTTP peticionPOST(String url, String parametros) {
        CodigoHTTP respuesta = new CodigoHTTP();
        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();
            conexionHTTP.setRequestMethod("POST");

            conexionHTTP.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexionHTTP.setDoOutput(true);
            //Escribir datos en el cuerpo de la petición
            OutputStream os = conexionHTTP.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            //termina la escritura

            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(convertirContenido(conexionHTTP.getInputStream()));
            } else {
                respuesta.setContenido("CODE ERROE:" + codigoRespuesta);
            }

        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error:" + ex.getMessage());
        } catch (IOException iox) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error:" + iox.getMessage());

        }

        return respuesta;

    }
    
    
    
    
     private static String convertirContenido(InputStream contenido) throws IOException {
        InputStreamReader inputLectura = new InputStreamReader(contenido);
        BufferedReader buffer = new BufferedReader(inputLectura);
        String cadenaEntrada;
        StringBuffer cadenaBuffer = new StringBuffer();
        while ((cadenaEntrada = buffer.readLine()) != null) {
            cadenaBuffer.append(cadenaEntrada);
        }
        buffer.close();
        return cadenaBuffer.toString();

    }
    
}
