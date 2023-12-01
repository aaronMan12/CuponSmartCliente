/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.pojo.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class FXMLHomelController2 implements Initializable {

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
    
    public void inicializarHome2(Usuario usuarioSesion){
    this.usuarioSesion = usuarioSesion;
    lbNombreUsuario.setText(usuarioSesion.getNombre()+" "
    +usuarioSesion.getApellidoPaterno()+" "+usuarioSesion.getApellidoMaterno());
    }
}
