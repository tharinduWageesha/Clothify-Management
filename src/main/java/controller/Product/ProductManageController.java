package controller.Product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Employee;
import model.Order;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductManageController implements Initializable {

    public TableView<Product> tblProduct;
    public TableColumn colProductID;
    public TableColumn colProductName;
    public TableColumn colSupplierID;
    public TableColumn colProductPrice;
    public TableColumn colProductQty;
    public Text txtverifymsg;
    @FXML
    private JFXButton btnAddNew;

    ProductService service = new ProductController();

    @FXML
    private ImageView btnDel;

    @FXML
    private ImageView btnSearch;

    @FXML
    private ImageView btnUpd;

    @FXML
    private JFXButton btnViewRep;

    @FXML
    private ImageView btnnotifi;

    @FXML
    private ImageView btnnotifi1;

    @FXML
    private ImageView btnnotifi2;

    @FXML
    private ImageView btnset;

    @FXML
    private ImageView btnset1;

    @FXML
    private JFXTextField searchbar;

    @FXML
    private JFXTextField txtProID;

    @FXML
    private JFXTextField txtProName;

    @FXML
    private JFXTextField txtProPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSupID;



    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addNewProd.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDelOnAction(MouseEvent event) {
        boolean isSuccess = service.deleteProduct(txtProID.getText());
        if(isSuccess){
            setDatatoTabel();
            new Alert(Alert.AlertType.INFORMATION,"Product Deleted Succesfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error Deleting Product!").show();
        }

    }

    @FXML
    void btnNotOnAction(MouseEvent event) {
        setDatatoTabel();
    }

    @FXML
    void btnSearchOnAction(MouseEvent event) {
        Product product = service.serachProduct(searchbar.getText());
        if(product == null){
            txtverifymsg.setText("No results found...");
        }else{
            setTextToValues(product);
        }
    }

    @FXML
    void btnUpdOnAction(MouseEvent event) {
        if (txtProName.getText().equals("") || txtProPrice.getText().equals("") || txtSupID.getText().equals("") || txtQty.getText().equals("")) {
            txtverifymsg.setText("Please fill all the fields.");
        } else {
            try {
                // Auto-generate the Product ID
                String proID = txtProID.getText();
                String proName = txtProName.getText();
                Double proPrice = Double.valueOf(txtProPrice.getText());
                int proQty = Integer.parseInt(txtQty.getText());
                String proSup = txtSupID.getText();

                Product product = new Product(proID, proName, proPrice, proQty,proSup);
                System.out.println(product);

                boolean isSuccess = service.updateProduct(product);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully!").show();
                    setDatatoTabel();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error Updating Product!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void btnViewRepOnAction(ActionEvent event) {

    }

    @FXML
    void btnProfOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/profile.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnsetOnAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDatatoTabel();
        tblProduct.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));

    }

    private void setTextToValues(Product newValue) {
        txtProID.setText(newValue.getProduct_ID());
        txtProName.setText(newValue.getProduct_Name());
        txtProPrice.setText(String.valueOf(newValue.getPrice()));
        txtQty.setText(String.valueOf(newValue.getQty()));
        txtSupID.setText(newValue.getSupplier_ID());
    }

    private void setDatatoTabel() {
        colProductID.setCellValueFactory(new PropertyValueFactory<>("Product_ID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("Product_Name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colProductQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("Supplier_ID"));

        ObservableList<Product> productObservableList = FXCollections.observableArrayList();

        try {
            // SQL query to fetch data from the employee table
            String sql = "SELECT * FROM product;";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            // Loop through the result set and add each employee to the list
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("Product_ID"),
                        resultSet.getString("Product_Name"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("Qty"),
                        resultSet.getString("Supplier_ID")

                );
                System.out.println(product);
                productObservableList.add(product);
            }

            // Set the items in the table to display the employee data
            tblProduct.setItems(productObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
