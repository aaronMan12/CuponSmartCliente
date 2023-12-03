package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class FXMLAdministrarUsuariosController implements Initializable {

    private int idUsuario;
    
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
        this.configurarColumnasTabla();
    }    
    
    public void inicializarInformacion(int idUsuario) {
        this.idUsuario = idUsuario;
        consultarInformacionUsuarios();
    }
    
    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colUserName.setCellValueFactory(new PropertyValueFactory("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("correo"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
    }
    
    private void consultarInformacionUsuarios(){
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarTodosLosUsuarios();
        
        if (!respuesta.isError()) {
            List<Usuario> listUsuarios = (List<Usuario>) respuesta.getUsuarios();
            usuarios.addAll(listUsuarios);
            tvUsuarios.setItems(usuarios);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnFormularioRegistrar(ActionEvent event) {
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
    }

    @FXML
    private void btnFormularioEliminar(ActionEvent event) {
    }

    @FXML
    private void btnFormularioBuscar(ActionEvent event) {
    } 
}
