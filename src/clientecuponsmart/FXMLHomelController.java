/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class FXMLHomelController implements Initializable {

    private Usuario usuarioSesion;
    @FXML
    private Label lbNombreUsuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarHome(Usuario usuarioSesion){
    this.usuarioSesion = usuarioSesion;
    lbNombreUsuario.setText(usuarioSesion.getNombre()+" "
    +usuarioSesion.getApellidoPaterno()+" "+usuarioSesion.getApellidoMaterno());
    
    
    }

    @FXML
    private void btnGestionUsuarios(ActionEvent event) {
        try{
        FXMLLoader vistaLoad =
                new FXMLLoader(getClass().getResource("FXMLAdministrarUsuarios.fxml"));
        Parent vista = vistaLoad.load();
        FXMLAdministrarUsuariosController controlador = vistaLoad.getController();
        controlador.inicializarInformacion(usuarioSesion.getIdUsuario());
        Stage stage = new Stage();
        Scene escenaAdmin = new Scene(vista);
        stage.setScene(escenaAdmin);
        stage.setTitle("Usuarios");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        }catch(IOException e){
        e.printStackTrace();
        }
    }

    @FXML
    private void btnGestionEmpresas(ActionEvent event) {
    }

    @FXML
    private void btnGestionSucursales(ActionEvent event) {
    }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {
    }
}
