import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSV {

    public static void main(String[] args) {

        // Create list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("10001", "Jose", "Crisostomo", "123456789", "987654321", "567890123", "111222333", 200, 1500.00, 2000.00, 1000.00, 5, 10, 5));
        employees.add(new Employee("10002", "Christian", "Mata", "234567890", "876543210", "678901234", "444555666", 150, 1500.00, 2000.00, 1000.00, 5, 10, 5));
        employees.add(new Employee("003", "Brad", "San Jose", "345678901", "765432109", "789012345", "777888999", 100, 1500.00, 2000.00, 1000.00, 5, 10, 5));

        // Write employee details to CSV file
        String employeeFilename = "employees.csv";
        try (FileWriter writer = new FileWriter(employeeFilename)) {

            // Write employee details
            for (Employee employee : employees) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%d,%d,%d\n",
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
                        employee.getSickLeave(),
                        employee.getVacationLeave(),
                        employee.getEmergencyLeave()
                ));
            }
            System.out.println("Employee CSV file created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Handle leave applications
        String leaveFilename = "leaveApplication.csv";

        // Create file if it doesn't exist
        File file = new File(leaveFilename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}