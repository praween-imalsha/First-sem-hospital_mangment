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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ayurvedic_hospital.Util.Regex;
import lk.ijse.ayurvedic_hospital.model.Medicen;
import lk.ijse.ayurvedic_hospital.repository.MedicenRepo;
import lk.ijse.ayurvedic_hospital.tm.MedicenTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Medicencontroller {


    @FXML
    private TableColumn<?, ?> colMedicen_id;

    @FXML
    private TableColumn<?, ?> colMedicen_name;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colunit_price;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<MedicenTm> tblMedicen;

    @FXML
    private TextField txtmedicenId;

    @FXML
    private TextField txtmedicenName;

    @FXML
    private TextField txtquantity;
    @FXML
    private TextField txtunit_price;




    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)pane.getScene().getWindow();
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
        String medicenId = txtmedicenId.getText();
        try {
            boolean isDeleted = MedicenRepo.delete(medicenId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicen deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllMedicen();
        }

    }

    @FXML
    void btnsaveOnAction(ActionEvent event) {
        String medicenId = txtmedicenId.getText();
        String medicenName = txtmedicenName.getText();
        double unitprice = Double.parseDouble(txtunit_price.getText());
       int Qty = Integer.parseInt(txtquantity.getText());

        // Create a new Medicen object
        if (isValied()) {
            if (medicenId.isEmpty() || medicenName.isEmpty() || unitprice <= 0 || Qty <= 0)  {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Medicen medicen = new Medicen(medicenId, medicenName, unitprice, Qty);

            // Save the client
            try {
                boolean isSaved = MedicenRepo.save(medicen);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Dealer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllMedicen();
            }

        }
    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.medicenid,txtmedicenId)) return false;
        return true;
    }


    private void clearFields() {
        txtmedicenId.setText("");
        txtmedicenName.setText("");
        txtunit_price.setText("");
        txtquantity.setText("");
    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String medicenId = txtmedicenId.getText();
        String medicenName = txtmedicenName.getText();
        double unitprice = Double.parseDouble(txtunit_price.getText());
        int Qty = Integer.parseInt(txtquantity.getText());

        Medicen medicen = new Medicen(medicenId,medicenName,unitprice,Qty);
        try {
            boolean isUpdated = MedicenRepo.update(medicen);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicen updated!").show();
            }
        } catch (SQLException e) {

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllMedicen();
        }

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String medicenId = txtmedicenId.getText();
         Medicen medicen = MedicenRepo.searchById(medicenId);
         if (medicen != null) {
            txtmedicenId.setText(medicen.getMedicen_id());
            txtmedicenName.setText(medicen.getMedicen_name());
            txtunit_price.setText(String.valueOf(medicen.getUnit_price()));
            txtquantity.setText(String.valueOf(medicen.getQty()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Medicen not found!").show();
        }
    }

    private void setCellValueFactory() {
        colMedicen_id.setCellValueFactory(new PropertyValueFactory<>("Medicen_id"));
        colMedicen_name.setCellValueFactory(new PropertyValueFactory<>("Medicen_name"));
        colunit_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));

    }

    private void loadAllMedicen() {
        ObservableList<MedicenTm> obList = FXCollections.observableArrayList();

        try {
            List<Medicen> medicenList = MedicenRepo.getAllMedicen();
            for (Medicen medicen : medicenList) {
                MedicenTm medicenTm = new MedicenTm(
                        medicen.getMedicen_id(),
                        medicen.getMedicen_name(),
                        medicen.getUnit_price(),
                        medicen.getQty()

                );


                obList.add(medicenTm);
            }
            tblMedicen.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(){
        setCellValueFactory();
        loadAllMedicen();
    }

    public void txtmedicenIdOnKeyReleased(ActionEvent actionEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.medicenid,txtmedicenId);

    }
}
