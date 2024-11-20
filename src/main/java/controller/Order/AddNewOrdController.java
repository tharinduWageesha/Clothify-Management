package controller.Order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Product.ProductController;
import controller.Product.ProductService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.CartTM;
import dto.Order;
import dto.OrderDetails;
import dto.Product;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewOrdController implements Initializable {

    public Text txtmsg;
    public ImageView btnSearch;
    public Text txtmsg2;
    public Text lblCost;
    public Text lblDis;
    public Text lblBill;
    ProductService service = new ProductController();
    OrderService service2= new OrderController();
    public Text lblDate;
    public JFXTextField txtProID;
    @FXML
    private Text lblTime;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnNewItem;

    @FXML
    private JFXComboBox<?> cmdProID;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProId;

    @FXML
    private TableColumn<?, ?> colProName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Text date;

    @FXML
    private Text order_id;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private Text time;

    @FXML
    private JFXTextField txtProName;

    @FXML
    private JFXTextField txtProPrice;

    @FXML
    private JFXTextField txtProQty;

    @FXML
    private JFXTextField txtSupID;

    @FXML
    private Text verifyMsg;
    private int crntqty = 0;
    private double finalTot=0;
    private double discount=1;
    private double bill=0;
    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();


    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        if(txtProID.getText().equals("") && txtProName.getText().equals("") && txtProPrice.getText().equals("")){
            txtmsg2.setText("Get the Product Details by searching Product ID !");
        } else {
            if(txtProQty.getText().equals("")){
                txtmsg2.setText("Please enter the Quantity !");
            }else{
                colProId.setCellValueFactory(new PropertyValueFactory<>("productID"));
                colProName.setCellValueFactory(new PropertyValueFactory<>("proname"));
                colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

                String proname = txtProName.getText();
                String proID = txtProID.getText();
                double unitPrice = Double.parseDouble(txtProPrice.getText());
                int qty = Integer.parseInt(txtProQty.getText());
                if (crntqty < qty) {
                    txtmsg2.setText("Product Out of Stock ( Min - " + crntqty + " )");
                } else {
                    txtmsg2.setText("");
                    txtmsg.setText("");
                    Double total = qty * unitPrice;
                    cartTMS.add(new CartTM(proID, proname, qty, unitPrice, total));
                    tblCart.setItems(cartTMS);
                    finalTot = finalTot + total;

                    // Apply a fixed discount rate (10%)
                    discount = 0.1;

                    // Calculate the final bill after applying discount
                    Double discountAmount = finalTot * discount;
                    bill = finalTot - discountAmount;

                    // Format discount percentage and update labels
                    String formattedDiscount = String.format("%.2f", discount * 100);
                    lblCost.setText(String.valueOf(finalTot));
                    lblDis.setText(formattedDiscount);
                    lblBill.setText(String.valueOf(bill));

                    txtProPrice.setText("");
                    txtProName.setText("");
                    txtProQty.setText("");
                    txtSupID.setText("");
                }

            }
        }
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if(tblCart.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please add items to the cart !").show();
        }else {
            String oID = order_id.getText();
            LocalDate date = LocalDate.now();
            double discount = Double.parseDouble(lblDis.getText());
            double cost = Double.parseDouble(lblBill.getText());
            LocalTime time = LocalTime.now();
            List<OrderDetails> orderDetails = new ArrayList<>();
            cartTMS.forEach(obj -> {
                orderDetails.add(new OrderDetails(oID, obj.getProductID(), obj.getQty()));
            });
            Order order = new Order(oID, date, discount, cost, time, orderDetails);
            boolean isOrderPlaced = false;
            try {
                isOrderPlaced = service2.placeOrder(order);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (isOrderPlaced) {
                btnAdd.setDisable(true);
                new Alert(Alert.AlertType.INFORMATION, "Order Placed Succesfully !").show();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed !").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        String OrderID = generateOrdID();
        order_id.setText(OrderID);
    }

    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String crntdate = format.format(date);
        lblDate.setText(crntdate);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void btnSearchOnAction(MouseEvent event) {
        Product product = service.serachProduct(txtProID.getText());
        if(product == null){
            txtmsg.setFill(Color.rgb(239, 102, 102));
            txtmsg.setText("Invalid Product ID !");
        }else{
            txtmsg.setFill(Color.rgb(135, 206, 235));
            txtmsg.setText("Product found ( Stock - "+product.getQty()+" )");
            setTextToValues(product);
            txtmsg2.setText("");

        }
    }

    private void setTextToValues(Product product) {
        txtProName.setText(product.getProduct_Name());
        txtProPrice.setText(String.valueOf(product.getPrice()));
        txtSupID.setText(product.getSupplier_ID());
        crntqty = product.getQty();
    }

    private String generateOrdID() {
        String lastID = service2.getLastOrdID();
        if (lastID != null) {
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return "O" + String.format("%04d", numericPart);
        } else {
            return "O0001";
        }
    }
}
