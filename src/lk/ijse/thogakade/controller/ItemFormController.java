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
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.view.tm.CustomerTM;
import lk.ijse.thogakade.view.tm.ItemTM;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane itemContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQTYOnHand;
    public JFXButton btnSave;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOptions;
    public TextField txtSearch;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


        loadAllItems("");

        //-----------
        tblItem.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue!=null){
                        setData(newValue);
                    }

                });

        //---------------
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllItems(newValue);
        });
    }

    private void setData(ItemTM tm) {
        btnSave.setText("Update Item");
        txtItemCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        txtQTYOnHand.setText(String.valueOf(tm.getQtyOnHand()));
    }

    private void clearFields(){
        txtItemCode.clear();
        txtUnitPrice.clear();
        txtDescription.clear();
        txtQTYOnHand.setText("");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemContext.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(Objects.requireNonNull(getClass()
                                .getResource("../view/DashBoardForm.fxml")))));
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Item");
        clearFields();
    }

    public void saveItemOnAction(ActionEvent actionEvent) {

        if (btnSave.getText().equalsIgnoreCase("Save Item")){
            try {

                ItemDTO dto = new ItemDTO(
                        txtItemCode.getText(),txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQTYOnHand.getText())
                );
                if (new DataBaseAccessCode().saveItem(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item was Saved", ButtonType.OK).show();
                    loadAllItems("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong! please try again.", ButtonType.CANCEL).show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                ItemDTO dto = new ItemDTO(
                        txtItemCode.getText(),txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQTYOnHand.getText())
                );

                if (new DataBaseAccessCode().updateItem(dto)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item was Updated", ButtonType.OK).show();
                    loadAllItems("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong! please try again.", ButtonType.CANCEL).show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private void loadAllItems(String searchText) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        try {
            for (ItemDTO tempDTO:new DataBaseAccessCode().getAllItems("%" + searchText + "%")
                 ) {
                Button btn = new Button("Delete");
                ItemTM itemTM = new ItemTM(
                        tempDTO.getCode(),
                        tempDTO.getDescription(),
                        tempDTO.getUnitPrice(),
                        tempDTO.getQtyOnHand(),
                        btn
                );
                obList.add(itemTM);
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
                            if (new DataBaseAccessCode().deleteItem(itemTM.getCode())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Item was Deleted", ButtonType.OK).show();
                                loadAllItems("");
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
            tblItem.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
