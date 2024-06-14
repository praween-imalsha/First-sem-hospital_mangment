package lk.ijse.ayurvedic_hospital.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.Util.Regex;
import lk.ijse.ayurvedic_hospital.model.Appointment;
import lk.ijse.ayurvedic_hospital.repository.AppointmentRepo;
import lk.ijse.ayurvedic_hospital.tm.AppointmentTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentFromcontroller {

    @FXML
    private JFXButton btnPrintBill;

    @FXML
    private TableColumn<?, ?> colAppointment_id;

    @FXML
    private TableColumn<?, ?> colDateAppointment;

    @FXML
    private TableColumn<?, ?> colDoctor_id;

    @FXML
    private TableColumn<?, ?> colPatient_id;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<AppointmentTm> tblAppointment;

    @FXML
    private TextField txtAppointmentId;

    @FXML
    private TextField txtDateAppoinment;

    @FXML
    private TextField txtDoctorId;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtTime;

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
    void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/Blank_A4_5.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }



    @FXML
    void btnclearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String appointmentId = txtAppointmentId.getText();


        try {
            boolean isDeleted = AppointmentRepo.delete(appointmentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "appoinment deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllAppointment();
        }

        }


    @FXML
    void btnsaveOnAction(ActionEvent event) {

        String appointmentId = txtAppointmentId.getText();
        String doctorId = txtDoctorId.getText();
        String patientId = txtPatientId.getText();
        String appointmentDate = txtDateAppoinment.getText();
        String time = txtTime.getText();


        // Check if any required field is empty
        if (isValied()) {
            if (appointmentId.isEmpty() || doctorId.isEmpty() || patientId.isEmpty() || appointmentDate.isEmpty() || time.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            Appointment appointment = new Appointment(appointmentId, doctorId, patientId, appointmentDate, time);

            // Save the client
            try {
                boolean isSaved = AppointmentRepo.save(appointment);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Dealer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllAppointment();
            }

        }
    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.appointmentid,txtAppointmentId)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorid,txtDoctorId)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.patientid,txtPatientId)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.date,txtDateAppoinment)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.time,txtTime)) return false;

        return true;
    }



    private void clearFields() {
     txtAppointmentId.setText("");
     txtDoctorId.setText("");
     txtPatientId.setText("");
     txtDateAppoinment.setText("");
     txtTime.setText("");
    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String appointmentId = txtAppointmentId.getText();
        String doctorId = txtDoctorId.getText();
        String patientId = txtPatientId.getText();
        String appointmentDate = txtDateAppoinment.getText();
        String time = txtTime.getText();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, appointmentDate, time);
        try {
            boolean isUpdated = AppointmentRepo.update(appointment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        } finally {
            loadAllAppointment();

        }


    }

    @FXML
    void txtAppointmentIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.appointmentid,txtAppointmentId);
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String appointmentId = txtAppointmentId.getText();
        Appointment appointment = AppointmentRepo.searchById(appointmentId);
        if (appointment != null) {
            txtAppointmentId.setText(appointment.getAppointment_id());
            txtDoctorId.setText(appointment.getDoctor_id());
            txtPatientId.setText(appointment.getPatient_id());
            txtDateAppoinment.setText(appointment.getDateAppoinment());
            txtTime.setText(appointment.getTime());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Appointment not found!").show();
        }


    }
    private void setCellValueFactory () {
        colAppointment_id.setCellValueFactory(new PropertyValueFactory<>("Appointment_id"));
        colDoctor_id.setCellValueFactory(new PropertyValueFactory<>("Doctor_id"));
        colPatient_id.setCellValueFactory(new PropertyValueFactory<>("Patient_id"));
        colDateAppointment.setCellValueFactory(new PropertyValueFactory<>("DateAppoinment"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));

    }

    private void loadAllAppointment () {
        ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

        try {
            List<Appointment> appointmentList = AppointmentRepo.getAllAppointment();
            for (Appointment appointment : appointmentList) {
                AppointmentTm appointmentTm = new AppointmentTm(
                        appointment.getAppointment_id(),
                        appointment.getDoctor_id(),
                        appointment.getPatient_id(),
                        appointment.getDateAppoinment(),
                        appointment.getTime()

                );


                obList.add(appointmentTm);
            }
            tblAppointment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize () {
        setCellValueFactory();
        loadAllAppointment();


    }

    public void txtDoctorIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.doctorid,txtDoctorId);

    }


    public void txtpatientidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.patientid,txtPatientId);

    }



}
