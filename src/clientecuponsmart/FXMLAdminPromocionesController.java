package clientecuponsmart;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXMLAdminPromocionesController implements Initializable {

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
    
}
