/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.dao.CuponDAO;
import clientecuponsmart.modelo.pojo.Busqueda;
import clientecuponsmart.modelo.pojo.Promocion;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aaron
 */
public class FXMLCuponesController implements Initializable {

    Integer idEmpresa;
    private ObservableList<Promocion> promociones;

    @FXML
    private TableView tvCupones;
    @FXML
    private TextField tfCodigoCupon;
    @FXML
    private TableColumn colCategoria;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colFechaFin;
    @FXML
    private TableColumn colNoCupones;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colTipoPromocion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        promociones = FXCollections.observableArrayList();
       
        configurarColumnasTabla();
    }

    @FXML
    private void btnCanjearCupon(ActionEvent event) {
        if (tfCodigoCupon.getText().length() == 8) {
            if (idEmpresa != null) {
                canjearCuponComercial(tfCodigoCupon.getText(), idEmpresa);
                tfCodigoCupon.setText("");
                cargarInformacionComercial(idEmpresa);
            } else {
                canjearCupon(tfCodigoCupon.getText());
                tfCodigoCupon.setText("");
                cargarInformacionGeneral();
            }

        } else {
            Utilidades.mostrarAlertaSimple("Error de código", "El código debe de tener 8 caracteres", Alert.AlertType.WARNING);
        }
    }

    public void inicializarUsuarioComercial(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
        cargarInformacionComercial(idEmpresa);
    }

    void inicializarUsuarioGeneral() {
        cargarInformacionGeneral();
    }

    private void canjearCupon(String codigo) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();

        respuesta = CuponDAO.canjearCupon(codigo);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Canjeaste un cupón", "Cupón valido aplico el descuento", Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error de canje", respuesta.getContenido(), Alert.AlertType.INFORMATION);

        }
    }

    private void canjearCuponComercial(String codigo, Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = new RespuestaUsuarioEscritorio();

        respuesta = CuponDAO.canjearCuponComercial(codigo, idEmpresa);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Canjeaste un cupón", "Cupón valido aplico el descuento", Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.INFORMATION);

        }
    }

    private void cargarInformacionComercial(Integer idEmpresa) {
        RespuestaUsuarioEscritorio respuesta = CuponDAO.cargarInformacionComercial(idEmpresa);
        promociones.clear();

        List<Promocion> listaPromociones = (List<Promocion>) respuesta.getPromociones();
        promociones.addAll(listaPromociones);
        tvCupones.setItems(promociones);
    }

    private void configurarColumnasTabla() {
        colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoPromocion.setCellValueFactory(new PropertyValueFactory("tipoPromocion"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        colNoCupones.setCellValueFactory(new PropertyValueFactory("noCuponesMaximo"));
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
    }

    private void cargarInformacionGeneral() {
        RespuestaUsuarioEscritorio respuesta = CuponDAO.cargarInformacionGeneral();
        promociones.clear();
        List<Promocion> listaPromociones = (List<Promocion>) respuesta.getPromociones();
        promociones.addAll(listaPromociones);
        tvCupones.setItems(promociones);
    }

}
