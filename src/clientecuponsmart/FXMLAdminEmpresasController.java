package clientecuponsmart;

import clientecuponsmart.modelo.dao.EmpresaDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.Empresa;
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

public class FXMLAdminEmpresasController implements Initializable {

    // CONSTANTES
    private final int BUSQUEDA_POR_NOMBRE = 1;
    private final int BUSQUEDA_POR_RFC = 2;
    private final int BUSQUEDA_POR_REPRESENTANTE = 3;

    private ObservableList<Empresa> empresas;
    private FilteredList<Empresa> filteredListEmpresas;

    private ObservableList<Busqueda> empresasBusqueda;
    private Integer idBusquedaSeleccion;

    @FXML
    private TableView<Empresa> tvEmpresas;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colNombreComercial;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TextField tfBuscarEmpresa;
    @FXML
    private TableColumn colRepresentante;
    @FXML
    private TableColumn colRFC;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private ComboBox cbBusqueda;
    @FXML
    private TableColumn colDireccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresas = FXCollections.observableArrayList();
        empresasBusqueda = FXCollections.observableArrayList();
        this.configurarColumnasTabla();
        this.cargarInformacionBusqueda();
        this.configurarSeleccionBusqueda();
        this.configurartfBuscarEmpresa();
    }

    @FXML
    private void btnFormularioRegistrar(ActionEvent event) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarEmpresa.fxml"));
            Parent vista = vistaLoad.load();
            FXMLRegistrarEmpresaController controlador = vistaLoad.getController();
            controlador.inicializarFormularioRegistrarEmpresa();
            Stage stage = new Stage();
            Scene escenaAdmin = new Scene(vista);
            stage.setScene(escenaAdmin);
            stage.setTitle("Vista de usuario general");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.consultarInformacionEmpresas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnFormularioEditar(ActionEvent event) {
        int posocionSeleccionada = tvEmpresas.getSelectionModel().getSelectedIndex();
        if (posocionSeleccionada != -1) {
            try {
                FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarEmpresa.fxml"));
                Parent vista = vistaLoad.load();
                FXMLRegistrarEmpresaController controlador = vistaLoad.getController();
                controlador.inicializarUsuarioGeneral(filteredListEmpresas.get(posocionSeleccionada));
                Stage stage = new Stage();
                Scene escenaAdmin = new Scene(vista);
                stage.setScene(escenaAdmin);
                stage.setTitle("Vista de usuario general");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                this.consultarInformacionEmpresas();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección de empresa", "Para poder editar una empresa debes deleccionar una", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnFormularioEliminar(ActionEvent event) {
        int posicionSeleccionada = tvEmpresas.getSelectionModel().getSelectedIndex();

        if (posicionSeleccionada == -1) {
            Utilidades.mostrarAlertaSimple("Selección de empresa", "Para poder eliminar debes seleccionar una empresa de la tabla", Alert.AlertType.WARNING);
            return;
        }

        Empresa empresa = filteredListEmpresas.get(posicionSeleccionada);
        RespuestaUsuarioEscritorio mensaje = EmpresaDAO.eliminarEmpresa(empresa.getIdEmpresa(), empresa.getIdUbicacion());

        if (mensaje.isError()) {
            Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getContenido(), Alert.AlertType.ERROR);
            return;
        }

        this.consultarInformacionEmpresas();
        Utilidades.mostrarAlertaSimple("Empresa eliminada", mensaje.getContenido(), Alert.AlertType.INFORMATION);
    }

    private void configurarColumnasTabla() {
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNombreComercial.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colRFC.setCellValueFactory(new PropertyValueFactory("RFC"));
        colRepresentante.setCellValueFactory(new PropertyValueFactory("nombreRepresentante"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }

    public void inicializarInformacionGeneral() {
        this.consultarInformacionEmpresas();
    }

    private void consultarInformacionEmpresas() {
        RespuestaUsuarioEscritorio respuesta = EmpresaDAO.buscarTodasLasEmpresas();
        empresas.clear();

        List<Empresa> listEmpresas = (List<Empresa>) respuesta.getEmpresas();

        empresas.addAll(listEmpresas);
        filteredListEmpresas = new FilteredList<>(empresas);
        tvEmpresas.setItems(filteredListEmpresas);

        this.desabilitarBusqueda();
    }

    private void desabilitarBusqueda() {
        
        if (empresas.isEmpty()) {
            this.tfBuscarEmpresa.setDisable(true);
            this.cbBusqueda.setDisable(true);
        } else {
            this.tfBuscarEmpresa.setDisable(false);
            this.cbBusqueda.setDisable(false);
        }
    }

    private void cargarInformacionBusqueda() {
        empresasBusqueda.addAll(new Busqueda(this.BUSQUEDA_POR_NOMBRE, "Buscar por nombre"),
                new Busqueda(this.BUSQUEDA_POR_REPRESENTANTE, "Buscar por representante"),
                new Busqueda(this.BUSQUEDA_POR_RFC, "Busqueda por RFC"));
        cbBusqueda.setItems(empresasBusqueda);
    }

    private void configurarSeleccionBusqueda() {
        cbBusqueda.valueProperty().addListener(new ChangeListener<Busqueda>() {
            @Override
            public void changed(ObservableValue<? extends Busqueda> observable, Busqueda oldValue, Busqueda newValue) {
                idBusquedaSeleccion = newValue.getIdBusqueda();
                tfBuscarEmpresa.setText("");
            }
        });
    }

    private void configurartfBuscarEmpresa() {
        tfBuscarEmpresa.textProperty().addListener((textObservable, oldText, newText) -> {
            if (newText.isEmpty()) {
                filteredListEmpresas.setPredicate(null);
                return;
            }
            if (idBusquedaSeleccion == null) {
                tfBuscarEmpresa.setText("");
                Utilidades.mostrarAlertaSimple("Error", "Por favor selecciona un tipo de busqueda", Alert.AlertType.ERROR);
                return;
            }
            buscarEmpresas(idBusquedaSeleccion, newText);
        });
    }

    private void buscarEmpresas(int idBusqueda, String busqueda) {
        Predicate<Empresa> predicado = new Predicate<Empresa>() {
            @Override
            public boolean test(Empresa empresa) {
                switch (idBusqueda) {
                    case BUSQUEDA_POR_NOMBRE:
                        return empresa.getNombre().equals(busqueda);
                    case BUSQUEDA_POR_REPRESENTANTE:
                        return empresa.getNombreRepresentante().equals(busqueda);
                    default:
                        return empresa.getRFC().equals(busqueda);
                }
            }
        };
        filteredListEmpresas.setPredicate(predicado);
    }

}
