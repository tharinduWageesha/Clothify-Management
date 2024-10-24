package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Supplier;
import model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
            String sql = "SELECT * FROM user WHERE username = ?;";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userService.getUserName());  // Use parameterized query to prevent SQL injection

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("ContactNo"),
                        resultSet.getString("Name")
                );
                System.out.println(user);

                // Update the UI components with the retrieved user information
                txtName.setText(user.getName());
                txtCon.setText(user.getContactNo());
                txtEmail.setText(user.getEmail());
                txtPassword.setText(user.getPassword());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching user data", e);
        }
    }

}
