package lk.ijse.ayurvedic_hospital.repository;


import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Medicen;
import lk.ijse.ayurvedic_hospital.model.PrescriptionMedicen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicenRepo {

    public static boolean save(Medicen medicen) throws SQLException {
        String sql = "INSERT INTO Medicen VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, medicen.getMedicen_id ());
        pstm.setObject(2, medicen.getMedicen_name());
        pstm.setObject(3,medicen.getUnit_price());
        pstm.setObject(4, medicen.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static Medicen searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Medicen WHERE Medicen_id= ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String medicen_id  = resultSet.getString("Medicen_id");
            String medicen_name = resultSet.getString("Medicen_name");
            double unitprice = resultSet.getDouble("unit_price");
            int Qty = Integer.parseInt(resultSet.getString("Qty"));



            Medicen medicen = new Medicen(medicen_id,medicen_name,unitprice,Qty);

            return medicen;
        }

        return null;
    }


    public static boolean update(Medicen medicen) throws SQLException {
        String sql = "UPDATE Medicen SET Medicen_name=?, unit_price =?,Qty=?  WHERE Medicen_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, medicen.getMedicen_name());
        pstm.setObject(2,medicen.getUnit_price());
        pstm.setObject(3, medicen.getQty());
        pstm.setObject(4, medicen.getMedicen_id());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String medicenId) throws SQLException {
        String sql = "DELETE FROM  Medicen WHERE Medicen_id =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, medicenId);

        return pstm.executeUpdate() > 0;

    }

    public static List<Medicen>getAllMedicen() throws SQLException {

        String sql = "SELECT * FROM Medicen";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Medicen>  medicenList = new ArrayList<>();
        while (resultSet.next()) {
            String medicenId  = resultSet.getString("Medicen_id");
            String medicenName = resultSet.getString("Medicen_name");
            double unitprice  = resultSet.getDouble("unit_price");
           int Qty = Integer.parseInt(resultSet.getString("Qty"));



            Medicen medicen = new Medicen(medicenId,medicenName,unitprice,Qty);
            medicenList.add(medicen);
        }
        return medicenList;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Medicen_id FROM Medicen";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
           idList.add(resultSet.getString(1));
        }
        return idList;
    }
    private static boolean updateQty(String MedicenId, int qty) throws SQLException {
        System.out.println(MedicenId);
        System.out.println(qty);
        String sql = "UPDATE Medicen SET Qty = Qty-? WHERE Medicen_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, MedicenId);

        return pstm.executeUpdate() > 0;
    }
    public static boolean update(List<PrescriptionMedicen> prescriptionMedicenList) throws SQLException {
        boolean isUpdateQty = false;
        for (PrescriptionMedicen prescriptionMedicen : prescriptionMedicenList) {
             isUpdateQty = updateQty(prescriptionMedicen.getMedicen_id(), prescriptionMedicen.getQty());

        }
        return isUpdateQty;
    }
}