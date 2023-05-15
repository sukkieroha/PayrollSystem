import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSV {

    public static void main(String[] args) {

        // Create list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("10001", "Jose", "Crisostomo", "49-1632020-8", "382189453145", "441093369646", "317-674-022-000", 373.04, 1500.00, 1000, 1000.00, 5, 10, 5));
        employees.add(new Employee("10002", "Christian", "Mata", "49-2959312-6", "824187961962", "631052853464", "103-100-522-000", 255.80, 1500.00, 800, 800, 5, 10, 5));
        employees.add(new Employee("10003", "Brad", "San Jose", "40-2400714-1	","239192926939",	"210850209964",	"672-474-690-000", 255.80, 1500.00, 800, 800, 5, 10, 5));
        employees.add(new Employee("10004","Anthony","Salcedo","26-9647608-3",	"126445315651",	"218002473454",	"210-805-911-000",362.05,1500.00,1000,1000,5,10,5));
        employees.add(new Employee("10005","Alice","Romualdez","55-4476527-2","545652640232","211385556888","888-572-294-000",133.93, 1500,500,500,5,10,5));
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

        // Read employee details from employees.csv and store username and password in authorizedAccounts.csv
        String employeeFile = "employees.csv";
        String authorizedAccountsFile = "authorizedAccounts.csv";

        // Password format: alpha-numeric, minimum 8 characters
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFile));
             FileWriter writer = new FileWriter(authorizedAccountsFile)) {

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                String employeeNumber = data[0];
                String firstName = data[1];
                String lastName = data[2];
                String username = firstName + "." + lastName;
                String password = "Welcome" + employeeNumber;

                if (password.matches(passwordPattern)) {
                    writer.write(String.format("%s,%s,%s,%s,%s%n", username, password, firstName, lastName, employeeNumber));
                } else {
                    System.out.println("Invalid password for username: " + username);
                }
            }
            System.out.println("Authorized accounts CSV file created successfully!");

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

        System.out.println("Leave Application CSV file created successfully!");
        }
    }
