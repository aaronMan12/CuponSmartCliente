/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.dao.EmpresaDAO;
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
    
    private int idEmpresa;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (idEmpresa > 0){
            inicializarComponentesUsuarioComercial();
        }else{
        }
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
    
    
    
     public void inicializarUsuario(int idEmpresa){
    this.idEmpresa = idEmpresa;
    
    if (idEmpresa > 0){
        inicializarUsuarioComercial(idEmpresa);
    }
    }
    

    private void inicializarUsuarioComercial(int idEmpresa) {
        RespuestaUsuarioEscritorio empresa = EmpresaDAO.buscarEmpresaPorId(idEmpresa);
        
        //tfNombreEmpresa.setText(empresa.getEmpresas().get(0).getNombre());
        
    }
    
    private void inicializarComponentesUsuarioComercial(){
        lbTituloEmpresa.setText("");
        lbTituloEmpresa.setText("Editar datos de la Empresa");
        cbEstatus.setVisible(false);
        tfRFC.setVisible(false);
    }
}
