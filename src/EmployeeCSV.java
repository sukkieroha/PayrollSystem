import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSV {

    public static void main(String[] args) {

        // Create list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("10001", "Jose", "Crisostomo", "631052853464", "631052853464", "631052853464", "631052853464", 373.04, 1500.00, 1000, 1000.00, 5, 10, 5));
        employees.add(new Employee("10002", "Christian", "Mata", "49-2959312-6", "824187961962", "631052853464", "444555666", 255.80, 1500.00, 800, 800, 5, 10, 5));
        employees.add(new Employee("10003", "Brad", "San Jose", "40-2400714-1	","239192926939",	"672-474-690-000",	"210850209964", 255.80, 1500.00, 800, 800, 5, 10, 5));
        employees.add(new Employee("10004","Anthony","Salcedo","26-9647608-3",	"126445315651",	"210-805-911-000",	"218002473454",362.05,1500.00,1000,1000,5,10,5));
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