package lk.ijse.ayurvedic_hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WardDTO {
    private String war_id;
    private String war_name;
    private String War_capacity;
    private String User_id;
}
