import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSV {

    public static void main(String[] args) {

        // Create list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("10001", "Jose", "Crisostomo",  "123456789", "987654321", "567890123", "111222333",200, 1500.00, 2000.00, 1000.00,5,10,5));
        employees.add(new Employee("10002", "Christian", "Mata", "234567890", "876543210", "678901234", "444555666",150, 1500.00, 2000.00, 1000.00,5,10,5));
        employees.add(new Employee("10003", "Brad", "San Jose",  "345678901", "765432109", "789012345", "777888999",100, 1500.00, 2000.00, 1000.005,5,10,5));

        // Write employee details to CSV file
        String employeeFilename = "employees.csv";
        String leaveFilename = "leaveApplication.csv";
        try (FileWriter employeeWriter = new FileWriter(employeeFilename);
             FileWriter leaveWriter = new FileWriter(leaveFilename)) {

            // Write employee details
            for (Employee employee : employees) {
                employeeWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f\n",
                        employee.getEmployeeNumber(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSssNumber(),
                        employee.getPhilhealthNumber(),
                        employee.getPagibigNumber(),
                        employee.getTin(),
                        employee.getHourlyRate(),
                        employee.getRiceAllowance(),
                        employee.getPhoneAllowance(),
                        employee.getClothingAllowance()
                ));
            }
            System.out.println("Employee CSV files created successfully!");

            // Write leave application details
            for (Employee employee : employees) {
                leaveWriter.write(String.format("employee_number,start_date,end_date,leave_type\n",
                        employee.getEmployeeNumber()
                        ));
            }


            System.out.println("Leave CSV files created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
