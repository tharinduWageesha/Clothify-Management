package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileController implements Initializable{
    public Label lblUserNameDis;
    UserService userService = new UserController();

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblStatus;

    @FXML
    private Text lblUserName;

    @FXML
    private JFXTextField txtCon;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private Text verifyMsg;

    private String username;
    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setUserService(UserService userService) {
        System.out.println("Username in dashboard: " + userService.getUserName());
        lblUserNameDis.setText(userService.getUserName());
        username=userService.getUserName();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ini");
            txtName.setText(userService.getUserName());
            txtPassword.setText(userService.getPassowrd());
            txtEmail.setText(userService.getEmail());
        System.out.println(userService.getUserName()+"ssasa");
        System.out.println(userService.getPassowrd());

    }

}
