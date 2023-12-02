/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.pojo.Empresa;
import clientecuponsmart.modelo.pojo.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class FXMLAdminEmpresasController implements Initializable {
 
    private int idUsuario;
    private ObservableList<Empresa> empresas;
    
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
    private TableColumn colRfc;
    @FXML
    private TableColumn colNomRepresentante;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void inicializarInformacion(Usuario usuario) {
        if(usuario.getIdEmpresa() > 0){
            this.idUsuario = usuario.getIdUsuario();
        }
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

    @FXML
    private void btnFormularioBuscar(ActionEvent event) {
    }
}
