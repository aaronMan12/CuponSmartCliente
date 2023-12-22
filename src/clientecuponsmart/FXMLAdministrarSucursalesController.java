package clientecuponsmart;

import clientecuponsmart.modelo.dao.SucursalDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Sucursal;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLAdministrarSucursalesController implements Initializable {

    // CONSTANTES PARA FILTRAR
    private final int BUSQUEDA_POR_NOMBRE = 1;
    private final int BUSQUEDA_POR_CALLE = 2;
    private final int BUSQUEDA_POR_NUMERO = 3;

    private ObservableList<Sucursal> sucursales;
    private FilteredList<Sucursal> filteredListSucursales;

    private ObservableList<Busqueda> sucursalesBusqueda;
    private Integer idBusquedaSeleccion;
    
    private Integer idUsuario;

    @FXML
    private TextField tfBuscarSucursal;
    @FXML
    private ComboBox cbBusqueda;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colLatitud;
    @FXML
    private TableColumn colLongitud;
    @FXML
    private TableColumn colEncargado;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableView tvSucursales;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursales = FXCollections.observableArrayList();
        sucursalesBusqueda = FXCollections.observableArrayList();
        this.configurarColumnasTabla();
        this.cargarInformacionBusqueda();
        this.configurarSeleccionBusqueda();
        this.configurarTfBuscarSucursal();
    }

    @FXML
    private void btnFormularioRegistrar(ActionEvent event) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarSucursal.fxml"));
            Parent vista = vistaLoad.load();

            FXMLRegistrarSucursalController controlador = vistaLoad.getController();
            controlador.inicializarInformacionRegistro();

            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Registrar sucursal");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.inicializarInformacionGeneral();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if (posicionSeleccionada != -1) {
            Sucursal sucursal = filteredListSucursales.get(posicionSeleccionada);

            try {
                FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarSucursal.fxml"));
                Parent vista = vistaLoad.load();

                FXMLRegistrarSucursalController controlador = vistaLoad.getController();
                controlador.inicializarInformacion(sucursal);

                Stage stage = new Stage();
                Scene scenaAdmin = new Scene(vista);
                stage.setScene(scenaAdmin);
                stage.setTitle("Editar sucursal");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (idUsuario != null) {
                    this.cargarInformacionComercial();
                } else {
                    this.inicializarInformacionGeneral();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de sucursal", "Para poder modificar debes seleccionar una sucursal de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnFormularioEliminar(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();

        if (posicionSeleccionada != -1) {
            Sucursal sucursal = filteredListSucursales.get(posicionSeleccionada);
            RespuestaUsuarioEscritorio mensaje = SucursalDAO.eliminarSucursal(sucursal.getIdSucursal(), sucursal.getIdUbicacion());

            if (!mensaje.isError()) {
                this.inicializarInformacionGeneral();
                Utilidades.mostrarAlertaSimple("Sucursal eliminada", mensaje.getContenido(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getContenido(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de sucursal", "Para poder eliminar debes seleccionar una sucursal de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void configurarColumnasTabla() {
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colEncargado.setCellValueFactory(new PropertyValueFactory("nombreEncargado"));
        colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }

    public void inicializarInformacionGeneral() {
        RespuestaUsuarioEscritorio respuesta = SucursalDAO.buscarTodasLasSucusales();

        sucursales.clear();
        List<Sucursal> listSucusales = (List<Sucursal>) respuesta.getSucursales();
        sucursales.addAll(listSucusales);
        filteredListSucursales = new FilteredList<>(sucursales);
        tvSucursales.setItems(filteredListSucursales);
    }
    
    public void inicializarInformacionComercial(Integer idUsuario) {
        this.idUsuario = idUsuario;
        this.cargarInformacionComercial();
    }
    
    private void cargarInformacionComercial() {
        this.btnEliminar.setDisable(true);
        this.btnRegistrar.setDisable(true);
        
        RespuestaUsuarioEscritorio respuesta = SucursalDAO.buscarSucursalesUsuario(this.idUsuario);

        sucursales.clear();
        List<Sucursal> listSucusales = (List<Sucursal>) respuesta.getSucursales();
        sucursales.addAll(listSucusales);
        filteredListSucursales = new FilteredList<>(sucursales);
        tvSucursales.setItems(filteredListSucursales);
    }

    private void cargarInformacionBusqueda() {
        sucursalesBusqueda.addAll(new Busqueda(this.BUSQUEDA_POR_NOMBRE, "Buscar por nombre"),
                new Busqueda(this.BUSQUEDA_POR_CALLE, "Buscar por calle"),
                new Busqueda(this.BUSQUEDA_POR_NUMERO, "Buscar por número"));
        cbBusqueda.setItems(sucursalesBusqueda);
    }

    private void configurarSeleccionBusqueda() {
        cbBusqueda.valueProperty().addListener(new ChangeListener<Busqueda>() {
            @Override
            public void changed(ObservableValue<? extends Busqueda> observable, Busqueda oldValue, Busqueda newValue) {
                if (filteredListSucursales.isEmpty()) {
                    Utilidades.mostrarAlertaSimple("Error", "No hay sucursales para buscar.", Alert.AlertType.ERROR);
                    return;
                }
                idBusquedaSeleccion = newValue.getIdBusqueda();
                tfBuscarSucursal.setText("");
            }
        });
    }

    private void configurarTfBuscarSucursal() {
        tfBuscarSucursal.textProperty().addListener((textObservable, oldText, newText) -> {
            if (newText.isEmpty()) {
                filteredListSucursales.setPredicate(null);
                return;
            }
            if (idBusquedaSeleccion == null) {
                tfBuscarSucursal.setText("");
                Utilidades.mostrarAlertaSimple("Error", "Por favor selecciona un tipo de busqueda", Alert.AlertType.ERROR);
                return;
            }
            buscarSucursales(idBusquedaSeleccion, newText);
        });
    }

    private void buscarSucursales(int idBusqueda, String busqueda) {
        Predicate<Sucursal> predicado = new Predicate<Sucursal>() {
            @Override
            public boolean test(Sucursal sucursal) {
                switch (idBusqueda) {
                    case BUSQUEDA_POR_NOMBRE:
                        return sucursal.getNombre().equals(busqueda);
                    case BUSQUEDA_POR_CALLE:
                        return sucursal.getCalle().equals(busqueda);
                    default:
                        return sucursal.getNumero().equals(busqueda);
                }
            }
        };
        filteredListSucursales.setPredicate(predicado);
    }

}
