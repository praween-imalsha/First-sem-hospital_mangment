package lk.ijse.ayurvedic_hospital.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.ayurvedic_hospital.DB.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrationfromcontroller {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtUserId;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String password = txtPw.getText();

        try {
            boolean isSaved = saveUser(userId, name, password);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String User_id , String User_name, String password) throws SQLException {
        String sql = "INSERT INTO User VALUES(?, ?, ?)";

       /* DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();*/

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, User_id);
        pstm.setObject(2, User_name);
        pstm.setObject(3, password);

        return pstm.executeUpdate() > 0;
    }
}





