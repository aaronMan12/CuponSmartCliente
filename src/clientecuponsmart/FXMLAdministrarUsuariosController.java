package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Roll;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdministrarUsuariosController implements Initializable {

    private ObservableList<Usuario> usuarios;

    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCurp;
    @FXML
    private TableColumn colEmail;
    @FXML
    private ComboBox cbBusqueda;
    @FXML
    private TableColumn colUserName;
    @FXML
    private TextField tfBuscarUsuario;
    @FXML
    private TableColumn colRoll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
        this.configurarColumnasTabla();
    }

    @FXML
    private void btnFormularioRegistrar(ActionEvent event) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUsuario.fxml"));
            Parent vista = vistaLoad.load();

            FXMLRegistrarUsuarioController controlador = vistaLoad.getController();
            controlador.inicializarInformacionRegistro();

            Stage stage = new Stage();
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.setTitle("Registrar usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
        int posicionSeleccionada = tvUsuarios.getSelectionModel().getSelectedIndex();
        if (posicionSeleccionada != -1) {
            Usuario usuario = usuarios.get(posicionSeleccionada);

            try {
                FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUsuario.fxml"));
                Parent vista = vistaLoad.load();

                FXMLRegistrarUsuarioController controlador = vistaLoad.getController();
                controlador.inicializarInformacion(usuario);

                Stage stage = new Stage();
                Scene scenaAdmin = new Scene(vista);
                stage.setScene(scenaAdmin);
                stage.setTitle("Actualizar usuario");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de usuario", "Para poder modificar debes seleccionar un usuario de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnFormularioEliminar(ActionEvent event) {
        int posicionSeleccionada = tvUsuarios.getSelectionModel().getSelectedIndex();

        if (posicionSeleccionada != -1) {
            Usuario usuario = usuarios.get(posicionSeleccionada);
            RespuestaUsuarioEscritorio mensaje = UsuarioDAO.eliminarUsuario(usuario.getIdUsuario());

            if (!mensaje.isError()) {
                Utilidades.mostrarAlertaSimple("Usuario eliminado", mensaje.getContenido(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getContenido(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de usuario", "Para poder eliminar debes seleccionar un usuario de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnFormularioBuscar(ActionEvent event) {
    }

    public void inicializarInformacion(int idUsuario) {
        consultarInformacionUsuarios(idUsuario);
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colUserName.setCellValueFactory(new PropertyValueFactory("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("correo"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
    }

    private void consultarInformacionUsuarios(Integer idUsuario) {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarTodosLosUsuarios(idUsuario);

        if (!respuesta.isError()) {
            List<Usuario> listUsuarios = (List<Usuario>) respuesta.getUsuarios();
            usuarios.addAll(listUsuarios);
            tvUsuarios.setItems(usuarios);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

}
