package clientecuponsmart;

import clientecuponsmart.modelo.pojo.Usuario;
import clientecuponsmart.utils.Constantes;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLHomeController implements Initializable {

    private Usuario usuarioSesion;

    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Button btnIconUsuarios;
    @FXML
    private Button btnIconEmpresas;
    @FXML
    private Button btnIconSucursales;
    @FXML
    private Button btnIconPromociones;
    @FXML
    private Button btnIconCupon;
    @FXML
    private Button btnIconCuenta;
    @FXML
    private Label lbRol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargarImagenes();
    }

    public void inicializarHome(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
        lbNombreUsuario.setText(usuarioSesion.getNombre() + " " + usuarioSesion.getApellidoPaterno());
        if (usuarioSesion.getIdRollUsuario() == Constantes.ID_ROL_GENERAL) {
            lbRol.setText("General");
        } else {
            lbRol.setText("Comercial");
            btnIconUsuarios.setVisible(false);
        }
    }

    @FXML
    private void btnGestionUsuarios(ActionEvent event) {
        try {
            FXMLLoader vistaLoad
                    = new FXMLLoader(getClass().getResource("FXMLAdministrarUsuarios.fxml"));
            Parent vista = vistaLoad.load();
            FXMLAdministrarUsuariosController controlador = vistaLoad.getController();
            controlador.inicializarInformacion(usuarioSesion.getIdUsuario());
            Stage stage = new Stage();
            Scene escenaAdmin = new Scene(vista);
            stage.setScene(escenaAdmin);
            stage.setTitle("Usuarios");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnGestionEmpresas(ActionEvent event) {
        if (Constantes.ID_ROL_GENERAL == usuarioSesion.getIdRollUsuario()) {
            try {
                FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLAdminEmpresas.fxml"));
                Parent vista = vistaLoad.load();
                FXMLAdminEmpresasController controlador = vistaLoad.getController();
                controlador.inicializarInformacionGeneral();
                Stage stage = new Stage();
                Scene escenaAdmin = new Scene(vista);
                stage.setScene(escenaAdmin);
                stage.setTitle("Empresas");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // TODO VISTA COMERCIAL
        }
    }

    @FXML
    private void btnGestionSucursales(ActionEvent event) {

    }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {

    }

    private void cargarImagenes() {
        this.cargarImagen(btnIconUsuarios, "/componentes/moduloUsuario.png");
        this.cargarImagen(btnIconEmpresas, "/componentes/moduloEmpresa.png");
        this.cargarImagen(btnIconCupon, "/componentes/moduloCupon.png");
        this.cargarImagen(btnIconSucursales, "/componentes/moduloSucursal.png");
        this.cargarImagen(btnIconPromociones, "/componentes/moduloPromocion.png");
        this.cargarImagen(btnIconCuenta, "/componentes/gestionCuenta.png");
    }

    private void cargarImagen(Button button, String imagePath) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
    }

    @FXML
    private void btnGestionCupon(ActionEvent event) {
    }

    @FXML
    private void btnGestionCuenta(ActionEvent event) {
        try {
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLRegistrarUsuario.fxml"));
            Parent vista = vistaLoad.load();

            FXMLRegistrarUsuarioController controlador = vistaLoad.getController();
            controlador.inicializarInformacion(usuarioSesion);

            Stage stage = new Stage();
            Scene scenaAdmin = new Scene(vista);
            stage.setScene(scenaAdmin);
            stage.setTitle("Editar usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
