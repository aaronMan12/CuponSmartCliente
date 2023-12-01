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
    private TextField tfUserName;
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
        String userName = tfUserName.getText();
        String contrasenia = tfContrasenia.getText();
        
        if (validarLoging(userName, contrasenia)){
            loging(userName, contrasenia);
        }
    }
    
    private boolean validarLoging(String userName, String contrasenia){
        boolean validacion = true;
        lbValidacionCorreo.setText("");
        lbValidacionContrasenia.setText("");
        
        if (userName.isEmpty()){
            validacion = false;
            lbValidacionCorreo.setText("Campos vacios");
        }
        
        if (contrasenia.isEmpty()){
            validacion = false;
            lbValidacionContrasenia.setText("Campos vacios");
        }
        
        return validacion;
    }
    
    private void loging(String userName, String contrasenia){
        RespuestaLogin respuestaLogin =LoginDAO.iniciarSesion(userName,contrasenia);
        
        if (respuestaLogin.getError() == false){
           Utilidades.mostrarAlertaSimple("Campos correctos", respuestaLogin.getContenido(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuestaLogin.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
}
