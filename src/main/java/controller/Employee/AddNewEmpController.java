package controller.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Employee;

public class AddNewEmpController {
    EmployeeService service = new EmployeeController();
    public Text txtalertSucces;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXTextField txtEmpCon;

    @FXML
    private JFXTextField txtEmpEmail;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtEmpStatus;

    @FXML
    private Text verifyMsg;

    @FXML
    void btnAddEmpOnAction(ActionEvent event) {
        if(txtEmpName.getText().equals("") || txtEmpStatus.getText().equals("") || txtEmpCon.getText().equals("") || txtEmpEmail.getText().equals("")){
            verifyMsg.setText("Please fill all the fields.");
        }else {
            String empID = generateEmpID();
            String empName = txtEmpName.getText();
            String empEmail = txtEmpEmail.getText();
            String empCon = txtEmpCon.getText();
            String empStatus = txtEmpStatus.getText();

            Employee employee = new Employee(empID, empName, empEmail, empCon, empStatus);
            System.out.println(employee);
            boolean isSucces = new EmployeeController().addEmployee(employee);

            if (isSucces) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Added Succesfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Added!").show();
            }
        }

    }

    private String generateEmpID() {
        String lastID = service.getLastEmpID();
        if (lastID != null) {
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return "E" + String.format("%04d", numericPart);
        } else {
            return "E0001";
        }
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
