import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PayrollCalculatorGUI extends JFrame implements ActionListener {
    // GUI components
    private JLabel employeeNumLabel, firstNameLabel, lastNameLabel, hoursWorkedLabel;
    private JTextField employeeNumTextField, firstNameTextField, lastNameTextField, hoursWorkedTextField;
    private JButton calculateButton;
    private JTextArea outputTextArea;

    // Employees
    private Employee emp1 = new Employee("10001", "Jose", "Crisostomo", 200, 1500, 1200, 1300);
    private Employee emp2 = new Employee("10002", "Christian", "Mata", 300.0, 1600.0, 1500.0, 1500.0);
    private Employee emp3 = new Employee("10003", "Brad", "San Jose", 200.0, 1400.0, 1500.0, 1500.0);

    public PayrollCalculatorGUI() {
        // Set up the frame
        setTitle("Payroll Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add the input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        employeeNumLabel = new JLabel("Employee Number:");
        employeeNumTextField = new JTextField();
        firstNameLabel = new JLabel("First Name:");
        firstNameTextField = new JTextField();
        lastNameLabel = new JLabel("Last Name:");
        lastNameTextField = new JTextField();
        hoursWorkedLabel = new JLabel("Hours Worked:");
        hoursWorkedTextField = new JTextField();
        inputPanel.add(employeeNumLabel);
        inputPanel.add(employeeNumTextField);
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameTextField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameTextField);
        inputPanel.add(hoursWorkedLabel);
        inputPanel.add(hoursWorkedTextField);
        add(inputPanel, BorderLayout.NORTH);

        // Add the calculate button
        calculateButton = new JButton("Calculate Payroll");
        calculateButton.addActionListener(this);
        add(calculateButton, BorderLayout.CENTER);

        // Add the output panel
        outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            // Get input from the user
            String empNum = employeeNumTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            double hoursWorked = 0;
            try {
                hoursWorked = Double.parseDouble(hoursWorkedTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid hours worked!");
                return;
            }

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
                return;
            }
            // Set the name and compute the gross pay
            employee.setName(firstName, lastName);
            double grossPay = employee.computeGrossPay(hoursWorked);

            // Compute the deductions and net pay
            double riceAllowance = Employee.getRiceAllowance();
            double phoneAllowance = Employee.getPhoneAllowance();
            double clothingAllowance = Employee.getClothingAllowance();
            double sssDeduction = employee.computeSSSDeduction(grossPay);
            double philhealthDeduction = employee.computePhilhealthDeduction(grossPay);
            double pagibigDeduction = employee.computePagibigDeduction(grossPay);
            double taxDeduction = employee.computeTaxDeduction(grossPay);
            double netPay = grossPay - sssDeduction - philhealthDeduction - pagibigDeduction - taxDeduction;

            // Display the payroll details in a GUI frame
            JFrame frame = new JFrame("Payroll Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            // Employee details panel
            JPanel employeeDetailsPanel = new JPanel(new GridLayout(6, 2));
            employeeDetailsPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

            JLabel employeeNumLabel = new JLabel("Employee Number:");
            employeeDetailsPanel.add(employeeNumLabel);
            JTextField employeeNumField = new JTextField(employee.getEmpNum());
            employeeDetailsPanel.add(employeeNumField);

            JLabel firstNameLabel = new JLabel("First Name:");
            employeeDetailsPanel.add(firstNameLabel);
            JTextField firstNameField = new JTextField(employee.getFirstName());
            employeeDetailsPanel.add(firstNameField);

            JLabel lastNameLabel = new JLabel("Last Name:");
            employeeDetailsPanel.add(lastNameLabel);
            JTextField lastNameField = new JTextField(employee.getLastName());
            employeeDetailsPanel.add(lastNameField);

            JLabel hoursWorkedLabel = new JLabel("Hours Worked:");
            employeeDetailsPanel.add(hoursWorkedLabel);
            JTextField hoursWorkedField = new JTextField(Double.toString(hoursWorked));
            employeeDetailsPanel.add(hoursWorkedField);

            // Payroll details panel
            JPanel payrollDetailsPanel = new JPanel(new GridLayout(5, 2));
            payrollDetailsPanel.setBorder(BorderFactory.createTitledBorder("Payroll Details"));

            JLabel grossPayLabel = new JLabel("Gross Pay:");
            payrollDetailsPanel.add(grossPayLabel);
            JTextField grossPayField = new JTextField(Double.toString(grossPay));
            payrollDetailsPanel.add(grossPayField);

            JLabel riceAllowanceLabel = new JLabel("Rice Allowance:");
            payrollDetailsPanel.add(riceAllowanceLabel);
            JTextField riceAllowanceField = new JTextField(Double.toString(riceAllowance));
            payrollDetailsPanel.add(riceAllowanceField);

            JLabel phoneAllowanceLabel = new JLabel("Phone Allowance:");
            payrollDetailsPanel.add(phoneAllowanceLabel);
            JTextField phoneAllowanceField = new JTextField(Double.toString(phoneAllowance));
            payrollDetailsPanel.add(phoneAllowanceField);

            JLabel clothingAllowanceLabel = new JLabel("Clothing Allowance:");
            payrollDetailsPanel.add(clothingAllowanceLabel);
            JTextField clothingAllowanceField = new JTextField(Double.toString(clothingAllowance));
            payrollDetailsPanel.add(clothingAllowanceField);


            JLabel sssDeductionLabel = new JLabel("SSS Deduction:");
            payrollDetailsPanel.add(sssDeductionLabel);
            JTextField sssDeductionField = new JTextField(Double.toString(sssDeduction));
            payrollDetailsPanel.add(sssDeductionField);

            JLabel philhealthDeductionLabel = new JLabel("Philhealth Deduction:");
            payrollDetailsPanel.add(philhealthDeductionLabel);
            JTextField philhealthDeductionField = new JTextField(Double.toString(philhealthDeduction));
            payrollDetailsPanel.add(philhealthDeductionField);

            JLabel pagibigDeductionLabel = new JLabel("Pagibig Deduction:");
            payrollDetailsPanel.add(pagibigDeductionLabel);
            JTextField pagibigDeductionField = new JTextField(Double.toString(pagibigDeduction));
            payrollDetailsPanel.add(pagibigDeductionField);

            JLabel taxDeductionLabel = new JLabel("Tax Deduction:");
            payrollDetailsPanel.add(taxDeductionLabel);
            JTextField taxDeductionField = new JTextField(Double.toString(taxDeduction));
            payrollDetailsPanel.add(taxDeductionField);

            JLabel netPayLabel = new JLabel("Net Pay:");
            payrollDetailsPanel.add(netPayLabel);
            JTextField netPayField = new JTextField(Double.toString(netPay));
            payrollDetailsPanel.add(netPayField);

            // Create labels to display the computed deductions and net pay
            riceAllowanceLabel =  new JLabel("Rice Allowance: " + String.format("%.2f", riceAllowance));
            phoneAllowanceLabel =  new JLabel("Phone Allowance: " + String.format("%.2f", phoneAllowance));
            clothingAllowanceLabel =  new JLabel("Clothing Allowance: " + String.format("%.2f", clothingAllowance));
            grossPayLabel = new JLabel("Gross Pay: " + String.format("%.2f", grossPay));
            sssDeductionLabel = new JLabel("SSS Deduction: " + String.format("%.2f", sssDeduction));
            philhealthDeductionLabel = new JLabel("Philhealth Deduction: " + String.format("%.2f", philhealthDeduction));
            pagibigDeductionLabel = new JLabel("Pagibig Deduction: " + String.format("%.2f", pagibigDeduction));
            taxDeductionLabel = new JLabel("Tax Deduction: " + String.format("%.2f", taxDeduction));
            netPayLabel = new JLabel("Net Pay: " + String.format("%.2f", netPay));

            // Add the labels to the output panel
            outputTextArea.add(riceAllowanceLabel);
            outputTextArea.add(phoneAllowanceLabel);
            outputTextArea.add(clothingAllowanceLabel);
            outputTextArea.add(sssDeductionLabel);
            outputTextArea.add(philhealthDeductionLabel);
            outputTextArea.add(pagibigDeductionLabel);
            outputTextArea.add(taxDeductionLabel);
            outputTextArea.add(netPayLabel);

            // Create a frame to display the employee details and payroll details
            frame = new JFrame("Payroll Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a panel to display the employee details
            JPanel employeePanel = new JPanel(new GridLayout(4, 2));
            employeePanel.add(employeeNumLabel);
            employeePanel.add(new JLabel(employee.getEmpNum()));
            employeePanel.add(firstNameLabel);
            employeePanel.add(new JLabel(firstName));
            employeePanel.add(lastNameLabel);
            employeePanel.add(new JLabel(lastName));
            employeePanel.add(hoursWorkedLabel);
            employeePanel.add(new JLabel(String.format("%.2f", hoursWorked)));

            // Create a panel to display the payroll details
            JPanel payrollPanel = new JPanel(new GridLayout(6, 1));
            payrollPanel.add(grossPayLabel);
            payrollPanel.add(riceAllowanceLabel);
            payrollPanel.add(phoneAllowanceLabel);
            payrollPanel.add(clothingAllowanceLabel);
            payrollPanel.add(sssDeductionLabel);
            payrollPanel.add(philhealthDeductionLabel);
            payrollPanel.add(pagibigDeductionLabel);
            payrollPanel.add(taxDeductionLabel);
            payrollPanel.add(netPayLabel);

            // Create a panel to hold the employee and payroll panels
            JPanel mainPanel = new JPanel(new GridLayout(1, 2));
            mainPanel.add(employeePanel);
            mainPanel.add(payrollPanel);

            // Add the main panel to the frame
            frame.getContentPane().add(mainPanel);

            // Display the frame
            frame.pack();
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PayrollCalculatorGUI();
    }
}


