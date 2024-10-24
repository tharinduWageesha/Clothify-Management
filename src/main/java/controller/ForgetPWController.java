package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgetPWController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtuserName;

    @FXML
    private Text verifyMsg;

    public void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
