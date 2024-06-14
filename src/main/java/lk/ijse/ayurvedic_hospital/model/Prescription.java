package lk.ijse.ayurvedic_hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prescription {
    private String Prescription_id;
    private String Doctor_id;
    private Date Date;
}
