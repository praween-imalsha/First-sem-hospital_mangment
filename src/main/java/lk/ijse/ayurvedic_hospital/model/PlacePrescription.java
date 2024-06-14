package lk.ijse.ayurvedic_hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlacePrescription {
    private Prescription prescription;
    private List<PrescriptionMedicen>prescriptionMedicenList;

}
