package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Car;
import sample.models.Rent;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Statistics controller.
 */
public class StatisticsController {

    @FXML
    private BarChart<String, Double> moneyChart;

    @FXML
    private Label totalLabel;

    private Main mainApp;
    private Stage dialogueStage;
    private Car clickedCar;
    private RestApiRequests requests = new RestApiRequests();

    /**
     * Initialize.
     *
     * @param mainApp    the main app
     * @param stage      the stage
     * @param clickedCar the clicked car
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Car clickedCar) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedCar = clickedCar;

        setChart(clickedCar);
    }

    private void setChart(Car car) throws IOException {
        Double total = 0.0;
        ObservableList<Rent> rentData = requests.getRentsByCarId(car.getCarId());
        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
        for (int i = 0; i< rentData.size(); i++){
            total+=rentData.get(i).getFinalPrice();
            series.getData().add(
                    new XYChart.Data<String, Double>(String.valueOf(rentData.get(i).startDateProperty().get()),
                            rentData.get(i).finalPriceProperty().get())
            ); }
        moneyChart.getData().add(series);
        totalLabel.setText(total +"₽");
    }


}
