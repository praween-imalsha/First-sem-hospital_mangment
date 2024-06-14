package lk.ijse.ayurvedic_hospital.model;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Appointment {
    private  String  Appointment_id ;
     private  String Doctor_id ;
    private  String  Patient_id;
    private  String  DateAppoinment;
    private  String Time;


    }



