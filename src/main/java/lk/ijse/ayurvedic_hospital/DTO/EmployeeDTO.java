package lk.ijse.ayurvedic_hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {
    private String Employee_id;
    private String Employee_name;
    private String Employee_address;
    private String Employee_contact;
    private String Ward_id;
}
