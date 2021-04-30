package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Car;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {
    private final String url1 = "https://github.com/AnnBengardt/CarRentDesktop";
    private final String url2 = "https://github.com/AnnBengardt/CarRentDesktopInterface";

    public void initialize() throws IOException {
    }


    @FXML
    private void redirectToServer() throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI(url1));
    }

    @FXML
    private void redirectToInterface() throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI(url2));
    }




}
