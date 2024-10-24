package controller.Order;

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
import model.Order;
import model.Product;

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
        txtOrderDis.setText(String.valueOf(newValue.getDiscount()));
        txtOrderTot.setText(String.valueOf(newValue.getCost()));
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
    void btnViewRepOnAction(ActionEvent event) {

    }

    @FXML
    void btnsetOnAction(MouseEvent event) {

    }




//    ----------------------------------------------  Database Connection  ---------------------------------------------


}
