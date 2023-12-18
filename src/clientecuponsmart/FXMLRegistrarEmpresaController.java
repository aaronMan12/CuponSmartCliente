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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class FXMLRegistrarEmpresaController implements Initializable {

    private Usuario usuarioSesion;
    private Empresa empresaUsuarioSesion;
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
    private ComboBox<?> cbEstatus;
    @FXML
    private Label lbTituloEmpresa;
    @FXML
    private Label lbEstatus;
    @FXML
    private TextField tfUbicación;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void btnLogo(ActionEvent event) {
    }

    @FXML
    private void btnSubirLogo(ActionEvent event) {
    }

    @FXML
    private void btnGuardarLogo(ActionEvent event) {
    }

    public void inicializarUsuario(Usuario usuario) {
        this.usuarioSesion = usuario;
        if (usuarioSesion.getIdEmpresa() > 0) {
            lbTituloEmpresa.setText("Editar datos de la Empresa");
            cbEstatus.setVisible(false);
            lbEstatus.setVisible(false);
            tfRFC.setDisable(true);
            inicializarUsuarioComercial(usuarioSesion.getIdEmpresa());
            inicializarComponentesUsuarioComercial();
        } else {
        }
       
    }

    private void inicializarUsuarioComercial(int idEmpresa) {
        RespuestaUsuarioEscritorio empresa = EmpresaDAO.buscarEmpresaPorId(idEmpresa);
        this.empresaUsuarioSesion = empresa.getEmpresa();  
    }

    private void inicializarComponentesUsuarioComercial() {
        tfNombreEmpresa.setText(empresaUsuarioSesion.getNombre());
        tfNombeComercialEmpresa.setText(empresaUsuarioSesion.getNombreComercial());
        tfPaginaWeb.setText(empresaUsuarioSesion.getPaginaWeb());
        tfEmail.setText(empresaUsuarioSesion.getEmail());
        tfTelefono.setText(empresaUsuarioSesion.getTelefono());
        tfRFC.setText(empresaUsuarioSesion.getRFC());
        tfUbicación.setText(empresaUsuarioSesion.getCiudad()+" "+empresaUsuarioSesion.getDireccion());
       
    }
}
