package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardController {

    UserService userService = new UserController();
    @FXML
    private JFXButton btnEmp;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnProd;

    @FXML
    private JFXButton btnRep;

    @FXML
    private JFXButton btnSup;

    @FXML
    private ImageView btnnotifi;

    @FXML
    private ImageView btnset;

    @FXML
    private Label lblTime;

    @FXML
    private Text txtId;

    private String username;
    public void setUserService(UserService userService) {
        System.out.println("Username in dashboard: " + userService.getUserName()); // To verify the username
        txtId.setText(userService.getUserName());
        username = userService.getUserName();
    }

    @FXML
    void btnEmpOnAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setIconified(true); // Minimize the current window

        Stage supplierStage = new Stage();
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/empManage.fxml"))));
            supplierStage.show();
            supplierStage.setOnHiding(e -> {
                currentStage.setIconified(false); // Restore (maximize) the main window
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnNotOnAction(MouseEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setIconified(true); // Minimize the current window

        Stage supplierStage = new Stage();
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/orderManage.fxml"))));
            supplierStage.show();
            supplierStage.setOnHiding(e -> {
                currentStage.setIconified(false); // Restore (maximize) the main window
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnProdOnAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setIconified(true); // Minimize the current window

        Stage supplierStage = new Stage();
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/productManage.fxml"))));
            supplierStage.show();
            supplierStage.setOnHiding(e -> {
                currentStage.setIconified(false); // Restore (maximize) the main window
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRepOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupOnAction(ActionEvent event) {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setIconified(true); // Minimize the current window

        Stage supplierStage = new Stage();
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/supplierManage.fxml"))));
            supplierStage.show();
            supplierStage.setOnHiding(e -> {
                currentStage.setIconified(false); // Restore (maximize) the main window
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnsetOnAction(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        // Create a formatter for displaying only time (hours, minutes, seconds)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH : mm : ss");

        // Update time label every second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lblTime.setText(LocalDateTime.now().format(timeFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }


    public void btnLogOutOnAction(MouseEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/loginWindow.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.close();
    }

    public void btnAddNewUSer(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setIconified(true); // Minimize the current window

        Stage supplierStage = new Stage();
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/addNewUser.fxml"))));
            supplierStage.show();
            supplierStage.setOnHiding(e -> {
                currentStage.setIconified(false); // Restore (maximize) the main window
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnProfOnAction(MouseEvent event) {
        userService.setUserName(username);
        // Load the dashboard FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/profile.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller and pass the UserService instance
        ProfileController controller = loader.getController();
        controller.setUserService(userService);

        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root));
        stage1.show();
    }

    public void btnSetOnAction(MouseEvent event) {
    }

}
