import javax.swing.*;
public class PayrollCalculator {
    public static void main(String[] args) {

        // Create 3 employees with their hourly rate and allowances pre-set
        Employee emp1 = new Employee("10001", "Jose", "Crisostomo", "222222222","11111111","333333","22220000",200, 1500, 1200, 130,5,10, 5);
        Employee emp2 = new Employee("10002", "Christian", "Mata", "111111111","2222222","4444444","33330000",30.0, 600.0, 150.0, 250.0,5,10, 5);
        Employee emp3 = new Employee("10003", "Brad", "San Jose", "333333333","3333333","5555555","4444000",20.0, 400.0, 75.0, 150.0,5,10, 5);

        // Get input from the user for each employee
        String employeeNumber = JOptionPane.showInputDialog(null, "Enter employee number:");
        String firstName = JOptionPane.showInputDialog(null, "Enter first name:");
        String lastName = JOptionPane.showInputDialog(null, "Enter last name:");
        double hoursWorked = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter hours worked:"));

        // Determine which employee matches the input employee number
        Employee employee = null;
        if (employeeNumber.equals(emp1.getEmployeeNumber())) {
            employee = emp1;
        } else if (employeeNumber.equals(emp2.getEmployeeNumber())) {
            employee = emp2;
        } else if (employeeNumber.equals(emp3.getEmployeeNumber())) {
            employee = emp3;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid employee number!");
            System.exit(0);
        }

        // Set the name and compute the gross pay
        employee.setName(firstName, lastName);
        double grossPay = employee.computeGrossPay(hoursWorked);

        // Compute the allowances, deductions and net pay
        double riceAllowance = Employee.getRiceAllowance();
        double phoneAllowance = Employee.getPhoneAllowance();
        double clothingAllowance = Employee.getClothingAllowance();
        double sssDeduction = employee.computeSSSDeduction(grossPay);
        double philhealthDeduction = employee.computePhilhealthDeduction(grossPay);
        double pagibigDeduction = employee.computePagibigDeduction(grossPay);
        double taxDeduction = employee.computeTaxDeduction(grossPay);
        double netPay = grossPay - sssDeduction - philhealthDeduction - pagibigDeduction - taxDeduction;

        // Display the payroll details
        System.out.println("Employee Details:" );
        System.out.println("Employee Number: " + employee.getEmployeeNumber());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Gross Pay: " + employee.computeGrossPay(hoursWorked));
        System.out.println("Rice Allowance: " + employee.getRiceAllowance());
        System.out.println("Phone Allowance: " + employee.getPhoneAllowance());
        System.out.println("Clothing Allowance: "+ employee.getClothingAllowance());
        System.out.println("SSS Deduction: " + employee.computeSSSDeduction(grossPay));
        System.out.println("Philhealth Deduction: " + employee.computePhilhealthDeduction(grossPay));
        System.out.println("Pagibig Deduction: " + employee.computePagibigDeduction(grossPay));
        System.out.println("Tax Deduction: " + employee.computeTaxDeduction(grossPay));
        System.out.println("Net pay:" + employee.computeNetPay(grossPay));
    }
}

