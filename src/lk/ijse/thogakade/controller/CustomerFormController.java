package lk.ijse.thogakade.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.thogakade.DataBaseAccessCode;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerSalary;
    public JFXButton btnSaveCustomer;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOptions;
    public TextField txtSearch;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));


        loadAllCustomers("");


        //-----------
        tblCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue!=null){
                        setData(newValue);
                    }

        });
        
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllCustomers(newValue);
        });

        //-----------
    }

    private void setData(CustomerTM tm) {
        btnSaveCustomer.setText("Update Customer");
        txtCustomerId.setText(tm.getId());
        txtCustomerName.setText(tm.getName());
        txtCustomerAddress.setText(tm.getAddress());
        txtCustomerSalary.setText(String.valueOf(tm.getSalary()));
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerContext.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(Objects.requireNonNull(getClass()
                                .getResource("../view/DashBoardForm.fxml")))));
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
        clearFields();
    }
    private void clearFields(){
        txtCustomerId.clear();
        txtCustomerSalary.clear();
        txtCustomerAddress.clear();
        txtCustomerName.setText("");
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {

        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")){
            try {
                CustomerDTO dto = new CustomerDTO(
                        txtCustomerId.getText(),
                        txtCustomerName.getText(),
                        txtCustomerAddress.getText(),
                        Double.parseDouble(txtCustomerSalary.getText())
                );
                if (new DataBaseAccessCode().saveCustomer(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer was Saved", ButtonType.OK).show();
                    loadAllCustomers("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong! please try again.", ButtonType.CANCEL).show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                CustomerDTO dto = new CustomerDTO(
                        txtCustomerId.getText(),
                        txtCustomerName.getText(),
                        txtCustomerAddress.getText(),
                        Double.parseDouble(txtCustomerSalary.getText())
                );
                if (new DataBaseAccessCode().updateCustomer(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer was Updated", ButtonType.OK).show();
                    loadAllCustomers("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong! please try again.", ButtonType.CANCEL).show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadAllCustomers(String searchText) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        try {

            for (CustomerDTO tempDto: new DataBaseAccessCode().getAllCustomers("%" + searchText + "%")
                 ) {
                Button btn = new Button("Delete");
                CustomerTM customerTM = new CustomerTM(
                        tempDto.getId(),
                        tempDto.getName(),
                        tempDto.getAddress(),
                        tempDto.getSalary(),
                        btn
                );
                obList.add(customerTM);

                //---- Delete Implementation
                btn.setOnAction(e -> {

                    Alert confirmation = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are You sure?",
                            ButtonType.YES, ButtonType.CLOSE
                    );
                    Optional<ButtonType> confirmState = confirmation.showAndWait();
                    if (confirmState.get().equals(ButtonType.YES)) {
                        try {
                            if (new DataBaseAccessCode().deleteCustomer(customerTM.getId())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Customer was Deleted", ButtonType.OK).show();
                                loadAllCustomers("");
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Something went wrong! please try again.", ButtonType.CANCEL).show();
                            }

                        } catch (ClassNotFoundException | SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                });
                //---- Delete Implementation

            }
            tblCustomer.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
