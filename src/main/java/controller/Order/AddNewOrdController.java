package controller.Order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AddNewOrdController {

    OrderService service = new OrderController();
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnNewItem;

    @FXML
    private JFXTextField txtOrdDate;

    @FXML
    private JFXTextField txtOrdDis;

    @FXML
    private JFXTextField txtOrdTime;

    @FXML
    private JFXTextField txtOrdTot;

    @FXML
    private Text verifyMsg;

    @FXML
    void btnAddOrderOnAction(ActionEvent event) {
        if(txtOrdTot.getText().equals("") || txtOrdDis.getText().equals("") || txtOrdTime.getText().equals("") || txtOrdDate.getText().equals("")){
            verifyMsg.setText("Please fill all the fields.");
        }else {
            String ordID = generateOrdID();;
            LocalDate ordDate = LocalDate.parse(txtOrdDate.getText());
            LocalTime ordTime = LocalTime.parse(txtOrdTime.getText());
            Double ordDis = Double.valueOf(txtOrdDis.getText());
            Double ordTot = Double.valueOf(txtOrdTot.getText());

            Order order = new Order(ordID, ordDate, ordDis, ordTot, ordTime);
            System.out.println(order);
            boolean isSucces = service.addOrder(order);
            if (isSucces) {
                new Alert(Alert.AlertType.INFORMATION, "Order Added Succesfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error Adding Order!").show();
            }
        }
    }

    private String generateOrdID() {
        String lastID = service.getLastOrdID();
        if (lastID != null) {
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return "OR" + String.format("%04d", numericPart);
        } else {
            return "OR0001";
        }
    }

    @FXML
    void btnAddSupOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addnewSup.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
