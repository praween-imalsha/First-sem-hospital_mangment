package lk.ijse.ayurvedic_hospital.repository;


import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepo {
    public static boolean save(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO Doctor  VALUES (?, ?, ?, ?, ?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, doctor.getDoctor_id());
        pstm.setObject(2, doctor.getDoctor_name());
        pstm.setObject(3, doctor.getDoctor_contact());
        pstm.setObject(4, doctor.getSpecialization());
        pstm.setObject(5, doctor.getWard_id());

        return pstm.executeUpdate() > 0;


    }
    public static Doctor searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE Doctor_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String doctorId = resultSet.getString("Doctor_id");
            String doctorName = resultSet.getString("Doctor_name");
            String doctorContact = resultSet.getString("Doctor_contact");
            String specialization = resultSet.getString("Specialization");
            String wardid = resultSet.getString("Ward_id");


            Doctor doctor = new Doctor(doctorId,doctorName,doctorContact,specialization,wardid);
            return doctor;
        }

        return null;
    }



    public static boolean update(Doctor doctor) throws SQLException {
        String sql = "UPDATE Doctor SET Doctor_name=?, Doctor_contact=?, Specialization=?, Ward_id=? WHERE Doctor_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, doctor.getDoctor_name());
        pstm.setObject(2, doctor.getDoctor_contact());
        pstm.setObject(3, doctor.getSpecialization());
        pstm.setObject(4, doctor.getWard_id());
        pstm.setObject(5, doctor.getDoctor_id());

        return pstm.executeUpdate() > 0;




    }

    public static boolean delete(String doctorId) throws SQLException {
        String sql = "DELETE FROM Doctor WHERE Doctor_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, doctorId);

        return pstm.executeUpdate() > 0;
    }
    public static List<Doctor> getAllDoctor() throws SQLException {

        String sql = "SELECT * FROM Doctor";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Doctor> doctorList = new ArrayList<>();
        while (resultSet.next()) {
            String doctorId = resultSet.getString("Doctor_id");
            String doctorName = resultSet.getString("Doctor_name");
            String doctorContact = resultSet.getString("Doctor_contact");
            String specialization = resultSet.getString("Specialization");
            String wardid = resultSet.getString("Ward_id");


                Doctor doctor = new Doctor(doctorId,doctorName,doctorContact,specialization,wardid);
           doctorList.add(doctor);
        }
        return doctorList;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Doctor_id FROM Doctor";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
