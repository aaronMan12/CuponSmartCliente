package clientecuponsmart;

import clientecuponsmart.modelo.dao.PromocionDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.Promocion;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Utilidades;
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

public class FXMLAdminPromocionesController implements Initializable {

    //CONSTANTES PARA FILTRAR
    private final int BUSQUEDA_POR_FECHA_INICIO = 1;
    private final int BUSQUEDA_POR_FECHA_FIN = 2;
    private final int BUSQUEDA_POR_NOMBRE = 3;

    private ObservableList<Promocion> promociones;
    private FilteredList<Promocion> filteredListPromociones;

    private ObservableList<Busqueda> promocionesBusqueda;
    private Integer idBusquedaSeleccion;

    private Integer idEmpresa;
    private Integer idUsuario;

    @FXML
    private ComboBox cbBusqueda;
    @FXML
    private TableView tbPromociones;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCategoria;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaFin;
    @FXML
    private TableColumn colTipoPromocion;
    @FXML
    private TableColumn colNoCupones;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TextField tfBuscarPromocion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        promociones = FXCollections.observableArrayList();
        promocionesBusqueda = FXCollections.observableArrayList();
        configurarColumnasTabla();
        cargarInformacionBusquedaComboBox();
        configurarSeleccioBusquedaComboBox();
        configurarTfBusquedaPromocion();
    }

    @FXML
    private void btnFormularioRegistrar(ActionEvent event) {
        try {
            if (idEmpresa != null) {
                FXMLLoader vistaload = new FXMLLoader(getClass().getResource("FXMLRegistrarPromocion.fxml"));
                Parent vista = vistaload.load();
                FXMLRegistrarPromocionController controlador = vistaload.getController();
                controlador.inicializarInformacionComercialRegistro(idUsuario);
                Stage stage = new Stage();
                Scene escenaAdmin = new Scene(vista);
                stage.setScene(escenaAdmin);
                stage.setTitle("Registrar promoción");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                this.cargarInformacionComercial(idEmpresa);
            } else {
                FXMLLoader vistaload = new FXMLLoader(getClass().getResource("FXMLRegistrarPromocion.fxml"));
                Parent vista = vistaload.load();
                FXMLRegistrarPromocionController controlador = vistaload.getController();
                controlador.inicializarInformacionGeneralRegistro();
                Stage stage = new Stage();
                Scene escenaAdmin = new Scene(vista);
                stage.setScene(escenaAdmin);
                stage.setTitle("Registrar promoción");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                this.cargarInformacionGeneral();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
    }

    @FXML
    private void btnFormularioEliminar(ActionEvent event) {
        int posicionSeleccionada = tbPromociones.getSelectionModel().getSelectedIndex();

        if (posicionSeleccionada != -1) {
            Promocion promocion = filteredListPromociones.get(posicionSeleccionada);

            RespuestaUsuarioEscritorio respuesta = PromocionDAO.eliminarPromocion(promocion.getIdPromocion());

            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Promocion Eliminada", respuesta.getContenido(), Alert.AlertType.INFORMATION);
                if (idEmpresa != null) {
                    this.cargarInformacionComercial(idEmpresa);
                } else {
                    this.cargarInformacionGeneral();
                }
            } else {
                Utilidades.mostrarAlertaSimple("Eliminación fallida", respuesta.getContenido(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", "Para eliminar una promoción debes seleccionar una", Alert.AlertType.ERROR);
        }
    }

    public void inicializarTablaComercial(Integer idEmpresa, Integer idUsuario) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        cargarInformacionComercial(this.idEmpresa);
    }

    public void inicializarTablaGeneral() {
        cargarInformacionGeneral();
    }

    private void cargarInformacionComercial(Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarPromocionesEmpresa(idEmpresa);
        promociones.clear();

        List<Promocion> listPromociones = (List<Promocion>) respuesta.getPromociones();

        promociones.addAll(listPromociones);
        filteredListPromociones = new FilteredList<>(promociones);
        tbPromociones.setItems(filteredListPromociones);
    }

    private void cargarInformacionGeneral() {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarTodasLasPromociones();
        promociones.clear();

        List<Promocion> listPromociones = (List<Promocion>) respuesta.getPromociones();

        promociones.addAll(listPromociones);
        filteredListPromociones = new FilteredList<>(promociones);
        tbPromociones.setItems(filteredListPromociones);
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colNoCupones.setCellValueFactory(new PropertyValueFactory("noCuponesMaximo"));
        colTipoPromocion.setCellValueFactory(new PropertyValueFactory("tipoPromocion"));
    }

    private void cargarInformacionBusquedaComboBox() {
        promocionesBusqueda.addAll(new Busqueda(this.BUSQUEDA_POR_NOMBRE, "Buscar por nombre"),
                new Busqueda(this.BUSQUEDA_POR_FECHA_INICIO, "Buscar por fecha inicio"),
                new Busqueda(this.BUSQUEDA_POR_FECHA_FIN, "Buscar por fecha fin"));
        cbBusqueda.setItems(promocionesBusqueda);
    }

    private void configurarSeleccioBusquedaComboBox() {
        cbBusqueda.valueProperty().addListener(new ChangeListener<Busqueda>() {
            @Override
            public void changed(ObservableValue<? extends Busqueda> observable, Busqueda oldValue, Busqueda newValue) {
                if (filteredListPromociones.isEmpty()) {
                    Utilidades.mostrarAlertaSimple("Error", "No hay promociones para buscar.", Alert.AlertType.ERROR);
                    return;
                }
                idBusquedaSeleccion = newValue.getIdBusqueda();
                tfBuscarPromocion.setText("");
            }
        });
    }

    private void configurarTfBusquedaPromocion() {
        tfBuscarPromocion.textProperty().addListener((textObservable, oldText, newText) -> {
            if (newText.isEmpty()) {
                filteredListPromociones.setPredicate(null);
                return;
            }
            if (idBusquedaSeleccion == null) {
                tfBuscarPromocion.setText("");
                Utilidades.mostrarAlertaSimple("Error", "por favor seleccione un tipo de busqueda", Alert.AlertType.ERROR);
                return;
            }
            buscarpromociones(idBusquedaSeleccion, newText);
        });

    }

    private void buscarpromociones(int idBusqueda, String busqueda) {
        Predicate<Promocion> predicado = new Predicate<Promocion>() {
            @Override
            public boolean test(Promocion promocion) {
                switch (idBusqueda) {
                    case BUSQUEDA_POR_NOMBRE:
                        return promocion.getNombre().equals(busqueda);
                    case BUSQUEDA_POR_FECHA_INICIO:
                        return promocion.getFechaInicio().equals(busqueda);
                    default:
                        return promocion.getFechaFin().equals(busqueda);
                }
            }
        };
        filteredListPromociones.setPredicate(predicado);
    }
}
