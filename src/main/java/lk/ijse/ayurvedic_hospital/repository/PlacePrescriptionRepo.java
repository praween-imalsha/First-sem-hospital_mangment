package lk.ijse.ayurvedic_hospital.repository;

import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.PlacePrescription;

import java.sql.Connection;
import java.sql.SQLException;

public class PlacePrescriptionRepo {
    public static boolean placePrescription(PlacePrescription placePrescription) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPrescriptionSaved = PrescriptionRepo.save(placePrescription.getPrescription());
            System.out.println("1"+isPrescriptionSaved);
            if (isPrescriptionSaved) {
                boolean isQtyUpdated = MedicenRepo.update(placePrescription.getPrescriptionMedicenList());
                System.out.println("2"+isQtyUpdated);
                if (isQtyUpdated) {
                    boolean isPrescriptionmedicenSaved = PrescriptionMedicineRepo.save(placePrescription.getPrescriptionMedicenList());
                    System.out.println("3"+isPrescriptionmedicenSaved);
                    if (isPrescriptionmedicenSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
//            connection.rollback();
//            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}