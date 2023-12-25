package clientecuponsmart.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

public class Utilidades {

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String CODIGO_POSTAL_PATTERN = "^\\d{5}$";
    public static final String TELEFONO_PATTERN = "^\\d{10}$";
    public static final String RFC_EMPRESA_PATTERN = "^[A-Z&Ñ]{3}(\\d{6})[A-Z0-9]{3}$";
    public static final String FECHA_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String CURP_PATTERN = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";
    public static final String PAGINA_WEB_PATTERN = "^www\\.[a-zA-Z0-9-]+\\.(com|net|org|info|biz|gov|edu|mil|arpa|int)$";
    public static final String ESPACIOS_INICIO_FIN_PATTERN = "^(\\s*|\\s*)$";
    public static final String NUMERO_PATTERN = "^[1-9]\\d*$";
    public static final String CODIGO_PATTERN = "^[a-zA-Z0-9]{8}$";

    public static boolean validarCadena(String cadena, String expresionRegular) {
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static boolean validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        
        return fechaInicio.isAfter(fechaFin);
    }
    
}
