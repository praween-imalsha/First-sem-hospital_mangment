package lk.ijse.ayurvedic_hospital.repository;


import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepo {



    public static boolean save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO Appointments  VALUES (?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, appointment.getAppointment_id());
        pstm.setObject(2, appointment.getDoctor_id());
        pstm.setObject(3, appointment.getPatient_id());
        pstm.setObject(4,appointment.getDateAppoinment());
        pstm.setObject(5, appointment.getTime());


        return pstm.executeUpdate() > 0;
    }

    public static Appointment searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Appointments WHERE Appointment_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String appointmentid = resultSet.getString("Appointment_id");
            String doctorId  = resultSet.getString("Doctor_id");
            String patientid = resultSet.getString("Patient_id");
            String dateappointment = resultSet.getString("DateAppoinment");
            String time = resultSet.getString("Time");

            Appointment appointment = new Appointment(appointmentid,doctorId,patientid,dateappointment,time);

            return appointment;
        }

        return null;
    }


    public static boolean update(Appointment appointment) throws SQLException {
        String sql = "UPDATE Appointments SET doctor_id=?, patient_id=?,  DateAppoinment=?, Time=? WHERE appointment_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, appointment.getDoctor_id());
            pstm.setObject(2, appointment.getPatient_id());
            pstm.setObject(3,appointment.getDateAppoinment());
            pstm.setObject(4, appointment.getTime());
            pstm.setObject(5, appointment.getAppointment_id());

            return pstm.executeUpdate() > 0;
        }

    public static boolean delete(String appointmentId) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE appointment_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, appointmentId);

            return pstm.executeUpdate() > 0;
        }

    public static boolean clear() throws SQLException {
        String sql = "DELETE FROM Appointments";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

            return pstm.executeUpdate() > 0;
        }


    public static List<Appointment> getAllAppointment() throws SQLException {

        String sql = "SELECT * FROM Appointments";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Appointment> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            String appointmentid = resultSet.getString("Appointment_id");
            String doctorId  = resultSet.getString("Doctor_id");
            String patientid = resultSet.getString("Patient_id");
            String dateappointment = resultSet.getString("DateAppoinment");
            String time = resultSet.getString("Time");

          Appointment appointment = new Appointment(appointmentid,doctorId,patientid,dateappointment,time);
           appointmentList.add(appointment);
        }
        return appointmentList;
    }



    public static String getId() throws SQLException {
        String sql = "SELECT Appointment_id FROM Appointments ORDER BY Appointment_id DESC LIMIT 1;";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String appointmentId= resultSet.getString(1);
            return appointmentId;
        }
        return null;
    }



}








