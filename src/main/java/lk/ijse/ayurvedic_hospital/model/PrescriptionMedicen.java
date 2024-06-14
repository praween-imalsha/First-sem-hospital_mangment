package lk.ijse.ayurvedic_hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrescriptionMedicen {
    private String Prescription_id;
    private String Medicen_id ;
    private String Medicen_name  ;
    private double unit_price;
    private  int Qty;
}
