package clientecuponsmart;

import clientecuponsmart.modelo.dao.SucursalDAO;
import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Sucursal;
import clientecuponsmart.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLRegistrarSucursalController implements Initializable {

    private Sucursal sucursal;
    private Integer idSucursalRegistrado;

    private ObservableList<Empresa> empresas;
    private Integer idEmpresa;

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbColonia;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Label lbTelefono;
    @FXML
    private Label lbEncargado;
    @FXML
    private TextField tfEncargado;
    @FXML
    private TextField tfLongitud;
    @FXML
    private Label lbLongitud;
    @FXML
    private Label lbLatitud;
    @FXML
    private TextField tfLatitud;
    @FXML
    private ComboBox cbEmpresa;
    @FXML
    private Button btnUbicacion;
    @FXML
    private Label lbEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (this.validarCampos()) {
            if (this.sucursal != null) {
                Sucursal sucursalEditada = new Sucursal();
                sucursalEditada.setIdSucursal(this.sucursal.getIdSucursal());
                sucursalEditada.setIdEmpresa(this.idEmpresa);
                sucursalEditada.setNombre(this.tfNombre.getText());
                sucursalEditada.setColonia(this.tfColonia.getText());
                sucursalEditada.setTelefono(this.tfTelefono.getText());
                sucursalEditada.setLatitud(Float.parseFloat(this.tfLatitud.getText()));
                sucursalEditada.setLongitud(Float.parseFloat(this.tfLongitud.getText()));
                sucursalEditada.setNombreEncargado(this.tfEncargado.getText());

                this.editarSucursal(sucursalEditada);
            } else {
                Sucursal sucursalNueva = new Sucursal();
                sucursalNueva.setNombre(this.tfNombre.getText());
                sucursalNueva.setColonia(this.tfColonia.getText());
                sucursalNueva.setTelefono(this.tfTelefono.getText());
                sucursalNueva.setLatitud(Float.parseFloat(this.tfLatitud.getText()));
                sucursalNueva.setLongitud(Float.parseFloat(this.tfLongitud.getText()));
                sucursalNueva.setNombreEncargado(this.tfEncargado.getText());
                sucursalNueva.setIdEmpresa(this.idEmpresa);

                this.registrarSucursal(sucursalNueva);
                this.irPantallaUbicacion(idSucursalRegistrado);
            }
        }
    }

    @FXML
    private void btnIrAgregarUbicacion(ActionEvent event) {
        if (this.sucursal != null) {
            this.irPantallaUbicacionEditar(sucursal.getIdUbicacion());
        }
    }

    public void inicializarInformacion(Sucursal sucursal) {
        this.sucursal = sucursal;
        this.cargarInformacion();
        this.cargarInformacionEmpresas();
        this.configurarSeleccionEmpresa();
        this.seleccionarEmpresaEdicion(sucursal.getIdEmpresa());
    }

    public void inicializarInformacionRegistro() {
        this.cargarInformacionEmpresas();
        this.configurarSeleccionEmpresa();
        this.btnUbicacion.setVisible(false);
    }

    private void cargarInformacionEmpresas() {
        empresas = FXCollections.observableArrayList();
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarEmpresas();

        List<Empresa> empresa = respuesta.getEmpresas();
        empresas.addAll(empresa);
        cbEmpresa.setItems(empresas);
    }

    private void configurarSeleccionEmpresa() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue) {
                idEmpresa = newValue.getIdEmpresa();
            }
        });
    }

    private void seleccionarEmpresaEdicion(Integer idEmpresaAsociada) {
        if (!empresas.isEmpty()) {
            Optional<Empresa> empresaAsociada = empresas.stream()
                    .filter(empresa -> empresa.getIdEmpresa() == idEmpresaAsociada)
                    .findFirst();
            empresaAsociada.ifPresent(empresa -> {
                cbEmpresa.getSelectionModel().select(empresa);
                idEmpresa = empresa.getIdEmpresa();
            });
        }
    }

    private void cargarInformacion() {
        this.tfColonia.setText(this.sucursal.getColonia());
        this.tfEncargado.setText(this.sucursal.getNombreEncargado());
        this.tfLatitud.setText(Float.toString(this.sucursal.getLatitud()));
        this.tfLongitud.setText(Float.toString(this.sucursal.getLongitud()));
        this.tfNombre.setText(this.sucursal.getNombre());
        this.tfTelefono.setText(this.sucursal.getTelefono());
    }

    private boolean validarCampos() {
        this.limpiarErrores();

        String errorMensaje = "Campo invalido.";
        boolean isCamposVacios = true;

        if (tfColonia.getText().isEmpty()) {
            lbColonia.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfEncargado.getText().isEmpty()) {
            lbEncargado.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfLatitud.getText().isEmpty() || !this.validarNumero(tfLatitud.getText())) {
            lbLatitud.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfLongitud.getText().isEmpty() || !this.validarNumero(tfLongitud.getText())) {
            lbLongitud.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfNombre.getText().isEmpty()) {
            lbNombre.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfTelefono.getText().isEmpty() || !Utilidades.validarCadena(tfTelefono.getText(), Utilidades.TELEFONO_PATTERN)) {
            lbTelefono.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (idEmpresa == null) {
            lbEmpresa.setText(errorMensaje);
            isCamposVacios = false;
        }

        return isCamposVacios;
    }

    private void limpiarErrores() {
        this.lbColonia.setText("");
        this.lbEmpresa.setText("");
        this.lbEncargado.setText("");
        this.lbLatitud.setText("");
        this.lbLongitud.setText("");
        this.lbNombre.setText("");
        this.lbTelefono.setText("");
    }

    private boolean validarNumero(String input) {
        try {
            double flotante = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void registrarSucursal(Sucursal sucursal) {
        RespuestaUsuarioEscritorio respuesta = SucursalDAO.registrarSucursal(sucursal);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Sucursal registrada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            this.idSucursalRegistrado = respuesta.getSucursal().getIdSucursal();
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void editarSucursal(Sucursal sucursal) {
        RespuestaUsuarioEscritorio respuesta = SucursalDAO.editarSucursal(sucursal);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Sucursal editada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void irPantallaUbicacionEditar(Integer idUbicacion) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUbicacion.fxml"));
            Parent vista = vistaLoad.load();

            FXMLRegistrarUbicacionController controlador = vistaLoad.getController();
            controlador.inicializarEditarSucursal(idUbicacion);

            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Editar ubicación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.cerrarPantalla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irPantallaUbicacion(Integer idSucursal) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUbicacion.fxml"));
            Parent vista = vistaLoad.load();

            FXMLRegistrarUbicacionController controlador = vistaLoad.getController();
            controlador.inicializarRegistroSucursal(idSucursal);

            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Registrar ubicación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.cerrarPantalla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

}
