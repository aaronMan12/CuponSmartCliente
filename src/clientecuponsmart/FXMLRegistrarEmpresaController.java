/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.dao.EmpresaDAO;
import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.RespuestaUsuarioEscritorio;
import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Constantes;
import clientecuponsmart.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class FXMLRegistrarEmpresaController implements Initializable {

    private Usuario usuarioSesion = null;
    private Empresa empresaUsuarioSesion = null;
    private File fotografia;
    @FXML
    private TextField tfNombreEmpresa;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNombeComercialEmpresa;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfPaginaWeb;
    @FXML
    private TextField tfRFC;
    @FXML
    private Label lbTituloEmpresa;
    @FXML
    private Label lbEstatus;
    @FXML
    private Button btnSeleccionarFoto;
    @FXML
    private Button btnGuardarLogo;
    @FXML
    private Button btnUbicacion;
    @FXML
    private TextField tfRepresentanteLegal;
    @FXML
    private ImageView ivLogoEmpresa;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private Label lbLogo;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRFC;
    @FXML
    private Label lbNombreComercial;
    @FXML
    private Label lbPaginaWeb;
    @FXML
    private Label lbTelefono;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbRepresentanteLegal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnSubirLogo(ActionEvent event) {
        fotografia = cargarFoto();
        if (fotografia != null) {
            mostarFotoSeleccioanda(fotografia);
        }
    }

    @FXML
    private void btnGuardarLogo(ActionEvent event) {
        if (fotografia != null) {
            RespuestaUsuarioEscritorio nuevoLogo = EmpresaDAO.actualizarLogoEmpresa(fotografia, empresaUsuarioSesion.getIdEmpresa());
            if (!nuevoLogo.isError()) {
                Utilidades.mostrarAlertaSimple("Logo guardado", nuevoLogo.getContenido(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al guardar", nuevoLogo.getContenido(), Alert.AlertType.ERROR);

            }
        } else {
            Utilidades.mostrarAlertaSimple("No hay logo seleccionado", "Para actualizar el logo antes debes elegir una nueva imagen",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnAgregarUbicacion(ActionEvent event) {
        if (empresaUsuarioSesion.getIdUbicacion() != null){
            editarUbicacionEmpresa(empresaUsuarioSesion);
        }
    }

    @FXML
    private void btnGuardarInformacionEmpresa(ActionEvent event) {
        //System.out.println(empresaUsuarioSesion.getNombre());
        if (empresaUsuarioSesion == null) {

            if (validarCamposEmpresa()) {
                Empresa nuevaEmpresa = new Empresa();
                nuevaEmpresa.setNombre(tfNombreEmpresa.getText());
                nuevaEmpresa.setNombreComercial(tfNombeComercialEmpresa.getText());
                nuevaEmpresa.setTelefono(tfTelefono.getText());
                nuevaEmpresa.setPaginaWeb(tfPaginaWeb.getText());
                nuevaEmpresa.setEmail(tfEmail.getText());
                nuevaEmpresa.setNombreRepresentante(tfRepresentanteLegal.getText());
                nuevaEmpresa.setRFC(tfRFC.getText());
                nuevaEmpresa.setEstatus("activo");

                RespuestaUsuarioEscritorio respuesta = EmpresaDAO.registrarEmpresa(nuevaEmpresa);

                if (respuesta.getEmpresa().getIdEmpresa() > 0) {
                    
                    btnUbicacion.setVisible(true);
                    Utilidades.mostrarAlertaSimple("Empresa agregada", "Ahora debes agregar una ubicación creada", Alert.AlertType.INFORMATION);
                    
                    registrarUbicacionEmpresa(respuesta.getEmpresa().getIdEmpresa());
                } else {
                    Utilidades.mostrarAlertaSimple("Error al agregar la empresa", "No se pudo agregar la empresa", Alert.AlertType.ERROR);

                }

            }
        } else {
            if (validarCamposEmpresa()) {
                Empresa empresaEditada = new Empresa();
                empresaEditada.setNombre(tfNombreEmpresa.getText());
                empresaEditada.setNombreComercial(tfNombeComercialEmpresa.getText());
                empresaEditada.setTelefono(tfTelefono.getText());
                empresaEditada.setPaginaWeb(tfPaginaWeb.getText());
                empresaEditada.setEmail(tfEmail.getText());
                empresaEditada.setNombreRepresentante(tfRepresentanteLegal.getText());
                empresaEditada.setIdEmpresa(empresaUsuarioSesion.getIdEmpresa());
                //empresaEditada.setEstatus("activo");
                if (rbInactivo.isSelected()) {
                    empresaEditada.setEstatus("inactivo");
                } else {
                    empresaEditada.setEstatus("activo");
                }
                RespuestaUsuarioEscritorio respuesta = EmpresaDAO.editarEmpresa(empresaEditada);
                System.out.println(respuesta.getContenido());
                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple("Actulaización Empresa", "La empresa se atualizo satisfactoriamente", Alert.AlertType.INFORMATION);
                }
            }

        }

    }

    public void inicializarUsuarioComercial(Usuario usuario) {
        this.usuarioSesion = usuario;
        if (usuarioSesion.getIdRollUsuario() == Constantes.ID_ROL_COMERCIAL) {
            lbTituloEmpresa.setText("Editar datos de la Empresa");
            btnUbicacion.setText("Editar ubicación");
            tfRFC.setDisable(true);
            inicializarEmpresaUsuarioComercial(usuarioSesion.getIdEmpresa());
            inicializarComponentes(usuarioSesion);
            obtenerFotoEmpresa(usuarioSesion.getIdEmpresa());
        }
    }

    
    public void inicializarUsuarioGeneral(Empresa empresacb) {
        this.empresaUsuarioSesion = empresacb;
        lbTituloEmpresa.setText("Editar datos de la empresa");
        tfRFC.setDisable(true);
        btnUbicacion.setText("Editar ubicación");
        inicializarComponentes(usuarioSesion);
        obtenerFotoEmpresa(empresaUsuarioSesion.getIdEmpresa());
    }

    public void inicializarFormularioRegistrarEmpresa() {
        lbEstatus.setVisible(false);
        rbActivo.setVisible(false);
        rbInactivo.setVisible(false);
        lbLogo.setVisible(false);
        ivLogoEmpresa.setVisible(false);
        btnSeleccionarFoto.setVisible(false);
        btnGuardarLogo.setVisible(false);
        btnUbicacion.setVisible(false);
    }

    private void inicializarEmpresaUsuarioComercial(int idEmpresa) {
        RespuestaUsuarioEscritorio empresa = EmpresaDAO.buscarEmpresaPorId(idEmpresa);
        this.empresaUsuarioSesion = empresa.getEmpresa();
    }

    private void inicializarComponentes(Usuario usuario) {
        if (usuario != null) {
            tfNombreEmpresa.setText(empresaUsuarioSesion.getNombre());
            tfNombeComercialEmpresa.setText(empresaUsuarioSesion.getNombreComercial());
            tfPaginaWeb.setText(empresaUsuarioSesion.getPaginaWeb());
            tfEmail.setText(empresaUsuarioSesion.getEmail());
            tfTelefono.setText(empresaUsuarioSesion.getTelefono());
            tfRFC.setText(empresaUsuarioSesion.getRFC());
            tfRepresentanteLegal.setText(empresaUsuarioSesion.getNombreRepresentante());
            if (empresaUsuarioSesion.getEstatus().equals(Constantes.ESTATUS_INACTIVO)) {
                rbInactivo.setSelected(true);
            } else {

                rbActivo.setSelected(true);
            }
        } else {
            tfNombreEmpresa.setText(empresaUsuarioSesion.getNombre());
            tfNombeComercialEmpresa.setText(empresaUsuarioSesion.getNombreComercial());
            tfPaginaWeb.setText(empresaUsuarioSesion.getPaginaWeb());
            tfEmail.setText(empresaUsuarioSesion.getEmail());
            tfTelefono.setText(empresaUsuarioSesion.getTelefono());
            tfRFC.setText(empresaUsuarioSesion.getRFC());
            tfRepresentanteLegal.setText(empresaUsuarioSesion.getNombreRepresentante());
            if (empresaUsuarioSesion.getEstatus().equals(Constantes.ESTATUS_INACTIVO)) {
                rbInactivo.setSelected(true);
            } else {

                rbActivo.setSelected(true);
            }
        }

    }

    private File cargarFoto() {
        FileChooser seleccionImg = new FileChooser();
        seleccionImg.setTitle("Seleccione un logo para su empresa");
        FileChooser.ExtensionFilter filtroArchivos
                = new FileChooser.ExtensionFilter("archivos PNG (*.png, *.jpg, *.jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        seleccionImg.getExtensionFilters().add(filtroArchivos);

        Stage esccenarioActual = (Stage) tfEmail.getScene().getWindow();

        return seleccionImg.showOpenDialog(esccenarioActual);
    }

    private void mostarFotoSeleccioanda(File fotografia) {
        try {
            BufferedImage buffer = ImageIO.read(fotografia);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivLogoEmpresa.setImage(image);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar el logo", "Error al intentar visualizar el logo", Alert.AlertType.ERROR);
        }
    }

    private void obtenerFotoEmpresa(int idEmpresa) {
        Empresa empresaFoto = EmpresaDAO.obtenerFotoEmpresa(idEmpresa);
        if (empresaFoto != null && empresaFoto.getLogoBase64() != null && empresaFoto.getLogoBase64().length() > 0) {
            byte[] decodeImg = Base64.getDecoder().decode(empresaFoto.getLogoBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImg));
            ivLogoEmpresa.setImage(image);
        }
    }
    
    private void registrarUbicacionEmpresa(int idEmpresa){
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUbicacion.fxml"));
            Parent vista = vistaLoad.load();
            FXMLRegistrarUbicacionController controlador = vistaLoad.getController();
            controlador.inicializarRegistroSucursalEmpresa(idEmpresa);
            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Editar ubicación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.cerrarPantalla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void editarUbicacionEmpresa(Empresa empresa){
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUbicacion.fxml"));
            Parent vista = vistaLoad.load();
            FXMLRegistrarUbicacionController controlador = vistaLoad.getController();
            controlador.inicializarEditarSucursal(empresa.getIdUbicacion());
            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Editar ubicación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.cerrarPantalla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validarCamposEmpresa() {
        boolean camposVacios = true;
        limpiarErrores();

        if (tfNombreEmpresa.getText().isEmpty()) {
            lbNombre.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }

        if (tfNombeComercialEmpresa.getText().isEmpty()) {
            lbNombreComercial.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }

        if (tfPaginaWeb.getText().isEmpty() || !Utilidades.validarCadena(tfPaginaWeb.getText(), Utilidades.PAGINA_WEB_PATTERN)) {
            lbPaginaWeb.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }

        if (tfEmail.getText().isEmpty() || !Utilidades.validarCadena(tfEmail.getText(), Utilidades.EMAIL_PATTERN)) {
            lbEmail.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }
        if (tfTelefono.getText().isEmpty() || !Utilidades.validarCadena(tfTelefono.getText(), Utilidades.TELEFONO_PATTERN)) {
            lbTelefono.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }
        if (tfRFC.getText().isEmpty() || !Utilidades.validarCadena(tfRFC.getText(), Utilidades.RFC_EMPRESA_PATTERN)) {
            lbRFC.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }
        if (tfRepresentanteLegal.getText().isEmpty()) {
            lbRepresentanteLegal.setText(Constantes.CAMPO_INVALIDO);
            camposVacios = false;
        }
        return camposVacios;
    }

    private void limpiarErrores() {
        this.lbNombre.setText("");
        this.lbNombreComercial.setText("");
        this.lbPaginaWeb.setText("");
        this.lbEmail.setText("");
        this.lbTelefono.setText("");
        this.lbRFC.setText("");
        this.lbRepresentanteLegal.setText("");
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfEmail.getScene().getWindow();
        stage.close();
    }

}
