package lk.ijse.ayurvedic_hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDTO {
    private String Patient_name ;
    private String Patient_address ;
    private String  Age  ;
    private String Gender  ;
    private String War_id   ;
    private String  Employee_id ;
}
