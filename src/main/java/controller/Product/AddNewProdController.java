package controller.Product;

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
import model.Product;

import java.io.IOException;
import java.sql.SQLException;

public class AddNewProdController {

    public Text verifyMsg1;
    public JFXTextField txtSupID;
    public JFXTextField txtQty;
    public JFXTextField txtProName;
    public JFXTextField txtPrice;
    ProductService service = new ProductController();

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnNewSup;


    @FXML
    void btnAddNewSup(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addnewSup.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtProName.getText().equals("") || txtPrice.getText().equals("") || txtSupID.getText().equals("") || txtQty.getText().equals("")) {
            verifyMsg1.setText("Please fill all the fields.");
        } else {
            try {
                // Auto-generate the Product ID
                String proID = generateProID();
                String proName = txtProName.getText();
                Double proPrice = Double.valueOf(txtPrice.getText());
                int proQty = Integer.parseInt(txtQty.getText());
                String proSup = txtSupID.getText();

                Product product = new Product(proID, proName, proPrice, proQty,proSup);
                System.out.println(product);

                boolean isSuccess = service.addProduct(product);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Added Successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error Adding Product!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }

    // Method to auto-generate Product ID
    private String generateProID() throws SQLException {
        String lastID = service.getLastProID();
        if (lastID != null) {
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return "P" + String.format("%04d", numericPart);
        } else {
            return "P0001";
        }
    }


    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
