package controller.Employee;

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
import model.Product;
import utilDBOPT.CRUDUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmpManageController implements Initializable {

    public TableView<Employee > tblEmployee;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colContactNo;
    public TableColumn colStatus;
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
    private JFXTextField txtConNo;

    @FXML
    private JFXTextField txtEmpEmail;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtEmpStatus;

    @FXML
    private JFXTextField txtEmpID;

    EmployeeService service = new EmployeeController();

    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/addnewEmp.fxml"))));
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
        boolean isSuccess = service.deleteEmployee(txtEmpID.getText());
        if(isSuccess){
            setDataToTable();
            new Alert(Alert.AlertType.INFORMATION,"Employee Deleted Succesfully!").show();
        }

    }

    @FXML
    void btnNotOnAction(MouseEvent event) {
        setDataToTable();
    }

    @FXML
    void btnSearchOnAction(MouseEvent event) {
        Employee employee = service.searchEmployee(searchbar.getText());
        if(employee == null){
            txtverifymsg.setText("No results found...");
        }else{
            setTextToValues(employee);
        }

    }

    @FXML
    void btnUpdOnAction(MouseEvent event) {
        if (txtEmpID.getText().equals("") || txtEmpName.getText().equals("") || txtEmpStatus.getText().equals("") || txtEmpEmail.getText().equals("")) {
            txtverifymsg.setText("Please fill all the fields.");
        } else {
            try {
                // Auto-generate the Product ID
                String empID = txtEmpID.getText();
                String empName = txtEmpName.getText();
                String empEmail = txtEmpEmail.getText();
                String empConNo = txtConNo.getText();
                String empStatus = txtEmpStatus.getText();

                Employee employee = new Employee(empID, empName, empEmail, empConNo,empStatus);
                System.out.println(employee);

                boolean isSuccess = service.updateEmployee(employee);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").show();
                    setDataToTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error Updating Employee!").show();
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
    void btnsProfOnAction(MouseEvent event) {
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
        setDataToTable();
        tblEmployee.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));

    }

    private void setTextToValues(Employee newValue) {
        txtEmpID.setText(newValue.getEmpID());
        txtEmpName.setText(newValue.getName());
        txtConNo.setText(newValue.getContactNo());
        txtEmpEmail.setText(newValue.getEmail());
        txtEmpStatus.setText(newValue.getStatus());

    }

    public EmpManageController setDataToTable(){
        // Set the cell value factories to map the Employee properties with the table columns
        colID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Create an ObservableList to hold the Employee data
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee;";
        try {
            ResultSet resultSet = CRUDUtil.execute(sql);

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString("Employee_ID"),
                        resultSet.getString("Employee_Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("ContactNo"),
                        resultSet.getString("Status")
                );
                System.out.println(employee);
                employeeObservableList.add(employee);
            }

            tblEmployee.setItems(employeeObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
