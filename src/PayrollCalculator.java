import javax.swing.*;
public class PayrollCalculator {
    public static void main(String[] args) {

        // Create 3 employees with their hourly rate and allowances pre-set
        Employee emp1 = new Employee("10001", "Jose", "Crisostomo", 200, 1500, 1200, 1300);
        Employee emp2 = new Employee("10001", "Christian", "Mata", 30.0, 600.0, 150.0, 250.0);
        Employee emp3 = new Employee("10003", "Brad", "San Jose", 20.0, 400.0, 75.0, 150.0);

        // Get input from the user for each employee
        String empNum = JOptionPane.showInputDialog(null, "Enter employee number:");
        String firstName = JOptionPane.showInputDialog(null, "Enter first name:");
        String lastName = JOptionPane.showInputDialog(null, "Enter last name:");
        double hoursWorked = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter hours worked:"));

        // Determine which employee matches the input employee number
        Employee employee = null;
        if (empNum.equals(emp1.getEmpNum())) {
            employee = emp1;
        } else if (empNum.equals(emp2.getEmpNum())) {
            employee = emp2;
        } else if (empNum.equals(emp3.getEmpNum())) {
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
        System.out.println("Employee Number: " + employee.getEmpNum());
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
