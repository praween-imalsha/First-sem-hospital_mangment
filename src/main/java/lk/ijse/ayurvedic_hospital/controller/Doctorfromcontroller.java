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
import lk.ijse.ayurvedic_hospital.model.Doctor;
import lk.ijse.ayurvedic_hospital.repository.DoctorRepo;
import lk.ijse.ayurvedic_hospital.tm.DoctorTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class Doctorfromcontroller {


    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<?, ?> colDoctor_id;

    @FXML
    private TableColumn<?, ?> colDoctor_name;

    @FXML
    private TableColumn<?, ?> colDoctor_contact;

    @FXML
    private TableColumn<?, ?> colSpecialization;

    @FXML
    private TableColumn<?, ?> colWard_id;

    @FXML
    private TableView<DoctorTm> tblDoctor;

    @FXML
    private TextField txtdoctorid;

    @FXML
    private TextField txtdoctorcontact;

    @FXML
    private TextField txtdoctorname;

    @FXML
    private TextField txtspecialization;

    @FXML
    private TextField txtwardid;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
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

    private void clearFields() {
        txtdoctorid.setText("");
        txtdoctorname.setText("");
        txtdoctorcontact.setText("");
        txtspecialization.setText("");
        txtwardid.setText("");

    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String Doctor_id = txtdoctorid.getText();


        try {
            boolean isDeleted = DoctorRepo.delete(Doctor_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Doctor deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllDoctor();
        }
    }

    @FXML
    void btnsaveOnAction(ActionEvent event) {

        String Doctor_id = txtdoctorid.getText();
        String Doctor_name = txtdoctorname.getText();
        String Doctor_contact = txtdoctorcontact.getText();
        String specialization = txtspecialization.getText();
        String Ward_id = txtwardid.getText();
        if (isValied()) {
            if (Doctor_id.isEmpty() || Doctor_name.isEmpty() || Doctor_contact.isEmpty() || specialization.isEmpty() || Ward_id.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Doctor doctor = new Doctor(Doctor_id, Doctor_name, Doctor_contact, specialization, Ward_id);


            // Save the client
            try {
                boolean isSaved = DoctorRepo.save(doctor);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllDoctor();
            }

        }

    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorid,txtdoctorid)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorname,txtdoctorname)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.contact,txtdoctorcontact)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.spealization,txtspecialization)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtwardid)) return false;

        return true;
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String Doctor_id = txtdoctorid.getText();
        Doctor doctor = DoctorRepo.searchById(Doctor_id);
        if (doctor != null) {
            txtdoctorid.setText(doctor.getDoctor_id());
            txtdoctorname.setText(doctor.getDoctor_name());
            txtdoctorcontact.setText(doctor.getDoctor_contact());
           txtspecialization.setText(doctor.getSpecialization());
            txtwardid.setText(doctor.getWard_id());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Doctor not found!").show();
        }

    }
    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String Doctor_id = txtdoctorid.getText();
        String Doctor_name = txtdoctorname.getText();
        String Doctor_contact = txtdoctorcontact.getText();
        String specialization = txtspecialization.getText();
        String Ward_id = txtwardid.getText();

        Doctor doctor = new Doctor(Doctor_id, Doctor_name, Doctor_contact, specialization, Ward_id);

        try {
            boolean isUpdated = DoctorRepo.update(doctor);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllDoctor();
        }



    }
    public void initialize(){
        setCahart();
        setCellValueFactory();
        loadAllDoctor();
    }

    private void setCahart() {

    }

    private void setCellValueFactory() {
        colDoctor_id.setCellValueFactory(new PropertyValueFactory<>("Doctor_id"));
        colDoctor_name.setCellValueFactory(new PropertyValueFactory<>("Doctor_name"));
        colDoctor_contact.setCellValueFactory(new PropertyValueFactory<>("Doctor_contact"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        colWard_id.setCellValueFactory(new PropertyValueFactory<>("Ward_id"));

    }

    private void loadAllDoctor() throws RuntimeException {
        ObservableList<DoctorTm> obList = FXCollections.observableArrayList();

        try {
            List<Doctor> doctorList = DoctorRepo.getAllDoctor();
            for (Doctor doctor : doctorList) {
                DoctorTm doctorTm = new DoctorTm(
                       doctor.getDoctor_id(),
                        doctor.getDoctor_name(),
                        doctor.getDoctor_contact(),
                        doctor.getSpecialization(),
                        doctor.getWard_id()


                );


                obList.add(doctorTm);
            }
            tblDoctor.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtDoctorIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorid,txtdoctorid);
    }


    public void txtDoctorcontactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.contact,txtdoctorcontact);
    }

    public void txtDoctornameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorname,txtdoctorname);
    }


    public void txtspecilizationOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.spealization,txtspecialization);
    }

    public void txtwaridOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtwardid);
    }
}


