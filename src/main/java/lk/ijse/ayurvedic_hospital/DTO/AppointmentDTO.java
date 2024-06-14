package lk.ijse.ayurvedic_hospital.DTO;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AppointmentDTO {

        private String Appointment_id;
        private String Doctor_id ;
        private String Patient_id;
        private String DateAppoinment;
        private String Time;

    }
