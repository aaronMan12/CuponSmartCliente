package clientecuponsmart;

import clientecuponsmart.modelo.dao.PromocionDAO;
import clientecuponsmart.modelo.dao.SucursalDAO;
import clientecuponsmart.modelo.dao.UsuarioDAO;
import clientecuponsmart.modelo.pojo.Categoria;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.Promocion;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Sucursal;
import clientecuponsmart.utils.Constantes;
import clientecuponsmart.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FXMLRegistrarPromocionController implements Initializable {

    // OBJETOS GLOBALES
    private Promocion promocion;
    private File fotografia;

    // TODOS LOS OBSERVABLELIST
    private ObservableList<Categoria> categorias;
    private ObservableList<Sucursal> sucursales;
    private ObservableList<Empresa> empresas;
    private ObservableList<Sucursal> sucursalesRegistradasValidas;

    // TODOS LOS IDS EN CLASES Y LISTAS
    private Integer idUsuario;
    private Integer idCategoria;
    private Integer idEmpresa;
    private List<Integer> IdSucursalesValidas;

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
    private ComboBox cbCategoria;
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
    private ListView lvSucursales;
    @FXML
    private Button btnFoto;
    @FXML
    private ComboBox cbEmpresa;
    @FXML
    private Label lbPromocion;
    @FXML
    private Label lbEstatus;
    @FXML
    private Label lbFoto;
    @FXML
    private Label lbSucursal;
    @FXML
    private TextField tfPorcentajePrecio;
    @FXML
    private Label lbPorcentajePrecio;
    @FXML
    private ListView lvSucursalesValidas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // INICIALIZACIÓN DE OBSERVADORES
        this.categorias = FXCollections.observableArrayList();
        this.sucursales = FXCollections.observableArrayList();
        this.empresas = FXCollections.observableArrayList();

        // INICIALIZARCIÓN DE LISTAS
        this.IdSucursalesValidas = new ArrayList<>();

        // CONFIGURACIÓN DE COMPONENTE LISTVIEW *MULTIPLE SELECCIÓN*
        this.lvSucursales.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void btnSeleccionarFoto(MouseEvent event) {
        fotografia = cargarFoto();
        if (fotografia != null){
            mostarFotoSeleccioanda(fotografia);
        }
    }

    @FXML
    private void btnGuardarFoto(ActionEvent event) {
         if (fotografia != null) {
            RespuestaUsuarioEscritorio nuevaFoto = PromocionDAO.actualizarFoto(fotografia, promocion.getIdPromocion());
            if (!nuevaFoto.isError()) {
                Utilidades.mostrarAlertaSimple("Foto guardado", nuevaFoto.getContenido(), Alert.AlertType.INFORMATION);
                cerrarPantalla();
            } else {
                Utilidades.mostrarAlertaSimple("Error al guardar", nuevaFoto.getContenido(), Alert.AlertType.ERROR);

            }
        } else {
            Utilidades.mostrarAlertaSimple("No hay fotografía seleccionado", "Para actualizar el logo antes debes elegir una nueva imagen",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        this.obtenerSucursalesValidas();

        if (!this.camposVacios()) {
            return;
        }

        if (this.promocion == null) {
            this.registrarPromocion(this.obtenerDatosDeLosComponentes());
        } else {
            this.editarPromocion(this.obtenerDatosDeLosComponentes());
        }
    }

    // INICIALIZADORES
    public void inicializarInformacionComercialRegistro(Integer idUsuario) {
        this.idUsuario = idUsuario;
        this.desabilitarComponentesComercialRegistro();
        this.obtenerTodasLasCategorias();
        this.configurarEventoCategoria();
        this.obtenerSucursalesValidasComercialRegistro();
    }

    public void inicializarInformacionGeneralRegistro() {
        this.desabilitarComponentesGeneralRegistro();
        this.obtenerTodasLasCategorias();
        this.configurarEventoCategoria();
        this.obtenerTodasLasEmpresas();
        this.configurarEventoEmpresa();    
    }
    
    public void inicializarInformacionComercialEditar(Promocion promocion, Integer idUsuario) {
        this.sucursalesRegistradasValidas = FXCollections.observableArrayList();
        this.promocion = promocion;
        this.idUsuario = idUsuario;
        this.desabilitarComponentesComercialEditar();
        this.obtenerTodasLasCategorias();
        this.configurarEventoCategoria();
        this.cargarDatosEnLosComponentes();
        this.obtenerSucursalesRegistradasValidas(this.promocion.getIdPromocion());
        this.obtenerSucursalesValidasComercialRegistro();
        System.out.println(promocion.getIdPromocion());
        this.obtenerFotoPromocion(this.promocion.getIdPromocion());
    }
    
    public void inicializarInformacionGeneralEditar(Promocion promocion) {
        this.sucursalesRegistradasValidas = FXCollections.observableArrayList();
        this.promocion = promocion;
        this.desabilitarComponentesGeneralEditar();
        this.obtenerTodasLasCategorias();
        this.configurarEventoCategoria();
        this.cargarDatosEnLosComponentes();
        this.obtenerTodasLasEmpresas();
        this.configurarEventoEmpresa();
        this.obtenerSucursalesRegistradasValidas(this.promocion.getIdPromocion());
           System.out.println(promocion.getIdPromocion());
        this.obtenerFotoPromocion(this.promocion.getIdPromocion());
    }

    // MÉTODOS PARA DESCARGAR LOS DATOS DE LA API
    private void obtenerTodasLasCategorias() {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarTodasLasCategorias();

        this.categorias.addAll(respuesta.getCategorias());
        this.cbCategoria.setItems(this.categorias);
    }

    private void obtenerSucursalesValidasComercialRegistro() {
        RespuestaUsuarioEscritorio respuesta = SucursalDAO.buscarSucursalesUsuario(this.idUsuario);

        this.sucursales.addAll(respuesta.getSucursales());
        this.lvSucursales.setItems(this.sucursales);
    }

    private void obtenerTodasLasEmpresas() {
        RespuestaUsuarioEscritorio respuesta = UsuarioDAO.buscarEmpresas();

        this.empresas.addAll(respuesta.getEmpresas());
        this.cbEmpresa.setItems(this.empresas);
    }
    
    private void obtenerSucursalesValidasGeneralRegistro() {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarSucursalesEmpresa(this.idEmpresa);
        
        this.sucursales.clear();
        this.sucursales.addAll(respuesta.getSucursales());
        this.lvSucursales.setItems(this.sucursales);
    }

    private void obtenerSucursalesRegistradasValidas(Integer idPromocion) {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.buscarSucursalesValidasGeneralRegistro(idPromocion);
        if (!respuesta.isError()) {
            this.sucursalesRegistradasValidas.addAll(respuesta.getSucursales());
            this.lvSucursalesValidas.setItems(this.sucursalesRegistradasValidas);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.WARNING);
        }
    }
    
    // MÉTODOS DE CONFIGURACIÓN DE EVENTOS
    private void configurarEventoCategoria() {
        cbCategoria.valueProperty().addListener(new ChangeListener<Categoria>() {
            @Override
            public void changed(ObservableValue<? extends Categoria> observable, Categoria oldValue, Categoria newValue) {
                idCategoria = newValue.getIdCategoria();
            }
        });
    }
    
    private void configurarEventoEmpresa() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue) {
                idEmpresa = newValue.getIdEmpresa();
                obtenerSucursalesValidasGeneralRegistro();
            }
        });
    }

    // MÉTODOS PARA GUARDAR Y EDITAR
    private void registrarPromocion(Promocion promocionNueva) {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.registrarPromocion(promocionNueva);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Promoción registrada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarPromocion(Promocion promocionEditada) {
        RespuestaUsuarioEscritorio respuesta = PromocionDAO.editarPromocion(promocionEditada);
        
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Promoción editada.", respuesta.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar.", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    // MÉTODOS PARA OCULTAR COMPONENTES
    private void desabilitarComponentesComercialRegistro() {
        this.rbActivo.setDisable(true);
        this.rbInactivo.setDisable(true);
        this.ivFotografia.setDisable(true);
        this.btnFoto.setDisable(true);
        this.cbEmpresa.setDisable(true);
        this.lvSucursalesValidas.setDisable(true);
    }

    private void desabilitarComponentesGeneralRegistro() {
        this.rbActivo.setDisable(true);
        this.rbInactivo.setDisable(true);
        this.ivFotografia.setDisable(true);
        this.btnFoto.setDisable(true);
        this.lvSucursalesValidas.setDisable(true);
    }
    
    private void desabilitarComponentesComercialEditar() {
        this.cbEmpresa.setDisable(true);
        this.lvSucursalesValidas.setDisable(true);
    }
    
    private void desabilitarComponentesGeneralEditar() {
        this.lvSucursalesValidas.setDisable(true);
    }

    // MÉTODOS PARA OBTENER LOS DATOS DE LOS INPUTS
    private Promocion obtenerDatosDeLosComponentes() {
        Promocion promocion = new Promocion();

        promocion.setIdCategoria(this.idCategoria);
        promocion.setNombre(this.tfNombrePromocion.getText());
        promocion.setDescripcion(this.taDescripcion.getText());
        promocion.setRestriccion(this.taRestriccion.getText());
        promocion.setNoCuponesMaximo(Integer.parseInt(this.tfNumeroCupones.getText()));
        promocion.setCodigo(this.tfCodigo.getText());
        promocion.setIdSucursales(this.IdSucursalesValidas);
        promocion.setFechaInicio(this.dpFechaInicio.getValue().toString());
        promocion.setFechaFin(this.dpFechaFin.getValue().toString());

        if (this.rbDescuento.isSelected()) {
            promocion.setTipoPromocion(Constantes.PROMOCION_DESCUENTO);
            promocion.setPorcentajeDescuento(Float.parseFloat(this.tfPorcentajePrecio.getText()));
        } else {
            promocion.setTipoPromocion(Constantes.PROMOCION_REBAJADO);
            promocion.setPrecioRebajado(Float.parseFloat(this.tfPorcentajePrecio.getText()));
        }

        if (this.promocion != null) {
            promocion.setIdPromocion(this.promocion.getIdPromocion());
            if (this.rbActivo.isSelected()) {
                promocion.setEstatus(Constantes.ESTATUS_ACTIVO);
            } else {
                promocion.setEstatus(Constantes.ESTATUS_INACTIVO);
            }
        }
        
        return promocion;
    }

    // MÉTODOS PARA CARGAR LOS DATOS EN LOS INPUTS
    private void cargarDatosEnLosComponentes() {
        this.tfNombrePromocion.setText(this.promocion.getNombre());
        this.tfNumeroCupones.setText(String.valueOf(this.promocion.getNoCuponesMaximo()));
        this.tfCodigo.setText(this.promocion.getCodigo());
        this.dpFechaInicio.setValue(LocalDate.parse(this.promocion.getFechaInicio()));
        this.dpFechaFin.setValue(LocalDate.parse(this.promocion.getFechaFin()));
        this.taRestriccion.setText(this.promocion.getRestriccion());
        this.taDescripcion.setText(this.promocion.getDescripcion());
        this.obtenerFotoPromocion(this.promocion.getIdPromocion());
        if (this.promocion.getTipoPromocion().equals(Constantes.PROMOCION_DESCUENTO)) {
            this.rbDescuento.setSelected(true);
            this.tfPorcentajePrecio.setText(String.valueOf(this.promocion.getPorcentajeDescuento()));
        } else {
            this.rbRebaja.setSelected(true);
            this.tfPorcentajePrecio.setText(String.valueOf(this.promocion.getPrecioRebajado()));
        }
        if (this.promocion.getEstatus().equals(Constantes.ESTATUS_ACTIVO)) {
            this.rbActivo.setSelected(true);
        } else {
            this.rbInactivo.setSelected(true);
        }
        this.seleccionarCategoriaEdicion(this.promocion.getIdCategoria());
    }
    
    private void seleccionarCategoriaEdicion(Integer idCategoria) {
        if (!this.categorias.isEmpty()) {
            Optional<Categoria> categoriaSeleccionada = this.categorias.stream()
                    .filter(categoria -> categoria.getIdCategoria() == idCategoria)
                    .findFirst();
            categoriaSeleccionada.ifPresent(categoria -> {
                this.cbCategoria.getSelectionModel().select(categoria);
                this.idCategoria = categoria.getIdCategoria();
            });
        }
    }
    
    // MÉTODOS GENERALES
    private void limpiarErrores() {
        this.lbNombrePromocion.setText("");
        this.lbNumeroCupones.setText("");
        this.lbCodigo.setText("");
        this.lbFechaInicio.setText("");
        this.lbFechaFin.setText("");
        this.lbCategoria.setText("");
        this.lbRestriccion.setText("");
        this.lbDescripcion.setText("");
        this.lbSucursal.setText("");
        this.lbEstatus.setText("");
        this.lbPromocion.setText("");
        lbPorcentajePrecio.setText("");
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombrePromocion.getScene().getWindow();
        stage.close();
    }

    private void obtenerSucursalesValidas() {
        this.IdSucursalesValidas.clear();
        List<Sucursal> listSucursales = lvSucursales.getSelectionModel().getSelectedItems();

        for (Sucursal sucursal : listSucursales) {
            this.IdSucursalesValidas.add(sucursal.getIdSucursal());
        }
    }

    private boolean camposVacios() {
        this.limpiarErrores();
        Boolean hayCamposVacios = true;

        if (tfNombrePromocion.getText().isEmpty()) {
            lbNombrePromocion.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (tfNumeroCupones.getText().isEmpty() || !Utilidades.validarCadena(tfNumeroCupones.getText(), Utilidades.NUMERO_PATTERN)) {
            lbNumeroCupones.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (tfCodigo.getText().isEmpty() || !Utilidades.validarCadena(tfCodigo.getText(), Utilidades.CODIGO_PATTERN)) {
            lbCodigo.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (dpFechaInicio.getValue() == null) {
            lbFechaInicio.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (dpFechaFin.getValue() == null) {
            lbFechaFin.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (dpFechaInicio.getValue() != null && dpFechaFin.getValue() != null && Utilidades.validarFechas(dpFechaInicio.getValue(), dpFechaFin.getValue())) {
            lbFechaInicio.setText(Constantes.CAMPO_INVALIDO);
            lbFechaFin.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (idCategoria == null) {
            lbCategoria.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (IdSucursalesValidas.isEmpty()) {
            lbSucursal.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (tgTipoDescuento.getSelectedToggle() == null) {
            lbPromocion.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (promocion != null) {
            if (tgEstatus.getSelectedToggle() == null) {
                lbEstatus.setText(Constantes.CAMPO_INVALIDO);
                hayCamposVacios = false;
            }
        }

        if (tfPorcentajePrecio.getText().isEmpty() || !Utilidades.validarCadena(tfPorcentajePrecio.getText(), Utilidades.NUMERO_PATTERN)) {
            lbPorcentajePrecio.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (taRestriccion.getText().isEmpty()) {
            lbRestriccion.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        if (taDescripcion.getText().isEmpty()) {
            lbDescripcion.setText(Constantes.CAMPO_INVALIDO);
            hayCamposVacios = false;
        }

        return hayCamposVacios;
    }
    
    private File cargarFoto() {
        FileChooser seleccionImg = new FileChooser();
        seleccionImg.setTitle("Seleccione un logo para su empresa");
        FileChooser.ExtensionFilter filtroArchivos
                = new FileChooser.ExtensionFilter("archivos PNG (*.png, *.jpg, *.jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        seleccionImg.getExtensionFilters().add(filtroArchivos);
        Stage esccenarioActual = (Stage) tfCodigo.getScene().getWindow();
        return seleccionImg.showOpenDialog(esccenarioActual);
    }

    private void mostarFotoSeleccioanda(File fotografia) {
        try {
            BufferedImage buffer = ImageIO.read(fotografia);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFotografia.setImage(image);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar el logo", "Error al intentar visualizar el logo", Alert.AlertType.ERROR);
        }
    }
    
    private void obtenerFotoPromocion(int idPromocion) {
        Promocion promocionFoto = PromocionDAO.obtenerFotoPromocion(idPromocion);
        if (promocionFoto != null && promocionFoto.getFotografiaBase64()!= null && promocionFoto.getFotografiaBase64().length() > 0) {
            System.out.println(promocionFoto.getFotografiaBase64());
            byte[] decodeImg = Base64.getDecoder().decode(promocionFoto.getFotografiaBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImg));
            ivFotografia.setImage(image);
        }
    }
}
