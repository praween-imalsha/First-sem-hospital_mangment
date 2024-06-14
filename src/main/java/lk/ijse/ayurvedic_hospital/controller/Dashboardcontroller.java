package lk.ijse.ayurvedic_hospital.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ayurvedic_hospital.DB.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dashboardcontroller {
    @FXML
    private LineChart<?, ?> chart;
    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDoctorCount;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblappointmentcount;

    @FXML
    private Label lblpatientcount;

    @FXML
    private AnchorPane mainancorpane;
    private int employeeCount;
    private int doctorCount;
    private int appointmentCount;
    private  int patientCount;
    @FXML
    void btnAppoimentOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AppointmentFrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);

    }

    @FXML
    void btnDoctorOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DoctorFrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);

    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Loginpage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) mainancorpane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Form");
        stage.show();


    }

    @FXML
    void btnMedicenOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Medicenfrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);
    }

    @FXML
    void btnemployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EmployeeFrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);
    }

    @FXML
    void btnpatientOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PatientFrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);

    }

    @FXML
    void btnplaceAppointmentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/placeappoinment.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);

    }

    @FXML
    void btnwardOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Wardfrom.fxml"));
        Parent rootNode = loader.load();
        mainancorpane.getChildren().clear();
        mainancorpane.getChildren().add(rootNode);

    }

    public void initialize() {
        setTime();
        setDate();
        try {
            employeeCount= getEmploeeCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmplyeeCount(employeeCount);
        try {
             doctorCount= getDoctorCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setDoctorCount(doctorCount);
        try {
            patientCount= getPatientcount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setPatientCount(patientCount);
        try {
            appointmentCount= getappointmentcount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setappointmentCount(patientCount);
    }

    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private void setappointmentCount(int patientCount) {
        lblappointmentcount.setText(String.valueOf(appointmentCount));
    }

    private int getappointmentcount() throws SQLException {
        String sql = "SELECT COUNT(*) AS appointment_count FROM Appointments";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("appointment_count");
        }
        return 0;
    }

    private void setPatientCount(int patientCount) {
        lblpatientcount.setText(String.valueOf(patientCount));
    }

    private int getPatientcount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Patient_count FROM Patient";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Patient_count");
        }
        return 0;
    }

    private void setDoctorCount(int doctorCount) {
        lblDoctorCount.setText(String.valueOf(doctorCount));
    }

    private int getDoctorCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Doctor_count FROM Doctor";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Doctor_count");
        }
        return 0;
    }


    private void setEmplyeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    private int getEmploeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS Employee_count FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("Employee_count");
        }
        return 0;
    }

}



