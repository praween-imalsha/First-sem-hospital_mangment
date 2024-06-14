package lk.ijse.ayurvedic_hospital.repository;

import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.PrescriptionMedicen;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PrescriptionMedicineRepo {
    public static boolean save(List<PrescriptionMedicen> prescriptionMedicenList) throws SQLException {
        for (PrescriptionMedicen prescriptionMedicen : prescriptionMedicenList) {
            boolean isSaved = save(prescriptionMedicen);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }



    private static boolean save(PrescriptionMedicen prescriptionMedicen) throws SQLException {
        String sql = "INSERT INTO Prescription_Medicen (Prescription_id, Medicen_id, Medicen_name, unit_price, Qty) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, prescriptionMedicen.getPrescription_id());
        pstm.setString(2, prescriptionMedicen.getMedicen_id());
        pstm.setString(3, prescriptionMedicen.getMedicen_name());
        pstm.setDouble(4, prescriptionMedicen.getUnit_price());
        pstm.setString(5, String.valueOf(prescriptionMedicen.getQty()));

        return pstm.executeUpdate() > 0;
    }
}
