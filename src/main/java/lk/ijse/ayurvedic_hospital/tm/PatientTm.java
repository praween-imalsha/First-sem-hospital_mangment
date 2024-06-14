package lk.ijse.ayurvedic_hospital.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PatientTm {
    private String Patient_id ;
    private String Patient_name ;
    private String Patient_address ;
    private String  Age  ;
    private String Gender  ;
    private String War_id   ;
    private String  Employee_id ;
}
