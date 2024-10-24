package controller.Supplier;

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
import model.Supplier;

import java.io.IOException;

public class AddNewSupController {

    SupplierService service = new SupplierController();
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnNewItem;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSupItem;

    @FXML
    private JFXTextField txtSupName;

    @FXML
    private Text verifyMsg;

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnAddItemOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addNewProd.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent event) {
        if(txtCompany.getText().equals("") || txtSupName.getText().equals("") || txtSupItem.getText().equals("") || txtEmail.getText().equals("")){
            verifyMsg.setText("Please fill all the fields.");
        }else {
            String supID = generateSupID();
            String supName = txtSupName.getText();
            String supItem = txtSupItem.getText();
            String supEmail = txtEmail.getText();
            String supCompany = txtCompany.getText();

            Supplier supplier = new Supplier(supID, supName, supCompany, supEmail, supItem);
            System.out.println(supplier);
            boolean isSucces = service.addSupplier(supplier);
            if (isSucces) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added Succesfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error Adding Supplier!").show();
            }
        }
    }

    private String generateSupID() {
        String lastID = service.getLastSupID();
        if (lastID != null) {
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return "S" + String.format("%04d", numericPart);
        } else {
            return "S0001";
        }
    }
}
