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
import lk.ijse.ayurvedic_hospital.model.Employee;
import lk.ijse.ayurvedic_hospital.repository.EmployeeRepo;
import lk.ijse.ayurvedic_hospital.tm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormcontroller {

    @FXML
    private TableColumn<?, ?> colEmployee_address;

    @FXML
    private TableColumn<?, ?> colEmployee_contact;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private TableColumn<?, ?> colEmployee_name;

    @FXML
    private TableColumn<?, ?> colWard_id;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtemployeeaddress;

    @FXML
    private TextField txtemployeecontact;

    @FXML
    private TextField txtemployeeid;

    @FXML
    private TextField txtemployeename;

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
    void btnClearOnAction(ActionEvent event) {
        clearFields();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String employeeId = txtemployeeid.getText();
        try {
            boolean isDeleted = EmployeeRepo.delete(employeeId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllEmployee();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Employee_id = txtemployeeid.getText();
        String Employee_name = txtemployeename.getText();
        String Employee_address = txtemployeeaddress.getText();
        String Employee_contact = txtemployeecontact.getText();
        String Ward_id = txtwardid.getText();

        if (isValied()) {
            if (Employee_id.isEmpty() || Employee_name.isEmpty() || Employee_address.isEmpty() || Employee_contact.isEmpty() || Ward_id.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }

            // Create a new Client object
            EmployeeTm employee = new EmployeeTm(Employee_id,Employee_name,Employee_address,Employee_contact,Ward_id);
            // Save the client
            try {
                boolean isSaved = EmployeeRepo.save(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Dealer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }finally {
                loadAllEmployee();
            }

        }


    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.employeeid,txtemployeeid)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.employeename,txtemployeename)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.address,txtemployeeaddress)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.contact,txtemployeecontact)) return false;
        if (!Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtwardid)) return false;

        return true;
    }



    private void clearFields() {
       txtemployeeid.clear();
       txtemployeename.clear();
       txtemployeeaddress.clear();
        txtemployeecontact.clear();
        txtwardid.clear();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Employee_id = txtemployeeid.getText();
        String Employee_name = txtemployeename.getText();
        String Employee_address = txtemployeeaddress.getText();
        String Employee_contact = txtemployeecontact.getText();
        String Ward_id = txtwardid.getText();

        EmployeeTm employee = new EmployeeTm(Employee_id,Employee_name,Employee_address,Employee_contact,Ward_id);
        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllEmployee();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String Employee_id = txtemployeeid.getText();
        Employee employee = EmployeeRepo.searchById(Employee_id);
        if (employee != null) {
           txtemployeeid.setText(employee.getEmployee_id());
            txtemployeename.setText(employee.getEmployee_name());
            txtemployeeaddress.setText(employee.getEmployee_address());
            txtemployeecontact.setText(employee.getEmployee_contact());
            txtwardid.setText(employee.getWard_id());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
        }


    }
    public void initialize(){
        setCellValueFactory();
        loadAllEmployee();
    }

    private void loadAllEmployee() {

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAllEmployee();
            for (Employee employee : employeeList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getEmployee_id(),
                        employee.getEmployee_name(),
                        employee.getEmployee_address(),
                        employee.getEmployee_contact(),
                        employee.getWard_id()

                );


                obList.add(employeeTm);
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colEmployee_name.setCellValueFactory(new PropertyValueFactory<>("Employee_name"));
        colEmployee_address.setCellValueFactory(new PropertyValueFactory<>("Employee_address"));
        colEmployee_contact.setCellValueFactory(new PropertyValueFactory<>("Employee_contact"));
        colWard_id.setCellValueFactory(new PropertyValueFactory<>("Ward_id"));

    }

    public void txtemployeeidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.employeeid,txtemployeeid);
    }

    public void txtwardidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.wardid,txtwardid);
    }

    public void txtemployeecontactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.contact,txtemployeecontact);
    }

    public void txtEmployeeaddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.address,txtemployeeaddress);

    }

    public void txtemployeenameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.ayurvedic_hospital.Util.TextField.employeename,txtemployeename);


    }
}
