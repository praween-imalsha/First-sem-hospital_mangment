package lk.ijse.ayurvedic_hospital.repository;




import lk.ijse.ayurvedic_hospital.DB.DbConnection;
import lk.ijse.ayurvedic_hospital.model.Doctor;
import lk.ijse.ayurvedic_hospital.model.Employee;
import lk.ijse.ayurvedic_hospital.tm.EmployeeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

        public static boolean save(EmployeeTm employee) throws SQLException {
            String sql = "INSERT INTO Employee (Employee_id, Employee_name, Employee_address, Employee_contact, Ward_id) VALUES (?, ?, ?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);


            pstm.setObject(1, employee.getEmployee_id());
            pstm.setObject(2, employee.getEmployee_name());
            pstm.setObject(3, employee.getEmployee_address());
            pstm.setObject(4, employee.getEmployee_contact());
            pstm.setObject(5, employee.getWard_id());

            return pstm.executeUpdate() > 0;


        }
    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE Employee_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String employeeid = resultSet.getString("Employee_id");
            String employeename = resultSet.getString("Employee_name");
            String  employeeaddress= resultSet.getString("Employee_address");
            String  employeecontact= resultSet.getString("Employee_contact");
            String  wardid= resultSet.getString("Ward_id");

            Employee employee = new Employee(employeeid,employeename,employeeaddress,employeecontact,wardid);
            return employee;
        }

        return null;
    }

        public static boolean update(EmployeeTm employee) throws SQLException {
            String sql = "UPDATE Employee SET Employee_name=?, Employee_address=?, Employee_contact=?, Ward_id=? WHERE Employee_id=?";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, employee.getEmployee_id());
            pstm.setObject(2, employee.getEmployee_address());
            pstm.setObject(3, employee.getEmployee_contact());
            pstm.setObject(4, employee.getWard_id());
            pstm.setObject(5, employee.getEmployee_id());

            return pstm.executeUpdate() > 0;
        }

        public static boolean delete(String employeeId) throws SQLException {
            String sql = "DELETE FROM Employee WHERE Employee_id=?";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, employeeId);

            return pstm.executeUpdate() > 0;


        }
    public static List<Employee> getAllEmployee() throws SQLException {

        String sql = "SELECT * FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employeeid = resultSet.getString("Employee_id");
            String employeename = resultSet.getString("Employee_name");
            String  employeeaddress= resultSet.getString("Employee_address");
            String  employeecontact= resultSet.getString("Employee_contact");
            String  wardid= resultSet.getString("Ward_id");




            Employee employee = new Employee(employeeid,employeename,employeeaddress,employeecontact,wardid);
            employeeList.add(employee);
        }
        return employeeList;
    }
}


