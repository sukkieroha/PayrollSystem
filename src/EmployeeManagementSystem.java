import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeManagementSystem extends JFrame {

    JTable employeeTable;
    DefaultTableModel model;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton leaveButton;
    private JButton calculatePayButton;
    JButton showButton;
    private JTextField employeeNumberField, lastNameField, firstNameField, grossPayField, riceAllowanceField,phoneAllowanceField,clothingAllowanceField, sssDeductionField,philhealthDeductionField, pagibigDeductionField,taxDeductionField, netPayField  ;
    private int selectedRow;
    private ArrayList<Employee> employees;
    private ArrayList<Employee> deletedEmployees;
    private boolean isEditing;
    private static final String EMPLOYEES_FILE = "employees.csv";
    private static final String ACCOUNTS_FILE = "authorizedAccounts.csv";

    public EmployeeManagementSystem() {


        JFrame loginframe = new JFrame("Employee Management System");
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setSize(400, 150);
        loginframe.setLayout(new BorderLayout());
        loginframe.getContentPane().setBackground(Color.BLUE);

        // Create an empty border
        int marginSize = 10;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize, marginSize, marginSize);
        loginframe.getRootPane().setBorder(emptyBorder);

        // Create the credentials panel
        JPanel credentialsPanel = new JPanel(new GridLayout(2, 2));
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        credentialsPanel.add(new JLabel("Username:"));
        credentialsPanel.add(usernameField);
        credentialsPanel.add(new JLabel("Password:"));
        credentialsPanel.add(passwordField);
        loginframe.add(credentialsPanel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        loginframe.add(buttonPanel, BorderLayout.SOUTH);

        loginframe.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateCredentials(username, password)) {
                    loginframe.dispose();
                    showApplicationWindow();
                } else {
                    JOptionPane.showMessageDialog(loginframe, "Incorrect login credentials. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        });


    }
    boolean validateCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("authorizedAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String storedUsername = data[0];
                String storedPassword = data[1];

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void showApplicationWindow() {
        setTitle("Employee Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the table model
        model = new DefaultTableModel();
        model.addColumn("Employee Number");
        model.addColumn(" First Name");
        model.addColumn("Last Name");
        model.addColumn("SSS No.");
        model.addColumn("PhilHealth No.");
        model.addColumn("Pagibig No.");
        model.addColumn("TIN");

        // Read the employee details from the CSV file and add them to the table model
        try (BufferedReader br = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading employees.csv", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


        // Create the table
        employeeTable = new JTable(model);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add buttons to view, delete, and update employee details, and to save or cancel changes
        viewButton = new JButton("View Details");
        viewButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        updateButton = new JButton("Update");
        updateButton.setEnabled(false);
        leaveButton = new JButton("Apply Leave");
        leaveButton.setEnabled(false);
        calculatePayButton = new JButton("Calculate Pay");
        calculatePayButton.setEnabled(false);

        // Add action listeners to buttons
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and display the employee details and pay details
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 2);
                    String firstName = (String) model.getValueAt(selectedRow, 1);
                    String sssNumber = (String) model.getValueAt(selectedRow, 3);
                    String philhealthNumber = (String) model.getValueAt(selectedRow, 4);
                    String pagibigNumber = (String) model.getValueAt(selectedRow, 5);
                    String tin = (String) model.getValueAt(selectedRow, 6);

                    // Display the employee details and pay details in a new JFrame
                    JFrame detailsFrame = new JFrame();
                    detailsFrame.setTitle("Employee Details - " + firstName + " " + lastName);
                    detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    detailsFrame.setLayout(new BorderLayout());

                    // Create a panel for the employee details
                    JPanel employeeDetailsPanel = new JPanel(new GridLayout(7, 2));
                    employeeDetailsPanel.add(new JLabel("Employee Number:"));
                    employeeDetailsPanel.add(new JLabel(employeeNumber));
                    employeeDetailsPanel.add(new JLabel("First Name:"));
                    employeeDetailsPanel.add(new JLabel(firstName));
                    employeeDetailsPanel.add(new JLabel("Last Name:"));
                    employeeDetailsPanel.add(new JLabel(lastName));
                    employeeDetailsPanel.add(new JLabel("SSS No.:"));
                    employeeDetailsPanel.add(new JLabel(sssNumber));
                    employeeDetailsPanel.add(new JLabel("PhilHealth No.:"));
                    employeeDetailsPanel.add(new JLabel(philhealthNumber));
                    employeeDetailsPanel.add(new JLabel("Pagibig No.:"));
                    employeeDetailsPanel.add(new JLabel(pagibigNumber));
                    employeeDetailsPanel.add(new JLabel("TIN:"));
                    employeeDetailsPanel.add(new JLabel(tin));

                    // Create a panel for the pay details
                    JPanel payDetailsPanel = new JPanel(new BorderLayout());
                    payDetailsPanel.setBorder(BorderFactory.createTitledBorder("Pay Details"));

                    // Create a dropdown for selecting the weekly pay date
                    JComboBox<String> payDateComboBox = new JComboBox<>();
                    payDateComboBox.addItem("Select");
                    payDateComboBox.addItem("May 1 to 7");
                    payDateComboBox.addItem("May 8 to 14");
                    payDateComboBox.addItem("May 15 to 21");
                    payDateComboBox.addItem("May 22 to 28");


                    // Create a panel to hold the labels and values
                    JPanel detailsPanel = new JPanel(new GridLayout(14, 2));

                    // Add the detailsPanel to the center of the payDetailsPanel
                    payDetailsPanel.add(detailsPanel, BorderLayout.CENTER);

                    // Add the pay details labels and text fields to the details panel
                    detailsPanel.add(new JLabel("Employee Number:"));
                    JTextField employeeNumberField = new JTextField();
                    detailsPanel.add(employeeNumberField);

                    detailsPanel.add(new JLabel("First Name:"));
                    JTextField firstNameField = new JTextField();
                    detailsPanel.add(firstNameField);

                    detailsPanel.add(new JLabel("Last Name:"));
                    JTextField lastNameField = new JTextField();
                    detailsPanel.add(lastNameField);

                    detailsPanel.add(new JLabel("Gross Pay:"));
                    JTextField grossPayField = new JTextField();
                    detailsPanel.add(grossPayField);

                    detailsPanel.add(new JLabel("Rice Allowance:"));
                    JTextField riceAllowanceField = new JTextField();
                    detailsPanel.add(riceAllowanceField);

                    detailsPanel.add(new JLabel("Phone Allowance:"));
                    JTextField phoneAllowanceField = new JTextField();
                    detailsPanel.add(phoneAllowanceField);

                    detailsPanel.add(new JLabel("Clothing Allowance:"));
                    JTextField clothingAllowanceField = new JTextField();
                    detailsPanel.add(clothingAllowanceField);

                    detailsPanel.add(new JLabel("SSS Deduction:"));
                    JTextField sssDeductionField = new JTextField();
                    detailsPanel.add(sssDeductionField);

                    detailsPanel.add(new JLabel("Philhealth Deduction:"));
                    JTextField philhealthDeductionField = new JTextField();
                    detailsPanel.add(philhealthDeductionField);

                    detailsPanel.add(new JLabel("Pagibig Deduction:"));
                    JTextField pagibigDeductionField = new JTextField();
                    detailsPanel.add(pagibigDeductionField);

                    detailsPanel.add(new JLabel("Tax Deduction:"));
                    JTextField taxDeductionField = new JTextField();
                    detailsPanel.add(taxDeductionField);

                    detailsPanel.add(new JLabel("Net Pay:"));
                    JTextField netPayField = new JTextField();
                    detailsPanel.add(netPayField);


                    // Create a panel to hold the payDateComboBox with a FlowLayout
                    JPanel payDateComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    payDateComboBoxPanel.add(new JLabel("Select Pay Date:"));
                    payDateComboBoxPanel.add(payDateComboBox);

                    // Create a "Show" button to display the selected pay date details
                    JButton showButton = new JButton("Show");
                    payDateComboBoxPanel.add(showButton);

                    // Add action listener for the Show button
                    showButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String selectedPayDate = (String) payDateComboBox.getSelectedItem();
                            String[] payDateParts = selectedPayDate.split(" to ");
                            String startDate = payDateParts[0];
                            String endDate = payDateParts[1];

                            String selectedEmployeeNumber = (String) model.getValueAt(selectedRow, 0); // Get the selected employee number

                            // Fetch the pay details from the payroll.csv file based on the selected pay date and employee number
                            try (BufferedReader reader = new BufferedReader(new FileReader("payroll.csv"))) {
                                String line;
                                boolean isFirstLine = true;
                                boolean isPayDateFound = false;
                                while ((line = reader.readLine()) != null) {
                                    if (isFirstLine) {
                                        isFirstLine = false;
                                        continue; // Skip the header line
                                    }
                                    String[] data = line.split(",");
                                    String employeeNumberFromFile = data[0].trim();
                                    String dataStartDate = data[3].trim();
                                    String dataEndDate = data[4].trim();
                                    if (employeeNumberFromFile.equals(selectedEmployeeNumber) && dataStartDate.equals(startDate) && dataEndDate.equals(endDate)) {
                                        System.out.println(Arrays.toString(data));
                                        isPayDateFound = true;

                                        // Update the values of the pay details text fields in the details panel
                                        employeeNumberField.setText(data[0].trim());
                                        firstNameField.setText(data[1].trim());
                                        lastNameField.setText(data[2].trim());
                                        grossPayField.setText(data[5].trim());
                                        riceAllowanceField.setText(data[6].trim());
                                        phoneAllowanceField.setText(data[7].trim());
                                        clothingAllowanceField.setText(data[8].trim());
                                        sssDeductionField.setText(data[9].trim());
                                        philhealthDeductionField.setText(data[10].trim());
                                        pagibigDeductionField.setText(data[11].trim());
                                        taxDeductionField.setText(data[12].trim());
                                        netPayField.setText(data[13].trim());

                                        break; // Exit the loop since we found the matching pay details
                                    }
                                }

                                if (!isPayDateFound) {
                                    // Display a message if the selected pay date is not found for the employee in the file
                                    JOptionPane.showMessageDialog(detailsFrame, "No pay details found for the selected pay date and employee.", "Pay Details Not Found", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                // Handle file reading error
                            }
                        }
                    });



                    // Add the payDateComboBoxPanel to the payDetailsPanel
                    payDetailsPanel.add(payDateComboBoxPanel, BorderLayout.NORTH);

                    // Create a panel to hold employee details and pay details
                    JPanel contentPanel = new JPanel(new BorderLayout());
                    contentPanel.add(employeeDetailsPanel, BorderLayout.NORTH);
                    contentPanel.add(payDetailsPanel, BorderLayout.CENTER);


                    // Add the payDatePanel and contentPanel to the detailsFrame
                    detailsFrame.add(contentPanel, BorderLayout.CENTER);

                    // Set the size and show the details frame
                    detailsFrame.setSize(400, 600);
                    detailsFrame.setVisible(true);
                }
            }
        });
        // Add action listener for the Calculate Pay button
        calculatePayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String firstName = (String) model.getValueAt(selectedRow, 1);
                    String lastName = (String) model.getValueAt(selectedRow, 2);

                    // Create a custom JFrame for input
                    JFrame inputFrame = new JFrame("Payroll Calculation");
                    inputFrame.setSize(300, 150);
                    inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    inputFrame.setLayout(new GridLayout(3, 2));

                    // Add labels and text fields to the JFrame
                    // Create a dropdown for selecting the weekly pay date
                    JComboBox<String> payDateComboBox = new JComboBox<>();
                    payDateComboBox.addItem("May 1 to 7");
                    payDateComboBox.addItem("May 8 to 14");
                    payDateComboBox.addItem("May 15 to 21");
                    payDateComboBox.addItem("May 22 to 28");
                    JLabel hoursLabel = new JLabel("Number of Hours Worked:");
                    JTextField hoursTextField = new JTextField();
                    JButton calculateButton = new JButton("Calculate");

                    // Add action listener to the Calculate button
                    calculateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String selectedPayPeriod = (String) payDateComboBox.getSelectedItem();
                            String[] payPeriodParts = selectedPayPeriod.split(" to ");
                            String startDate = payPeriodParts[0];
                            String endDate = payPeriodParts[1];
                            String hoursWorkedStr = hoursTextField.getText();

                            // Validate and convert the hours worked input
                            double hoursWorked = 0.0;
                            try {
                                hoursWorked = Double.parseDouble(hoursWorkedStr);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid input for hours worked. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Call the method to calculate and save the pay for the selected employee
                            calculateAndSavePay(employeeNumber, firstName, lastName, startDate, endDate, hoursWorked);

                            // Close the input frame
                            inputFrame.dispose();
                        }
                    });

                    // Add components to the JFrame
                    inputFrame.add(new JLabel("Pay Date:"));
                    inputFrame.add(payDateComboBox);
                    inputFrame.add(hoursLabel);
                    inputFrame.add(hoursTextField);
                    inputFrame.add(calculateButton);


                    // Set the JFrame visible
                    inputFrame.setVisible(true);
                }
            }
        });
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 2);
                    String firstName = (String) model.getValueAt(selectedRow, 1);

                    // Display a dialog or prompt to enter leave details
                    String[] options = {"Vacation ", "Sick ", "Emergency "};
                    int leaveType = JOptionPane.showOptionDialog(null, "Select Leave Type", "Leave Application", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (leaveType != JOptionPane.CLOSED_OPTION) {
                        String leaveTypeName = options[leaveType];

                        // Display a dialog or prompt to enter leave dates
                        String startDate = JOptionPane.showInputDialog(null, "Enter Start Date (MM-DD-YYYY):");
                        String endDate = JOptionPane.showInputDialog(null, "Enter End Date (MM-DD-YYYY)):");

                        // Save leave application to a different CSV file
                        saveLeaveApplication(employeeNumber,firstName, lastName, leaveTypeName, startDate, endDate);

                        // Update remaining leave count for the employee
                        updateRemainingLeaveCount(employeeNumber, leaveTypeName);
                    }
                }
            }

            private void saveLeaveApplication(String employeeNumber, String firstName, String lastName,String leaveTypeName, String startDate, String endDate) {
                // Write leave details to a CSV file
                try (CSVWriter writer = new CSVWriter(new FileWriter("leaveApplication.csv", true))) {
                    String[] record = {employeeNumber,firstName, lastName, leaveTypeName, startDate, endDate};
                    writer.writeNext(record);
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            private void updateRemainingLeaveCount(String employeeNumber, String leaveTypeName) {
                // Retrieve remaining leave count for the employee
                int remainingLeaves = getRemainingLeaveCountFromDataSource(employeeNumber, leaveTypeName);

                // Update the UI or display a message with the remaining leave count
                JOptionPane.showMessageDialog(null, "You have " + remainingLeaves + " " + leaveTypeName + " leaves remaining.");
            }

            // Helper method to retrieve remaining leave count from a data source
            private int getRemainingLeaveCountFromDataSource(String employeeNumber, String leaveTypeName) {
                // Retrieve and calculate remaining leave count based on the data source

                if (leaveTypeName.equals("Vacation ")) {
                    return 10 - getUsedLeaveCountFromDataSource(employeeNumber, leaveTypeName);
                } else if (leaveTypeName.equals("Sick ")) {
                    return 5 - getUsedLeaveCountFromDataSource(employeeNumber, leaveTypeName);
                } else if (leaveTypeName.equals("Emergency ")) {
                    return 5 - getUsedLeaveCountFromDataSource(employeeNumber, leaveTypeName);
                } else {
                    return 0;
                }
            }

            // Helper method to retrieve used leave count from a data source
            private int getUsedLeaveCountFromDataSource(String employeeNumber, String leaveTypeName) {
                // Read the "leaveApplication.csv" file and calculate the used leave count

                try (CSVReader reader = new CSVReader(new FileReader("leaveApplication.csv"))) {
                    String[] nextLine;
                    int usedLeaveCount = 0;

                    while ((nextLine = reader.readNext()) != null) {
                        String csvEmployeeNumber = nextLine[0];
                        String csvLeaveTypeName = nextLine[3];

                        if (csvEmployeeNumber.equals(employeeNumber) && csvLeaveTypeName.equals(leaveTypeName)) {
                            usedLeaveCount++;
                        }
                    }

                    return usedLeaveCount;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return 0; // Return 0 if an error occurs while reading the file
                }
            }
        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Confirm the deletion
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // Remove the selected row from the table model
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the current employee details
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 2);
                    String firstName = (String) model.getValueAt(selectedRow, 1);
                    String sssNumber = (String) model.getValueAt(selectedRow, 3);
                    String philhealthNumber = (String) model.getValueAt(selectedRow, 4);
                    String pagibigNumber = (String) model.getValueAt(selectedRow, 5);
                    String tin = (String) model.getValueAt(selectedRow, 6);


                    // Display a dialog for updating the employee details
                    JPanel updatePanel = new JPanel(new GridLayout(7, 2));
                    updatePanel.add(new JLabel("Employee Number:"));
                    JTextField employeeNumberField = new JTextField(employeeNumber);
                    employeeNumberField.setEditable(false);
                    updatePanel.add(employeeNumberField);
                    updatePanel.add(new JLabel("Last Name:"));
                    JTextField lastNameField = new JTextField(lastName);
                    updatePanel.add(lastNameField);
                    updatePanel.add(new JLabel("First Name:"));
                    JTextField firstNameField = new JTextField(firstName);
                    updatePanel.add(firstNameField);
                    updatePanel.add(new JLabel("SSS No.:"));
                    JTextField sssNumberField = new JTextField(sssNumber);
                    updatePanel.add(sssNumberField);
                    updatePanel.add(new JLabel("PhilHealth No.:"));
                    JTextField philhealthNumberField = new JTextField(philhealthNumber);
                    updatePanel.add(philhealthNumberField);
                    updatePanel.add(new JLabel("Pagibig No.:"));
                    JTextField pagibigNumberField = new JTextField(pagibigNumber);
                    updatePanel.add(pagibigNumberField);
                    updatePanel.add(new JLabel("TIN:"));
                    JTextField tinField = new JTextField(tin);
                    updatePanel.add(tinField);


                    // Create the dialog box
                    int result = JOptionPane.showConfirmDialog(null, updatePanel, "Update Employee Details", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        // Get the new values from the text fields
                        String newLastName = lastNameField.getText();
                        String newFirstName = firstNameField.getText();
                        String newSssNo = sssNumberField.getText();
                        String newPhilHealthNo = philhealthNumberField.getText();
                        String newPagibigNo = pagibigNumberField.getText();
                        String newTin = tinField.getText();


                        // Update the values in the table model
                        model.setValueAt(newLastName, selectedRow, 1);
                        model.setValueAt(newFirstName, selectedRow, 2);
                        model.setValueAt(newSssNo, selectedRow, 3);
                        model.setValueAt(newPhilHealthNo, selectedRow, 4);
                        model.setValueAt(newPagibigNo, selectedRow, 5);
                        model.setValueAt(newTin, selectedRow, 6);

                    }
                }

            }

        });


        // Add a selection listener to enable/disable the view, delete, and update buttons
        employeeTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                selectedRow = employeeTable.getSelectedRow();
                boolean isSelected = (selectedRow != -1);
                viewButton.setEnabled(isSelected);
                deleteButton.setEnabled(isSelected);
                updateButton.setEnabled(isSelected);
                leaveButton.setEnabled(isSelected);
                calculatePayButton.setEnabled(isSelected);


            }
        });

        // Add the table and buttons to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(leaveButton);
        buttonPanel.add(calculatePayButton);
        add(buttonPanel, BorderLayout.SOUTH);


        // Add the table and button panel to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and show the frame
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void calculateAndSavePay(String employeeNumber, String firstName, String lastName, String startDate, String endDate, double hoursWorked) {
        // Read the employee details from employees.csv file
        String employeeFilename = "employees.csv";
        double hourlyRate = 0.0;
        double riceAllowance = 0.0;
        double phoneAllowance = 0.0;
        double clothingAllowance = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFilename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                String empNumber = data[0];
                if (empNumber.equals(employeeNumber)) {
                    hourlyRate = Double.parseDouble(data[7]);
                    riceAllowance = Double.parseDouble(data[8]);
                    phoneAllowance = Double.parseDouble(data[9]);
                    clothingAllowance = Double.parseDouble(data[10]);
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading employees.csv", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the calculation to get the pay for the employee
        double grossPay = hourlyRate * hoursWorked + riceAllowance + phoneAllowance + clothingAllowance;
        riceAllowance = riceAllowance / 4; //allowances applied on a weekly basis
        phoneAllowance = phoneAllowance /4;
        clothingAllowance = clothingAllowance /4;
        double sssDeduction = Employee.computeSSSDeduction(grossPay);
        double philhealthDeduction = Employee.computePhilhealthDeduction(grossPay);
        double pagibigDeduction = Employee.computePagibigDeduction(grossPay);
        double taxDeduction = Employee.computeTaxDeduction(grossPay);
        double netPay = grossPay - sssDeduction - philhealthDeduction - pagibigDeduction - taxDeduction;

        // Save the calculated pay to the payroll.csv file
        String payrollFilename = "payroll.csv";
        try (FileWriter writer = new FileWriter(payrollFilename, true)) {
            writer.write(String.format("%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f\n", employeeNumber, firstName, lastName, startDate, endDate, grossPay,riceAllowance,phoneAllowance,clothingAllowance, sssDeduction, philhealthDeduction, pagibigDeduction, taxDeduction, netPay));
            writer.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving payroll data to payroll.csv", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Display the calculated pay in a dialog box
        JOptionPane.showMessageDialog(null, "Pay for Employee Number " + employeeNumber + ":\n" +
                "Gross Pay: Php " + grossPay + "\n" +
                "Rice Allowance: Php " + riceAllowance + "\n" +
                "Phone Allowance: Php " + phoneAllowance + "\n" +
                "Clothing Allowance: Php " + clothingAllowance + "\n" +
                "SSS Deduction: Php " + sssDeduction + "\n" +
                "Philhealth Deduction: Php " + philhealthDeduction + "\n" +
                "Pagibig Deduction: Php " + pagibigDeduction + "\n" +
                "Tax Deduction: Php " + taxDeduction + "\n" +
                "Net Pay: Php " + netPay, "Pay Calculation", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new EmployeeManagementSystem();


    }

}

