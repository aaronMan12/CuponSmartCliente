package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Roll;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Constantes;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrarUsuarioController implements Initializable {

    private Usuario usuario;

    private ObservableList<Roll> rolles;
    private ObservableList<Empresa> empresas;

    private Integer idRoll;
    private Integer idEmpresa;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfContrasenia;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidoPaterno;
    @FXML
    private Label lbCurp;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbApellidoMaterno;
    @FXML
    private Label lbContrasenia;
    @FXML
    private Label lbEmpresa;
    @FXML
    private Label lbRoll;
    @FXML
    private ComboBox cbRoll;
    @FXML
    private ComboBox cbEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (this.validarCampos()) {
            if (this.usuario != null) {
                Usuario usuarioEditado = new Usuario();
                usuarioEditado.setNombre(this.tfNombre.getText().trim());
                usuarioEditado.setApellidoPaterno(this.tfApellidoPaterno.getText().trim());
                usuarioEditado.setApellidoMaterno(this.tfApellidoMaterno.getText().trim());
                usuarioEditado.setCorreo(this.tfEmail.getText());
                usuarioEditado.setCurp(this.tfCurp.getText());
                usuarioEditado.setUserName(this.tfUserName.getText().trim());
                usuarioEditado.setContrasenia(this.tfContrasenia.getText());
                usuarioEditado.setIdUsuario(this.usuario.getIdUsuario());

                editarUsuario(usuarioEditado);
            } else {
                Usuario usuarioNuevo = new Usuario();
                usuarioNuevo.setNombre(this.tfNombre.getText().trim());
                usuarioNuevo.setApellidoPaterno(this.tfApellidoPaterno.getText().trim());
                usuarioNuevo.setApellidoMaterno(this.tfApellidoMaterno.getText().trim());
                usuarioNuevo.setCorreo(this.tfEmail.getText());
                usuarioNuevo.setCurp(this.tfCurp.getText());
                usuarioNuevo.setUserName(this.tfUserName.getText().trim());
                usuarioNuevo.setContrasenia(this.tfContrasenia.getText());
                usuarioNuevo.setIdRollUsuario(idRoll);

                if (idRoll == Constantes.ID_ROL_COMERCIAL) {
                    usuarioNuevo.setIdEmpresa(idEmpresa);

                    this.registrarUsuarioComercial(usuarioNuevo);
                } else {
                    this.registrarUsuarioGeneral(usuarioNuevo);
                }
            }
        }
    }

    private boolean validarCampos() {
        this.limpiarErrores();

        String errorMensaje = "Campo invalido.";
        boolean isCamposVacios = true;

        if (tfNombre.getText().isEmpty()) {
            lbNombre.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfApellidoPaterno.getText().isEmpty()) {
            lbApellidoPaterno.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfApellidoMaterno.getText().isEmpty()) {
            lbApellidoMaterno.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfEmail.getText().isEmpty() || !Utilidades.validarCadena(tfEmail.getText(), Utilidades.EMAIL_PATTERN)) {
            lbEmail.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfCurp.getText().isEmpty() || !Utilidades.validarCadena(tfCurp.getText(), Utilidades.CURP_PATTERN)) {
            lbCurp.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfUserName.getText().isEmpty()) {
            lbUserName.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (tfContrasenia.getText().isEmpty()) {
            lbContrasenia.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (idRoll == null) {
            lbRoll.setText(errorMensaje);
            isCamposVacios = false;
        }

        if (idEmpresa == null) {
            lbEmpresa.setText(errorMensaje);
            isCamposVacios = false;
        }

        return isCamposVacios;
    }

    private void limpiarErrores() {
        lbNombre.setText("");
        lbApellidoPaterno.setText("");
        lbApellidoMaterno.setText("");
        lbEmail.setText("");
        lbCurp.setText("");
        lbUserName.setText("");
        lbContrasenia.setText("");
        lbEmpresa.setText("");
        lbRoll.setText("");
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuario = usuario;
        this.idEmpresa = -1;
        this.idRoll = -1;
        this.cargarInformacion();
        this.ocultarCampos();
    }

    public void inicializarInformacionRegistro() {
        this.cargarInformacionRolles();
        this.configurarSeleccionRoll();
        this.cargarInformacionEmpresas();
        this.configurarSeleccionEmpresa();
    }

    private void cargarInformacionRolles() {
        rolles = FXCollections.observableArrayList();
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarRolles();

        if (!respuesta.isError()) {
            List<Roll> roll = respuesta.getRoles();
            rolles.addAll(roll);
            cbRoll.setItems(rolles);
        } else {
            Utilidades.mostrarAlertaSimple("Error al mostrar los rolles.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
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

    private void cargarInformacion() {
        this.tfNombre.setText(this.usuario.getNombre());
        this.tfApellidoPaterno.setText(this.usuario.getApellidoPaterno());
        this.tfApellidoMaterno.setText(this.usuario.getApellidoMaterno());
        this.tfEmail.setText(this.usuario.getCorreo());
        this.tfCurp.setText(this.usuario.getCurp());
        this.tfUserName.setText(this.usuario.getUserName());
        this.tfContrasenia.setText(this.usuario.getContrasenia());
    }

    private void ocultarCampos() {
        this.cbRoll.setVisible(false);
        this.cbEmpresa.setVisible(false);
    }

    private void editarUsuario(Usuario usuario) {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.editarUsuario(usuario);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Usuario actualizado.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void registrarUsuarioComercial(Usuario usuario) {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.registrarUsuarioComercial(usuario);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Usuario registrado.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void registrarUsuarioGeneral(Usuario usuario) {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.registrarUsuarioGeneral(usuario);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Usuario registrado.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void configurarSeleccionRoll() {
        cbRoll.valueProperty().addListener(new ChangeListener<Roll>() {
            @Override
            public void changed(ObservableValue<? extends Roll> observable, Roll oldValue, Roll newValue) {
                cbEmpresa.setVisible(true);
                idRoll = newValue.getIdRollUsuario();
                idEmpresa = null;
                if (newValue.getIdRollUsuario() == Constantes.ID_ROL_GENERAL) {
                    cbEmpresa.setVisible(false);
                    idEmpresa = -1;
                }
            }
        });
    }

    private void configurarSeleccionEmpresa() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue) {
                idEmpresa = newValue.getIdEmpresa();
            }
        });
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
    
}
