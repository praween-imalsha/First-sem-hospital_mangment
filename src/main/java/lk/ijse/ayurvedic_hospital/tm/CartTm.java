package lk.ijse.ayurvedic_hospital.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String Medicen_id ;
    private String Medicen_name  ;
    private  int Qty;
    private double unit_price;
    private double Total;
    private JFXButton btnRemove;
}