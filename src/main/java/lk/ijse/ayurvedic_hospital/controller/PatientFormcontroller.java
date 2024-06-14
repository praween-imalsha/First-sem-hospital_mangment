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
import lk.ijse.ayurvedic_hospital.model.Patient;
import lk.ijse.ayurvedic_hospital.repository.PatientRepo;
import lk.ijse.ayurvedic_hospital.tm.PatientTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PatientFormcontroller {

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colPatient_address;

    @FXML
    private TableColumn<?, ?> colPatient_id;

    @FXML
    private TableColumn<?, ?> colPatient_name;

    @FXML
    private TableColumn<?, ?> colWar_id;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<PatientTm> tblPaitent;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmployeeid;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtPatientaddress;

    @FXML
    private TextField txtPatientid;

    @FXML
    private TextField txtPatientname;

    @FXML
    private TextField txtWarid;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Form");
        stage.show();



    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String patientId = txtPatientid.getText();
        Patient patient = PatientRepo.searchById(patientId);
        if (patient != null) {
            txtPatientid.setText(patient.getPatient_id());
           txtPatientname.setText(patient.getPatient_name());
            txtPatientaddress.setText(patient.getPatient_address());
            txtAge.setText(patient.getAge());
           txtGender.setText(patient.getGender());
            txtWarid.setText(patient.getWar_id());
            txtEmployeeid.setText(patient.getEmployee_id());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Patient not found!").show();
        }



    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String patientId = txtPatientid.getText();
        try {
            boolean isDeleted = PatientRepo.delete(patientId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllPatient();
        }



    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Patientid = txtPatientid.getText();
        String Patientname = txtPatientname.getText();
        String Patientaddress = txtPatientaddress.getText();
        String Age = txtAge.getText();
        String Gender = txtGender.getText();
        String Warid = txtWarid.getText();
        String Empolyeeid = txtEmployeeid.getText();

        if (isValied()) {
            if (Patientid.isEmpty() || Patientname.isEmpty() || Patientaddress.isEmpty() || Age.isEmpty() || Gender.isEmpty() ||Warid.isEmpty()||Empolyeeid.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Patient patient = new Patient(Patientid,Patientname,Patientaddress,Age,Gender,Warid,Empolyeeid);


            // Save the client
            try {
                boolean isSaved = PatientRepo.save(patient);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Dealer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllPatient();
            }

        }




    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.patientid,txtPatientid)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.name,txtPatientname)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtWarid)) return false;
        return true;
    }

    private void clearFields() {
       txtPatientid.clear();
        txtPatientname.clear();
        txtPatientaddress.clear();
        txtAge.clear();
        txtGender.clear();
        txtWarid.clear();
        txtEmployeeid.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String Patientid = txtPatientid.getText();
        String Patientname = txtPatientname.getText();
        String Patientaddress = txtPatientaddress.getText();
        String Age = txtAge.getText();
        String Gender = txtGender.getText();
        String Warid = txtWarid.getText();
        String Empolyeeid = txtEmployeeid.getText();


        Patient patient = new Patient(Patientid,Patientname,Patientaddress,Age,Gender,Warid,Empolyeeid);

        try {
            boolean isUpdated = PatientRepo.update(patient);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "patient updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllPatient();
        }
    }
    private void setCellValueFactory() {
        colPatient_id.setCellValueFactory(new PropertyValueFactory<>("Patient_id"));
        colPatient_name.setCellValueFactory(new PropertyValueFactory<>("Patient_name"));
        colPatient_address.setCellValueFactory(new PropertyValueFactory<>("Patient_address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colWar_id.setCellValueFactory(new PropertyValueFactory<>("War_id"));
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));


    }

    private void loadAllPatient() {
        ObservableList<PatientTm> obList = FXCollections.observableArrayList();

        try {
            List<Patient> patientList = PatientRepo.getAllPatient();
            for (Patient patient: patientList) {
                PatientTm patientTm = new PatientTm(
                        patient.getPatient_id(),
                        patient.getPatient_name(),
                        patient.getPatient_address(),
                        patient.getAge(),
                        patient.getGender(),
                        patient.getWar_id(),
                        patient.getEmployee_id()

                );


                obList.add(patientTm);
            }
            tblPaitent.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(){
        setCellValueFactory();
        loadAllPatient();
    }

    public void txtPatientIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.patientid,txtPatientid);
    }

    public void txtWardidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtWarid);
    }

    public void txtPatientnmaeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.name,txtPatientname);
    }
}



