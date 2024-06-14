package lk.ijse.ayurvedic_hospital.repository;

import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Prescription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrescriptionRepo {
    public static String getId() throws SQLException {
        String sql = "SELECT  Prescription_id FROM Prescription   ORDER BY Prescription_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean save( Prescription prescription) throws SQLException {
        String sql = "INSERT INTO  Prescription VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, prescription.getPrescription_id());
        pstm.setString(2, prescription.getDoctor_id());
        pstm.setDate(3, prescription.getDate());

        return pstm.executeUpdate() > 0;
    }
    public static String genarateOrderId() throws SQLException {

        String sql = "SELECT Prescription_id FROM Prescription ORDER BY Prescription_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("PR0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "PR00"+id;
            }else {
                if (length < 3){
                    return "PR0"+id;
                }else {
                    return "PR"+id;
                }
            }
        }
        return "PR001";
    }
}
