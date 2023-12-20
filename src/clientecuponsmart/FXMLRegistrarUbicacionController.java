package clientecuponsmart;

import clientecuponsmart.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLRegistrarUbicacionController implements Initializable {

    @FXML
    private TextField tfCalle;
    @FXML
    private Label lbCalle;
    @FXML
    private Label lbNumero;
    @FXML
    private TextField tfNumero;
    @FXML
    private Label lbCodigoPostal;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private Label lbCiudad;
    @FXML
    private TextField tfCiudad;
    @FXML
    private Label lbColonia;
    @FXML
    private TextField tfColonia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (this.validarCampos()) {
            
        }
    }
    
    private boolean validarCampos() {
        this.limpiarErrores();
        
        String errorMensaje = "Campo invalido.";
        boolean isCamposVacios = true;
        
        if (tfCalle.getText().isEmpty()) {
            lbCalle.setText(errorMensaje);
            isCamposVacios = false;
        }
        
        if (tfCiudad.getText().isEmpty()) {
            lbCiudad.setText(errorMensaje);
            isCamposVacios = false;
        }
        
        if (tfCodigoPostal.getText().isEmpty() || !Utilidades.validarCadena(tfCodigoPostal.getText(), Utilidades.CODIGO_POSTAL_PATTERN)) {
            lbCodigoPostal.setText(errorMensaje);
            isCamposVacios = false;
        }
        
        if (tfNumero.getText().isEmpty()) {
            lbNumero.setText(errorMensaje);
            isCamposVacios = false;
        }
        
        return isCamposVacios;
    }
    
    private void limpiarErrores() {
        lbCalle.setText("");
        lbCiudad.setText("");
        lbCodigoPostal.setText("");
        lbNumero.setText("");
    }
    
}
