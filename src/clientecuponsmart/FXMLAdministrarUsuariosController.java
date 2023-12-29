package clientecuponsmart;

import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

    // CONSTANTES
    private final int BUSQUEDA_POR_NOMBRE = 1;
    private final int BUSQUEDA_POR_USERNAME = 2;
    private final int BUSQUEDA_POR_ROL = 3;

    private ObservableList<Usuario> usuarios;
    private FilteredList<Usuario> filteredListUsuarios;

    private ObservableList<Busqueda> usuariosBusqueda;
    private Integer idBusquedaSeleccion;
    private Integer idUsuario;

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
    @FXML
    private TableColumn colEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
        usuariosBusqueda = FXCollections.observableArrayList();
        this.configurarColumnasTabla();
        this.cargarInformacionBusqueda();
        this.configurarSeleccionBusqueda();
        this.configurarTfBuscarUsuario();
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
            this.consultarInformacionUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
        int posicionSeleccionada = tvUsuarios.getSelectionModel().getSelectedIndex();
        if (posicionSeleccionada != -1) {
            Usuario usuario = filteredListUsuarios.get(posicionSeleccionada);

            try {
                FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUsuario.fxml"));
                Parent vista = vistaLoad.load();

                FXMLRegistrarUsuarioController controlador = vistaLoad.getController();
                controlador.inicializarInformacion(usuario);

                Stage stage = new Stage();
                Scene scenaAdmin = new Scene(vista);
                stage.setScene(scenaAdmin);
                stage.setTitle("Editar usuario");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                this.consultarInformacionUsuarios();
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
            Usuario usuario = filteredListUsuarios.get(posicionSeleccionada);
            RespuestaUsuarioEscritorio mensaje = UsuarioDAO.eliminarUsuario(usuario.getIdUsuario());

            if (!mensaje.isError()) {
                this.consultarInformacionUsuarios();
                Utilidades.mostrarAlertaSimple("Usuario eliminado", mensaje.getContenido(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getContenido(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de usuario", "Para poder eliminar debes seleccionar un usuario de la tabla", Alert.AlertType.WARNING);
        }
    }

    public void inicializarInformacion(int idUsuario) {
        this.idUsuario = idUsuario;
        this.consultarInformacionUsuarios();
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colUserName.setCellValueFactory(new PropertyValueFactory("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("correo"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        colRoll.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));
    }

    private void consultarInformacionUsuarios() {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarTodosLosUsuarios(this.idUsuario);
        usuarios.clear();
        
        List<Usuario> listUsuarios = (List<Usuario>) respuesta.getUsuarios();
        
        usuarios.addAll(listUsuarios);
        filteredListUsuarios = new FilteredList<>(usuarios);
        tvUsuarios.setItems(filteredListUsuarios);
        this.desabilitarBusqueda();
    }

    private void desabilitarBusqueda() {

        if (usuarios.isEmpty()) {
            this.tfBuscarUsuario.setDisable(true);
            this.cbBusqueda.setDisable(true);
        } else {
            this.tfBuscarUsuario.setDisable(false);
            this.cbBusqueda.setDisable(false);
        }
    }

    private void cargarInformacionBusqueda() {
        usuariosBusqueda.addAll(new Busqueda(this.BUSQUEDA_POR_NOMBRE, "Buscar por nombre"),
                new Busqueda(this.BUSQUEDA_POR_USERNAME, "Buscar por nombre de usuario"),
                new Busqueda(this.BUSQUEDA_POR_ROL, "Buscar por rol"));
        cbBusqueda.setItems(usuariosBusqueda);
    }

    private void configurarSeleccionBusqueda() {

        cbBusqueda.valueProperty().addListener(new ChangeListener<Busqueda>() {
            @Override
            public void changed(ObservableValue<? extends Busqueda> observable, Busqueda oldValue, Busqueda newValue) {
                idBusquedaSeleccion = newValue.getIdBusqueda();
                tfBuscarUsuario.setText("");
            }
        });
    }

    private void configurarTfBuscarUsuario() {
        tfBuscarUsuario.textProperty().addListener((textObservable, oldText, newText) -> {
            if (newText.isEmpty()) {
                filteredListUsuarios.setPredicate(null);
                return;
            }
            if (idBusquedaSeleccion == null) {
                tfBuscarUsuario.setText("");
                Utilidades.mostrarAlertaSimple("Error", "Por favor selecciona un tipo de busqueda", Alert.AlertType.ERROR);
                return;
            }
            buscarUsuarios(idBusquedaSeleccion, newText);
        });
    }

    private void buscarUsuarios(int idBusqueda, String busqueda) {
        Predicate<Usuario> predicado = new Predicate<Usuario>() {
            @Override
            public boolean test(Usuario usuario) {
                switch (idBusqueda) {
                    case BUSQUEDA_POR_NOMBRE:
                        return usuario.getNombre().equals(busqueda);
                    case BUSQUEDA_POR_USERNAME:
                        return usuario.getUserName().equals(busqueda);
                    default:
                        return usuario.getNombreRol().equals(busqueda);
                }
            }
        };
        filteredListUsuarios.setPredicate(predicado);
    }

}
