package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ForgetPWController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private Text lblDone;

    @FXML
    private Text lblInvalidMsg;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtuserName;

    @FXML
    private Text verifyMsg;

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnGetPwOnAction(ActionEvent event) {
        String userName = txtuserName.getText();
        String email = txtEmail.getText();

        if (userName.isEmpty() || email.isEmpty()) {
            lblInvalidMsg.setText("Please enter your Username and Email!");
            return;
        }

        try {
            // Query to check if the username and email match
            String sql = "SELECT * FROM user WHERE username = ? AND email = ?;";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, email);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                // User exists, retrieve the password
                String password = resultSet.getString("password");
                String retrievedUsername = resultSet.getString("username");
                sendPasswordEmail(email, retrievedUsername, password);  // Send the email
                lblInvalidMsg.setText("An email with your password has been sent.");
            } else {
                lblInvalidMsg.setText("Invalid Username or Email!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            lblInvalidMsg.setText("Error occurred while retrieving password.");
        }

    }


    public void sendPasswordEmail(String recipientEmail, String username, String password) {
        String senderEmail = "tharinduwageesha@gmail.com";
        String senderPassword = "rcnn mxgj jqbf bemq";

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your Password Information");
            message.setText("Hello " + username + ",\n\nYour password is: " + password + "\n\nPlease keep it safe!");

            // Send email
            Transport.send(message);

            lblDone.setText("Check your Email Inbox and get your password");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }
}
