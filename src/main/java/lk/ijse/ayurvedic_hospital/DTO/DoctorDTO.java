package lk.ijse.ayurvedic_hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorDTO {
    private String Doctor_id;
    private String Doctor_name;
    private String Doctor_contact;
    private String Specialization;
    private String Ward_id;
}
