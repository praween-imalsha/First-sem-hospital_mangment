package lk.ijse.ayurvedic_hospital.repository;



import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Medicen;
import lk.ijse.ayurvedic_hospital.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepo {

    public static boolean save(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient  VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, patient.getPatient_id());
        pstm.setObject(2, patient.getPatient_name());
        pstm.setObject(3, patient.getPatient_address());
        pstm.setObject(4, patient.getAge());
        pstm.setObject(5, patient.getGender());
        pstm.setObject(6, patient.getWar_id());
        pstm.setObject(7, patient.getEmployee_id());
        return pstm.executeUpdate() > 0;
    }
    public static   Patient searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE Patient_id= ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {

            String patientid = resultSet.getString("Patient_id");
            String patientname = resultSet.getString("Patient_name");
            String patientaddress = resultSet.getString("Patient_address");
            String age = resultSet.getString("Age");
            String gender = resultSet.getString("Gender");
            String warid = resultSet.getString("War_id");
            String employeeid = resultSet.getString("Employee_id");



            Patient patient = new Patient(patientid,patientname,patientaddress,age,gender,warid,employeeid);
            return patient;
        }

        return null;
    }


    public static boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE Patient SET Patient_name=?, Patient_address=?, Age=?, Gender=?, War_id=?, Employee_id=? WHERE Patient_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, patient.getPatient_name());
        pstm.setObject(2, patient.getPatient_address());
        pstm.setObject (3, patient.getAge());
        pstm.setObject(4, patient.getGender());
        pstm.setObject(5, patient.getWar_id());
        pstm.setObject(6, patient.getEmployee_id());
        pstm.setObject(7, patient.getPatient_id());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String patientId) throws SQLException {
        String sql = "DELETE FROM Patient WHERE Patient_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);



            pstm.setObject(1, patientId);
        return pstm.executeUpdate() > 0;

    }

    public static List<Patient> getAllPatient() throws SQLException {

        String sql = "SELECT * FROM Patient";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Patient>patientList  = new ArrayList<>();
        while (resultSet.next()) {
            String patientid = resultSet.getString("Patient_id");
            String patientname = resultSet.getString("Patient_name");
            String patientaddress = resultSet.getString("Patient_address");
            String age = resultSet.getString("Age");
            String gender = resultSet.getString("Gender");
            String warid = resultSet.getString("War_id");
            String employeeid = resultSet.getString("Employee_id");



           Patient patient = new Patient(patientid,patientname,patientaddress,age,gender,warid,employeeid);
            patientList.add(patient);
        }
        return patientList;
    }
}
