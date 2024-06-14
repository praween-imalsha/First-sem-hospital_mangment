package lk.ijse.ayurvedic_hospital.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case appointmentid:
                filed  = "^([A-Z][0-9]{3})$";
                break;
            case patientid:
                filed = "([A-Z][0-9]{3})$";
                break;
            case address:
                filed ="^[A-z|\\\\\\\\s]{3,}$";
                break;
            case doctorid:
                filed = "([A-Z][0-9]{3})$";
                break;
            case contact:
                filed ="^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";;
                break;
            case employeeid:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case qty:
                filed = "^([0-9]){1,}$";
                break;
            case doctorname:
                filed = "^[a-zA-Z ]+$";
                break;
            case wardid:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case employeename:
                filed = "^[A-z|\\\\s]{3,}$";
                break;
            case medicenid:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case unit_price:
                filed = "^\\\\d+$";
                break;
            case name:
                filed = "^[a-zA-Z ]+$";
                break;
            case time:
                filed ="^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
                break;
            case User_Id:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case User_Name:
                filed = "^[a-zA-Z ]+$";
                break;
            case Password:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";

        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: GREEN");
            return true;
        }else {
            textField.setStyle("-fx-border-color: RED");
            return false;
        }
    }
}
