package lk.ijse.ayurvedic_hospital.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class MedicenTm {
    private String Medicen_id ;
    private String Medicen_name  ;
    private double unit_price;
    private  int Qty;

}
