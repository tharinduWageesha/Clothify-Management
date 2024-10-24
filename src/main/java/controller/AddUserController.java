package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddUserController {

    public JFXTextField txtContactNo;
    public JFXTextField txtName;
    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPasswordAdmin;

    @FXML
    private JFXTextField txtPasswordUser;

    @FXML
    private JFXTextField txtuserName;

    @FXML
    private Text verifyMsg;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        String adPW = txtPasswordAdmin.getText();
        if (txtContactNo.getText().equals("") || txtName.getText().equals("") || txtuserName.getText().equals("") || txtPasswordUser.getText().equals("") || txtPasswordAdmin.getText().equals("") || txtEmail.getText().equals("")) {
            verifyMsg.setText("Please fill all the fields.");
        }else if (!adPW.equals("admin14567")){
                verifyMsg.setText("Admin Password is Incorrect !.");
        }else {
            try {
                String userName = txtuserName.getText();
                String userPW = txtPasswordUser.getText();
                String userEmail = txtEmail.getText();
                String name = txtName.getText();
                String contactNo = txtContactNo.getText();

                User user = new User(userName,userPW,userEmail,name,contactNo);
                System.out.println(user);

                String SQL = "Insert into User Values(?,?,?,?,?)";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
                PreparedStatement pstm = connection.prepareStatement(SQL);
                pstm.setObject(1,user.getUsername());
                pstm.setObject(2,user.getPassword());
                pstm.setObject(3,user.getEmail());
                pstm.setObject(4,user.getContactNo());
                pstm.setObject(5,user.getName());

                boolean isSucces = pstm.executeUpdate()>0;
                if(isSucces){
                    new Alert(Alert.AlertType.INFORMATION, "User Added Succesfully").show();

                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }


    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

