package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;

public class LoginWindow_Controller {

    private UserService userService = new UserController();    @FXML
    private JFXButton btnExit;


    @FXML
    private JFXButton btnLog;

    @FXML
    private JFXButton btnfpw;

    @FXML
    private JFXPasswordField txtpassword;

    @FXML
    private JFXTextField txtuserName;

    @FXML
    private Text txtErrMsg;




    public void btnForgotOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/forgetpw.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnLogInOnAction(ActionEvent event) {
        String username = txtuserName.getText();
        String password = txtpassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            txtErrMsg.setText("Please enter your Username and Password!");
        } else {
            try {
                if (validateCredentials(username, password)) {
                    userService.setUserName(username);  // This sets the username
                    userService.setPassword(password);

                    System.out.println(userService.getUserName());  // Testing if username is set

                    // Load the dashboard FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/dashBoard.fxml"));
                    Parent root = loader.load();

                    // Get the controller and pass the UserService instance
                    DashBoardController controller = loader.getController();
                    controller.setUserService(userService);

                    // Show the new scene
                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.show();

                    // Close the current login window
                    Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage2.close();
                } else {
                    txtErrMsg.setText("Invalid Username or Password !");
                }
            } catch (Exception e) {
                e.printStackTrace();
                txtErrMsg.setText("An error occurred during login.");
            }
        }
    }


    private boolean validateCredentials(String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
        String query = "SELECT password FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return password.equals(storedPassword);
            }
        } finally {
            connection.close();
        }
        return false; // No matching user found
    }


}
