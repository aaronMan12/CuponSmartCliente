package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Sucursal;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrarSucursalController implements Initializable {

    private Sucursal sucursal;
    
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
            // TODO REGISTRAR
        }
    }

    @FXML
    private void btnIrAgregarUbicacion(ActionEvent event) {
    }
    
    public void inicializarInformacion(Sucursal sucursal) {
        this.sucursal = sucursal;
        this.cargarInformacion();
    }
    
    public void inicializarInformacionRegistro() {
        this.cargarInformacionEmpresas();
        this.configurarSeleccionEmpresa();
    }
    
    private void cargarInformacionEmpresas() {
        empresas = FXCollections.observableArrayList();
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarEmpresas();

        if (!respuesta.isError()) {
            List<Empresa> empresa = respuesta.getEmpresas();
            empresas.addAll(empresa);
            cbEmpresa.setItems(empresas);
        } else {
            Utilidades.mostrarAlertaSimple("Error al mostrar las empresas.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarSeleccionEmpresa() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue) {
                idEmpresa = newValue.getIdEmpresa();
            }
        });
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
    
    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
