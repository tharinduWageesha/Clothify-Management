package controller.Order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Product.ProductController;
import controller.Product.ProductService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Product;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class AddNewOrdController implements Initializable {

    public Text txtmsg;
    ProductService service = new ProductController();
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
    private TableView<?> tblCart;

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

    @FXML
    void btnAddCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
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
            txtmsg.setText("Invalid Product ID !");
        }else{
            setTextToValues(product);
            txtmsg.setText(null);
        }
    }

    private void setTextToValues(Product product) {
        txtProName.setText(product.getProduct_Name());
        txtProPrice.setText(String.valueOf(product.getPrice()));
        txtProQty.setText(String.valueOf(product.getQty()));
        txtSupID.setText(product.getSupplier_ID());
    }
}
