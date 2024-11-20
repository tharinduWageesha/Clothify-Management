package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewReportController implements Initializable {

    @FXML
    private AreaChart<?, ?> chrtDaily;

    @FXML
    private AreaChart<?, ?> chrtMonthly;

    @FXML
    private AreaChart<?, ?> chrtWeekly;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
