/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

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
 * @author aaron
 */
public class FXMLAdministrarUsuariosController implements Initializable {

    private int idUsuario;
    private ObservableList<Usuario> usuariosEmpresa;
    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCurp;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colUsuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(int idUsuario) {
        this.idUsuario = idUsuario;
        consultarInformacionUsuarios();
    }
    
    private void consultarInformacionUsuarios(){
    //hacer usuarioDAO un metodo para obtener todos los usuarios, ejemplo obtenerUsuariosPorIdUsuario
   
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
