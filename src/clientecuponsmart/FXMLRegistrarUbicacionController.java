package clientecuponsmart;

import clientecuponsmart.modelo.dao.UbicacionDAO;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Ubicacion;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrarUbicacionController implements Initializable {

    private Ubicacion ubicacion;
    private Integer idSucursal;
    private Integer idEmpresa;

    @FXML
    private TextField tfCalle;
    @FXML
    private Label lbCalle;
    @FXML
    private Label lbNumero;
    @FXML
    private TextField tfNumero;
    @FXML
    private Label lbCodigoPostal;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private Label lbCiudad;
    @FXML
    private TextField tfCiudad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (this.validarCampos()) {
            if (idSucursal != null) {
                if (ubicacion != null) {
                    // Editar
                } else {
                    Ubicacion ubicacionNueva = new Ubicacion();
                    ubicacionNueva.setCalle(this.tfCalle.getText());
                    ubicacionNueva.setNumero(Integer.parseInt(this.tfNumero.getText()));
                    ubicacionNueva.setCodigoPostal(this.tfCodigoPostal.getText());
                    ubicacionNueva.setCiudad(this.tfCiudad.getText());
                    ubicacionNueva.setIdSucursal(idSucursal);

                    this.registrarUbicacionSucursal(ubicacionNueva);
                }
            } else {
                // Registro ubicacion empresa
            }
        }
    }

    public void inicializarRegistroSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    private void registrarUbicacionSucursal(Ubicacion ubicacion) {
        RespuestaUsuarioEscritorio respuesta = UbicacionDAO.registrarUbicacionSucursal(ubicacion);
        
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Ubicación registrada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            this.cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        this.limpiarErrores();

        String errorMensaje = "Campo invalido.";
        boolean isCamposVacios = true;

        if (tfCalle.getText().isEmpty()) {
            lbCalle.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfCiudad.getText().isEmpty()) {
            lbCiudad.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfCodigoPostal.getText().isEmpty() || !Utilidades.validarCadena(tfCodigoPostal.getText(), Utilidades.CODIGO_POSTAL_PATTERN)) {
            lbCodigoPostal.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfNumero.getText().isEmpty()) {
            lbNumero.setText(errorMensaje);
            isCamposVacios = false;
        }

        return isCamposVacios;
    }

    private void limpiarErrores() {
        lbCalle.setText("");
        lbCiudad.setText("");
        lbCodigoPostal.setText("");
        lbNumero.setText("");
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfCalle.getScene().getWindow();
        stage.close();
    }
}
