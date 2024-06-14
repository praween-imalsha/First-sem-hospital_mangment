package lk.ijse.ayurvedic_hospital.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ayurvedic_hospital.Util.Regex;
import lk.ijse.ayurvedic_hospital.model.Ward;
import lk.ijse.ayurvedic_hospital.repository.WardRepo;
import lk.ijse.ayurvedic_hospital.tm.WardTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Wardfromcontroller {

    @FXML
    private TableColumn<?, ?> colUser_id;

    @FXML
    private TableColumn<?, ?> colWar_capacity;

    @FXML
    private TableColumn<?, ?> colWar_id;

    @FXML
    private TableColumn<?, ?> colWar_name;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<WardTm> tblWard;
    @FXML
    private TextField txtWarName;
    @FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtWarId;

    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Form");
        stage.show();

    }

    @FXML
    void btnclearOnAction(ActionEvent event) {

        clearFields();


    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String warId = txtWarId.getText();
        try {
            boolean isDeleted = WardRepo.delete(warId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "ward deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllWard();
        }
    }

    @FXML
    void btnsaveOnAction(ActionEvent event) {
        String warId = txtWarId.getText();
        String warName = txtWarName.getText();
        String warCapacity = txtCapacity.getText();
        String userId = txtUserId.getText();

        if (isValied()) {
            if (warId.isEmpty() || warName.isEmpty() ||warCapacity.isEmpty() || userId.isEmpty() ) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Ward ward = new Ward(warId, warName, warCapacity, userId);

            // Save the client
            try {
                boolean isSaved = WardRepo.save(ward);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Dealer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllWard();
            }

        }
    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtWarId)) return false;
        return false;
    }

    private void clearFields() {
        txtWarId.clear();
        txtWarName.clear();
        txtCapacity.clear();
        txtUserId.clear();
    }


    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String warId = txtWarId.getText();
        String warName = txtWarName.getText();
        String warCapacity = txtCapacity.getText();
        String userId = txtUserId.getText();

        Ward ward = new Ward(warId, warName, warCapacity, userId);

        try {
            boolean isUpdated = WardRepo.update(ward);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "ward updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }finally {
            loadAllWard();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String warId = txtWarId.getText();
        Ward ward = WardRepo.searchById(warId);
        if (ward != null) {
            txtWarId.setText(ward.getWar_id());
            txtWarName.setText(ward.getWar_name());
            txtCapacity.setText(ward.getWar_capacity());
            txtUserId.setText(ward.getUser_id());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Ward not found!").show();
        }
    }
    private void setCellValueFactory() {
        colWar_id.setCellValueFactory(new PropertyValueFactory<>("War_id"));
        colWar_name.setCellValueFactory(new PropertyValueFactory<>("War_name"));
        colWar_capacity.setCellValueFactory(new PropertyValueFactory<>("War_capacity"));
        colUser_id.setCellValueFactory(new PropertyValueFactory<>("User_id"));


    }

    private void loadAllWard() {
        ObservableList<WardTm> obList = FXCollections.observableArrayList();

        try {
            List<Ward> wardList = WardRepo.getAllWards();
            for (Ward ward : wardList) {
                WardTm wardTm = new WardTm(
                        ward.getWar_id(),
                        ward.getWar_name(),
                        ward.getWar_capacity(),
                        ward.getUser_id()

                );


                obList.add(wardTm);
            }
            tblWard.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        setCellValueFactory();
        loadAllWard();




    }

    public void txtwardidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtWarId);
    }
}
