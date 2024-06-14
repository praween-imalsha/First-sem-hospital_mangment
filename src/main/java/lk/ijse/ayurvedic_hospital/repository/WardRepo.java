package lk.ijse.ayurvedic_hospital.repository;

import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Ward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardRepo {
    public static boolean save(Ward ward) throws SQLException {
        String sql = "INSERT INTO Ward (War_id, War_name, War_capacity, User_id) VALUES (?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, ward.getWar_id());
            pstm.setObject(2, ward.getWar_name());
            pstm.setObject(3, ward.getWar_capacity());
            pstm.setObject(4, ward.getUser_id());

        return pstm.executeUpdate() > 0;

    }
    public static Ward searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Ward WHERE War_id= ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String wardid = resultSet.getString("War_id");
            String wardname = resultSet.getString("War_name");
            String wardcapacity= resultSet.getString("War_capacity");
            String userid= resultSet.getString("User_id");




            Ward ward = new Ward(wardid,wardname,wardcapacity,userid);

            return ward;
        }

        return null;
    }

    public static boolean update(Ward ward) throws SQLException {
        String sql = "UPDATE Ward SET War_name=?, War_capacity=? WHERE War_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

          pstm.setObject(1, ward.getWar_name());
            pstm.setObject(2, ward.getWar_capacity());
            pstm.setObject(3, ward.getWar_id());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String wardId) throws SQLException {
        String sql = "DELETE FROM Ward WHERE War_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, wardId);

        return pstm.executeUpdate() > 0;

    }

    public static List<Ward> getAllWards() throws SQLException {

        String sql = "SELECT * FROM Ward";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Ward> wardList = new ArrayList<>();
        while (resultSet.next()) {
            String wardid = resultSet.getString("War_id");
            String wardname = resultSet.getString("War_name");
            String wardcapacity= resultSet.getString("War_capacity");
            String userid= resultSet.getString("User_id");




            Ward ward = new Ward(wardid,wardname,wardcapacity,userid);
            wardList.add(ward);
        }
        return wardList;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT War_id FROM Ward";
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
    }

