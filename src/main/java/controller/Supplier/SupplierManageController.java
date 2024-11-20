package controller.Supplier;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dto.Supplier;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SupplierManageController implements Initializable {

    public Text txtverifymsg;
    SupplierService service = new SupplierController();
    public TableView<Supplier> tblSupplier;
    public TableColumn colSupID;
    public TableColumn colSupName;
    public TableColumn colSupEmail;
    public TableColumn colSupCompany;
    public TableColumn colSupItemID;
    public JFXTextField txtID;
    public JFXTextField txtCompany;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtProID;
    @FXML
    private JFXButton btnAddNew;

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
    void btnAddNewOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addnewSup.fxml"))));
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
        boolean isSuccess = service.deleteSupplier(txtID.getText());
        if(isSuccess){
            setDataToTable();
            new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted Succesfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error Deleting Supplier!").show();
        }
    }

    @FXML
    void btnNotOnAction(MouseEvent event) {
        setDataToTable();
    }

    @FXML
    void btnSearchOnAction(MouseEvent event) {
        Supplier supplier = service.serachSupplier(searchbar.getText());
        setTextToValues(supplier);
        if(supplier == null){
            txtverifymsg.setText("No results found...");
        }else{
            setTextToValues(supplier);
        }
    }

    @FXML
    void btnProfOnAction(MouseEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/profile.fxml"))));
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdOnAction(MouseEvent event) {
        if (txtProID.getText().equals("") || txtEmail.getText().equals("") || txtID.getText().equals("") || txtName.getText().equals("")) {
            txtverifymsg.setText("Please fill all the fields.");
        } else {
            try {
                // Auto-generate the Product ID
                String supID = txtID.getText();
                String supName = txtName.getText();
                String company = txtCompany.getText();
                String email = txtEmail.getText();
                String pro_id = txtProID.getText();

                Supplier supplier = new Supplier(supID, supName, company, email,pro_id);
                System.out.println(supplier);

                boolean isSuccess = service.updateSupplier(supplier);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully!").show();
                    setDataToTable();
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
    public void btnViewRepOnAction(ActionEvent event) {
        // File chooser to specify PDF save location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        java.io.File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Create a new document
            Document document = new Document();

            try {
                // Initialize PDF writer
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Add document title
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                Paragraph title = new Paragraph("Supplier Report", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph(" "));

                // Create table with appropriate column count
                PdfPTable pdfTable = new PdfPTable(tblSupplier.getColumns().size());
                pdfTable.setWidthPercentage(100);

                // Add table headers
                tblSupplier.getColumns().forEach(column -> {
                    PdfPCell headerCell = new PdfPCell(new Phrase(column.getText()));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(headerCell);
                });

                // Add table rows
                tblSupplier.getItems().forEach(item -> {
                    tblSupplier.getColumns().forEach(column -> {
                        Object cellData = column.getCellObservableValue(item).getValue();
                        pdfTable.addCell(cellData != null ? cellData.toString() : "");
                    });
                });

                // Add table to document
                document.add(pdfTable);

                // Confirmation message
                new Alert(Alert.AlertType.INFORMATION, "PDF Generated Succesfully").show();

            } catch (FileNotFoundException | DocumentException e) {
                System.err.println("Error generating PDF: " + e.getMessage());
            } finally {
                document.close();
            }
        }
    }

    @FXML
    void btnsetOnAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable();
        tblSupplier.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));
    }

    private void setTextToValues(Supplier newValue) {
        txtID.setText(newValue.getSupplier_ID());
        txtCompany.setText(newValue.getCompany());
        txtName.setText(newValue.getSupplier_Name());
        txtEmail.setText(newValue.getEmail());
        txtProID.setText(newValue.getProduct_ID());

    }

    private void setDataToTable() {
        colSupID.setCellValueFactory(new PropertyValueFactory<>("Supplier_ID"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("Supplier_Name"));
        colSupCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colSupEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colSupItemID.setCellValueFactory(new PropertyValueFactory<>("Product_ID"));

        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();

        try {
            String sql = "Select* from supplier;";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            while(resultSet.next()){

                Supplier supplier = new Supplier(
                        resultSet.getString("Supplier_ID"),
                        resultSet.getString("Supplier_Name"),
                        resultSet.getString("Company"),
                        resultSet.getString("Email"),
                        resultSet.getString("Product_ID")
                );
                System.out.println(supplier);
                supplierObservableList.add(supplier);
            }
            tblSupplier.setItems(supplierObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
