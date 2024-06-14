package lk.ijse.ayurvedic_hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicenDTO {
    private String Medicen_id;
    private String Medicen_name;
    private double unit_price;
    private int Qty;
}
