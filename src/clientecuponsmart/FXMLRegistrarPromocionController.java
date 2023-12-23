package clientecuponsmart;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class FXMLRegistrarPromocionController implements Initializable {

    @FXML
    private TextField tfNombrePromocion;
    @FXML
    private Label lbNombrePromocion;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Label lbFechaFin;
    @FXML
    private RadioButton rbDescuento;
    @FXML
    private ToggleGroup tgTipoDescuento;
    @FXML
    private RadioButton rbRebaja;
    @FXML
    private Label lbNumeroCupones;
    @FXML
    private TextField tfNumeroCupones;
    @FXML
    private TextField tfCodigo;
    @FXML
    private Label lbCodigo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private ComboBox<?> cbCategoria;
    @FXML
    private Label lbCategoria;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taRestriccion;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbRestriccion;
    @FXML
    private ImageView ivFotografia;
    @FXML
    private ListView<?> lvSucursales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void btnSeleccionarFoto(MouseEvent event) {
    }

    @FXML
    private void btnGuardarFoto(ActionEvent event) {
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }
    
}
