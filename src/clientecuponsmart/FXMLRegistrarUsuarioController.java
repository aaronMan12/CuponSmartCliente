package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
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
                usuarioEditado.setNombre(this.tfNombre.getText());
                usuarioEditado.setApellidoPaterno(this.tfApellidoPaterno.getText());
                usuarioEditado.setApellidoMaterno(this.tfApellidoMaterno.getText());
                usuarioEditado.setCorreo(this.tfEmail.getText());
                usuarioEditado.setCurp(this.tfCurp.getText());
                usuarioEditado.setUserName(this.tfUserName.getText());
                usuarioEditado.setContrasenia(this.tfContrasenia.getText());
                usuarioEditado.setIdUsuario(this.usuario.getIdUsuario());
                
                editarUsuario(usuarioEditado);
            } else {
                // TODO registrar 
            }
        }
    }

    // Registrar
    private boolean validarCampos() {
        this.limpiarErrores();
        
        String errorMensaje = "Campo invalido";
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
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuario = usuario;
        this.cargarInformacion();
        this.ocultarCampos();
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
     
    private void editarUsuario (Usuario usuario) {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.editarUsuario(usuario);
        
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Paciente actualizado", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
    /*
    * TODO
    ** EN LA PANTALLA DE MODULO USUARIOS DEBO DE IMPLEMENTAR LAS BUSQUEDAS 
    ** EN LA PANTALLA DE REGISTRAR DEBO DE IMPLEMENTAR LA CARGA DE LOS COMBO BOX
    ** EN LA PANTALLA DE MODULO USUARIOS DEBO DE IMPLEMENTAR EL LLEVADO DE LA BUSQUEDA POR...
    */
}
