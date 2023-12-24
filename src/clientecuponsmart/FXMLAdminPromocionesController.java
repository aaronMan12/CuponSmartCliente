package clientecuponsmart;

import clientecuponsmart.modelo.dao.PromocionDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.Promocion;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXMLAdminPromocionesController implements Initializable {

    private ObservableList <Promocion> promociones;
    private FilteredList <Promocion> filteredListPromociones;
    
    private ObservableList <Busqueda> promocionesBusqueda; 
    private Integer idBusquedaselección;
    
    private Integer idEmpresa;
    
    @FXML
    private DatePicker dtBuscarPromocion;
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
        // TODO
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
    
    
    public void inicializarTablaComercial(Integer idEmpresa){
        idEmpresa = idEmpresa;
        cargarImformacionComercial(this.idEmpresa);
        
    }
    
    public void inicializarTablaGeneral(){
        cargarInformacionGeneral();
    }
    
    private void cargarImformacionComercial (Integer idEmpresa){
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarPromocionesEmpresa(idEmpresa);
        promocionesBusqueda.clear();
        
        List <Promocion> listPromociones = (List<Promocion>) respuesta.getPromociones();
        
        promociones.addAll(listPromociones);
        filteredListPromociones = new FilteredList<>(promociones);
        
    }
    
    private void cargarInformacionGeneral (){
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarTodasLasPromociones();
        promocionesBusqueda.clear();
        
        List <Promocion> listPromociones = (List<Promocion>) respuesta.getPromociones();
        
        promociones.addAll(listPromociones);
        filteredListPromociones = new FilteredList<>(promociones);
    }
    
}
