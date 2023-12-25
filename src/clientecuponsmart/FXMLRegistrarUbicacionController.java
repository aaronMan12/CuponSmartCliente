package clientecuponsmart;

import clientecuponsmart.modelo.dao.UbicacionDAO;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Ubicacion;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLRegistrarUbicacionController implements Initializable {

    private Ubicacion ubicacion = null;
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
        Platform.runLater(() -> {
            Stage stage = (Stage) tfCalle.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    // Verificar si la ubicación es nula y consumir el evento si es necesario
                    if (ubicacionEsNula()) {
                        event.consume(); // Consumir el evento para evitar que la ventana se cierre
                        Utilidades.mostrarAlertaSimple(
                                "Error de registro",
                                "Para poder hacer valido el registro de la empresa debes agregar su ubicación",
                                Alert.AlertType.ERROR);

                        Utilidades.mostrarAlertaSimple(
                                "Error de registro",
                                "Para poder hacer valido el registro de la empresa debes agregar su ubicación",
                                Alert.AlertType.ERROR);
                    }
                }
            });
        });
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (this.validarCampos()) {
            if (idSucursal != null) {
                if (ubicacion != null) {
                    Ubicacion ubicacionEditada = new Ubicacion();
                    ubicacionEditada.setCalle(this.tfCalle.getText());
                    ubicacionEditada.setNumero(Integer.parseInt(this.tfNumero.getText()));
                    ubicacionEditada.setCodigoPostal(this.tfCodigoPostal.getText());
                    ubicacionEditada.setCiudad(this.tfCiudad.getText());
                    ubicacionEditada.setIdUbicacion(this.ubicacion.getIdUbicacion());

                    this.editarUbicacionSucursal(ubicacionEditada);
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
                if (ubicacion != null) {
                    Ubicacion ubicacionEditadaEmpresa = new Ubicacion();
                    ubicacionEditadaEmpresa.setCalle(this.tfCalle.getText());
                    ubicacionEditadaEmpresa.setNumero(Integer.parseInt(this.tfNumero.getText()));
                    ubicacionEditadaEmpresa.setCodigoPostal(this.tfCodigoPostal.getText());
                    ubicacionEditadaEmpresa.setCiudad(this.tfCiudad.getText());
                    ubicacionEditadaEmpresa.setIdUbicacion(this.ubicacion.getIdUbicacion());
                    editarUbicaciónEmpresa(ubicacionEditadaEmpresa);
                } else {
                    Ubicacion ubicacionNueva = new Ubicacion();
                    ubicacionNueva.setCalle(this.tfCalle.getText());
                    ubicacionNueva.setNumero(Integer.parseInt(this.tfNumero.getText()));
                    ubicacionNueva.setCodigoPostal(this.tfCodigoPostal.getText());
                    ubicacionNueva.setCiudad(this.tfCiudad.getText());
                    ubicacionNueva.setIdEmpresa(idEmpresa);
                    registrarUbicacionEmpresa(ubicacionNueva);

                }
            }
        }
    }

    public void inicializarEditarSucursal(Integer idUbicacion) {
        RespuestaUsuarioEscritorio respuesta = UbicacionDAO.buscarUbicacion(idUbicacion);
        if (!respuesta.isError()) {
            this.ubicacion = respuesta.getUbicacion();
            this.cargarInformacion();
            this.idSucursal = -1;
        } else {
            Utilidades.mostrarAlertaSimple("Error al obtener ubicación.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    public void inicializarRegistroSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void inicializarRegistroSucursalEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    private void editarUbicacionSucursal(Ubicacion ubicacion) {
        RespuestaUsuarioEscritorio respuesta = UbicacionDAO.editarUbicacion(ubicacion);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Ubicación editada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
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

    private void editarUbicaciónEmpresa(Ubicacion ubicacionEditadaEmpresa) {
        RespuestaUsuarioEscritorio respuesta = UbicacionDAO.editarUbicacion(ubicacionEditadaEmpresa);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Ubicación editada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void registrarUbicacionEmpresa(Ubicacion ubicacionNueva) {
        RespuestaUsuarioEscritorio respuesta = UbicacionDAO.registrarUbicacionEmpresa(ubicacionNueva);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Ubicación registrada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            this.cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void cargarInformacion() {
        this.tfCalle.setText(this.ubicacion.getCalle());
        this.tfCiudad.setText(this.ubicacion.getCiudad());
        this.tfCodigoPostal.setText(this.ubicacion.getCodigoPostal());
        this.tfNumero.setText(String.valueOf(this.ubicacion.getNumero()));
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

    private boolean ubicacionEsNula() {
        return ubicacion == null;
    }

}
