package lk.ijse.thogakade.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardFormController {


    public AnchorPane dashboardContext;

    public void loadOrderForm(MouseEvent mouseEvent) throws IOException {
        setUi("OrderForm");
    }

    public void loadCustomerForm(MouseEvent mouseEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void loadItemForm(MouseEvent mouseEvent) throws IOException {
        setUi("ItemForm");
    }

    public void loadOrderDetailsForm(MouseEvent mouseEvent) throws IOException {
        setUi("OrderDetailsForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(getClass()
                                .getResource("../view/"+location+".fxml"))));
    }

}
