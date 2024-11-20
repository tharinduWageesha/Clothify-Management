package controller.Order;

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
import dto.Order;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrderManageController implements Initializable {

    public TableColumn coldOrdID;

    public TableColumn colOrdDate;

    public TableColumn colOrdTime;

    public TableColumn colOrdDis;

    public TableColumn colOrdTot;

    public TableView<Order> ordTable;
    public Text txtverifymsg;

    @FXML
    private JFXButton btnAddNew;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnDel;

    @FXML
    private ImageView btnProf;

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
    private ImageView btnset;

    @FXML
    private JFXTextField searchbar;

    @FXML
    private JFXTextField txtOrderDate;

    @FXML
    private JFXTextField txtOrderDis;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtOrderTime;

    @FXML
    private JFXTextField txtOrderTot;

    OrderService service = new OrderController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable();
        ordTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));
    }

    private void setTextToValues(Order newValue) {
        txtOrderId.setText(newValue.getOrder_ID());
        txtOrderDate.setText(String.valueOf(newValue.getDate()));
        txtOrderTime.setText(String.valueOf(newValue.getTime()));
        txtOrderDis.setText(String.valueOf(newValue.getDiscount())+"%");
        txtOrderTot.setText("Rs "+String.valueOf(newValue.getCost()));
    }

    public void setDataToTable(){
        coldOrdID.setCellValueFactory(new PropertyValueFactory<>("Order ID"));
        colOrdDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colOrdTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colOrdDis.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        colOrdTot.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

        try {
            String sql = "Select* from orders;";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            while(resultSet.next()){

                Order order = new Order(
                        resultSet.getString("order_ID"),
                        resultSet.getDate("Date").toLocalDate(),
                        resultSet.getDouble("Discount"),
                        resultSet.getDouble("Cost"),
                        resultSet.getTime("Time").toLocalTime()
                );
                System.out.println(order);
                orderObservableList.add(order);
            }
            ordTable.setItems(orderObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addNewOrd.fxml"))));
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
        boolean isSuccess = service.deleteOrder(txtOrderId.getText());
        if(isSuccess){
            setDataToTable();
            new Alert(Alert.AlertType.INFORMATION,"Order Deleted Succesfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error Deleting Order!").show();
        }
    }

    @FXML
    void btnNotOnAction(MouseEvent event) {
        setDataToTable();
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
    void btnSearchOnAction(MouseEvent event) {
        Order order = service.serachOrder(searchbar.getText());
        if(order == null){
            txtverifymsg.setText("No results found...");
        }else{
            setTextToValues(order);
        }
    }

    @FXML
    void btnSetOnAction(MouseEvent event) {


    }

    @FXML
    void btnUpdOnAction(MouseEvent event) {

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
                Paragraph title = new Paragraph("Order Report", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph(" "));

                // Create table with appropriate column count
                PdfPTable pdfTable = new PdfPTable(ordTable.getColumns().size());
                pdfTable.setWidthPercentage(100);

                // Add table headers
                ordTable.getColumns().forEach(column -> {
                    PdfPCell headerCell = new PdfPCell(new Phrase(column.getText()));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(headerCell);
                });

                // Add table rows
                ordTable.getItems().forEach(item -> {
                    ordTable.getColumns().forEach(column -> {
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




//    ----------------------------------------------  Database Connection  ---------------------------------------------


}
