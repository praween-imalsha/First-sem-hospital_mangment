package lk.ijse.ayurvedic_hospital.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ayurvedic_hospital.model.*;
import lk.ijse.ayurvedic_hospital.repository.DoctorRepo;
import lk.ijse.ayurvedic_hospital.repository.MedicenRepo;
import lk.ijse.ayurvedic_hospital.repository.PlacePrescriptionRepo;
import lk.ijse.ayurvedic_hospital.repository.PrescriptionRepo;
import lk.ijse.ayurvedic_hospital.tm.CartTm;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class placeprescriptioncontroller {



    @FXML
    private JFXComboBox<String> cmbDoctorId;

    @FXML
    private JFXComboBox<String> cmbMedicenId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colMedicen_id;

    @FXML
    private TableColumn<?, ?> colMedicen_name;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colunit_price;

    @FXML
    private Label lblPrescriotionId;


    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblDoctorname;

    @FXML
    private Label lblMedicenname;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblPrescriptionDate;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private Label lblQty;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CartTm> tblMedicen;

    @FXML
    private TextField txtQty;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id =   cmbMedicenId.getValue();
        String name= lblMedicenname.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblMedicen.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblMedicen.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblMedicen.getItems().size(); i++) {
            if(id.equals(colMedicen_id.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = unitPrice *qty;
                tm.setQty(qty);
                tm.setTotal(total);

                tblMedicen.refresh();

                calculateNetTotal();
                return;
            }
        }
        int up = (int) unitPrice;
        CartTm tm = new CartTm(id,name, qty, up, total, btnRemove);
        obList.add(tm);

        tblMedicen.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblMedicen.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

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
    void btnNewDoctorIdOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceAppoinmentOnAction(ActionEvent event) {
        String prescriptionid = lblPrescriotionId.getText();
        String  DoctorId = cmbDoctorId.getValue();
        Date date = Date.valueOf(LocalDate.now());

        var prescription = new Prescription(prescriptionid,DoctorId,date);

        List<PrescriptionMedicen> odList = new ArrayList<>();

        for (int i = 0; i < tblMedicen.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            String tmQty = String.valueOf(tm.getQty());

            PrescriptionMedicen prescriptionMedicen = new PrescriptionMedicen(
                   prescriptionid,
                    tm.getMedicen_id(),
                    tm.getMedicen_name(),
                    tm.getUnit_price(),
                    tm.getQty()
            );



            odList.add(prescriptionMedicen);
        }

        PlacePrescription placePrescription = new PlacePrescription(prescription, odList);
        try {
            boolean isPlaced = PlacePrescriptionRepo.placePrescription(placePrescription); // Assuming the method name is 'place'
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (Exception e) {
            // Handle any exceptions here
            e.printStackTrace(); // or log the exception
        }

    }

    @FXML
    void cmbDoctorOnAction(ActionEvent event) {
        String id =  cmbDoctorId.getValue();
        try {
            Doctor doctor = DoctorRepo.searchById(id);

            lblDoctorname.setText(doctor.getDoctor_name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbMedicenOnAction(ActionEvent event) {
        String id =  cmbMedicenId.getValue();

        try {
            Medicen medicen = MedicenRepo.searchById(id);
            if(medicen != null) {
                lblMedicenname.setText(medicen.getMedicen_name());
                lblUnitPrice.setText(String.valueOf(medicen.getUnit_price()));
                 lblQty.setText(String.valueOf(medicen.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
      btnAddOnAction(event);
    }
    private final ObservableList<CartTm>obList= FXCollections.observableArrayList();
    public void initialize() {
       setTime();
        setDate();
        getprescriptionid();
        getDoctorid();
        getMedicenid();
        setCellValueFactory();
    }

    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void getDoctorid() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = DoctorRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbDoctorId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void getprescriptionid() {

        try {
            String currentId = PrescriptionRepo.genarateOrderId();
            lblPrescriotionId.setText(currentId);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPrescriptionId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("PR");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "PR" + ++idNum;
        }
        return "PR01";

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
       lblPrescriptionDate.setText(String.valueOf(now));

    }

    private void setCellValueFactory() {
        colMedicen_id.setCellValueFactory(new PropertyValueFactory<>("Medicen_id"));
        colMedicen_name.setCellValueFactory(new PropertyValueFactory<>("Medicen_name"));
        colunit_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getMedicenid() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = MedicenRepo.getIds();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbMedicenId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

}
}
