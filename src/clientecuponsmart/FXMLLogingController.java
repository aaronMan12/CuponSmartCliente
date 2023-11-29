/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart;

import clientecuponsmart.modelo.dao.LoginDAO;
import clientecuponsmart.modelo.pojo.RespuestaLogin;
import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author aaron
 */
public class FXMLLogingController implements Initializable {

    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField tfContrasenia;
    @FXML
    private Label lbValidacionCorreo;
    @FXML
    private Label lbValidacionContrasenia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        String correo = tfCorreo.getText();
        String contrasenia = tfContrasenia.getText();
        
        if (validarLoging(correo, contrasenia)){
            
            loging(correo, contrasenia);
        }
    }
    
    private boolean validarLoging(String correo, String contrasenia){
        boolean validacion = true;
        
        if (correo == null || correo.isEmpty()){
            validacion = false;
            lbValidacionCorreo.setText("campo invalido");
        }
        
        if (contrasenia == null || contrasenia.isEmpty()){
            validacion = false;
            lbValidacionContrasenia.setText("campo invalido");

        }
        
        return validacion;
    }
    
    
    
    private void loging(String correo, String contrasenia){
        
        RespuestaLogin respuestaLogin =LoginDAO.iniciarSesion(correo,contrasenia);
        
        if (respuestaLogin.getError() == false){
           Utilidades.mostrarAlertaSimple("campos Correctos", respuestaLogin.getContenido(), Alert.AlertType.INFORMATION);
        }
    
    }
    
}
