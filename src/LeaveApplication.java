import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LeaveApplication extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel model;
    private JButton viewButton, deleteButton, updateButton, leaveButton;
    private int selectedRow;
    private ArrayList<Employee> employees;
    private ArrayList<Employee> deletedEmployees;
    private boolean isEditing;
    private String startDateStr;
    private String endDateStr;
    private static final String EMPLOYEES_FILE = "employees.csv";
    private static final String ACCOUNTS_FILE = "authorizedAccounts.csv";


    public LeaveApplication() {


        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLUE);

        // Create an empty border
        int marginSize = 10;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize, marginSize, marginSize);
        frame.getRootPane().setBorder(emptyBorder);

        // Create the credentials panel
        JPanel credentialsPanel = new JPanel(new GridLayout(2, 2));
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        credentialsPanel.add(new JLabel("Username:"));
        credentialsPanel.add(usernameField);
        credentialsPanel.add(new JLabel("Password:"));
        credentialsPanel.add(passwordField);
        frame.add(credentialsPanel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateCredentials(username, password)) {
                    showApplicationWindow();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect login credentials. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        });


    }
    private boolean validateCredentials(String username, String password) {
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
        model.addColumn("Last Name");
        model.addColumn("First Name");
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

        // Add action listeners to buttons
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and display the employee details and pay details
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 1);
                    String firstName = (String) model.getValueAt(selectedRow, 2);
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
                    employeeDetailsPanel.add(new JLabel("Last Name:"));
                    employeeDetailsPanel.add(new JLabel(lastName));
                    employeeDetailsPanel.add(new JLabel("First Name:"));
                    employeeDetailsPanel.add(new JLabel(firstName));
                    employeeDetailsPanel.add(new JLabel("SSS No.:"));
                    employeeDetailsPanel.add(new JLabel(sssNumber));
                    employeeDetailsPanel.add(new JLabel("PhilHealth No.:"));
                    employeeDetailsPanel.add(new JLabel(philhealthNumber));
                    employeeDetailsPanel.add(new JLabel("Pagibig No.:"));
                    employeeDetailsPanel.add(new JLabel(pagibigNumber));
                    employeeDetailsPanel.add(new JLabel("TIN:"));
                    employeeDetailsPanel.add(new JLabel(tin));

                    // Create a panel for the pay details
                    //TODO: embed the PayRoll Calculation here to display pay details//
                    JPanel payDetailsPanel = new JPanel(new GridLayout(2, 2));
                    payDetailsPanel.setBorder(BorderFactory.createTitledBorder("Pay Details"));
                    payDetailsPanel.add(new JLabel("Pay Coverage:"));
                    payDetailsPanel.add(new JTextField());
                    payDetailsPanel.add(new JLabel("Net Pay:"));
                    payDetailsPanel.add(new JTextField());

                    // Add the panels to the details frame
                    detailsFrame.add(employeeDetailsPanel, BorderLayout.CENTER);
                    detailsFrame.add(payDetailsPanel, BorderLayout.SOUTH);

                    // Set the size and show the details frame
                    detailsFrame.setSize(400, 200);
                    detailsFrame.setVisible(true);
                }
            }
        });
        leaveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and display the employee details and pay details
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 1);
                    String firstName = (String) model.getValueAt(selectedRow, 2);
                    String sssNumber = (String) model.getValueAt(selectedRow, 3);
                    String philhealthNumber = (String) model.getValueAt(selectedRow, 4);
                    String pagibigNumber = (String) model.getValueAt(selectedRow, 5);
                    String tin = (String) model.getValueAt(selectedRow, 6);

                    // Create a new dialog box for the leave application
                    JFrame frame = new JFrame();
                    JDialog leaveDialog = new JDialog(frame, "Leave Application", true);
                    leaveDialog.setLayout(new BorderLayout());

                    // Create the leave application form
                    JPanel leaveFormPanel = new JPanel();
                    leaveFormPanel.setLayout(new GridLayout(5, 2));

                    // Add a field for the leave type
                    JLabel leaveTypeLabel = new JLabel("Leave Type:");
                    leaveFormPanel.add(leaveTypeLabel);
                    JComboBox<String> leaveTypeComboBox = new JComboBox<String>(new String[]{"Sick", "Vacation", "Emergency"});
                    leaveFormPanel.add(leaveTypeComboBox);

                    // Add a field for the start date
                    JLabel startDateLabel = new JLabel("Start Date (mm/dd/yyyy):");
                    leaveFormPanel.add(startDateLabel);
                    JTextField startDateField = new JTextField();
                    leaveFormPanel.add(startDateField);

                    // Add a field for the end date
                    JLabel endDateLabel = new JLabel("End Date (mm/dd/yyyy):");
                    leaveFormPanel.add(endDateLabel);
                    JTextField endDateField = new JTextField();
                    leaveFormPanel.add(endDateField);

                    // Add a submit button
                    JButton submitButton = new JButton("Submit");
                    leaveFormPanel.add(submitButton);
                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Get the selected leave type, start date, and end date
                            String leaveType = (String) leaveTypeComboBox.getSelectedItem();
                            String startDate = startDateField.getText();
                            String endDate = endDateField.getText();

                            try {
                                FileWriter writer = new FileWriter("leaveApplication.csv", true);
                                writer.write(employeeNumber + "," + lastName + "," + firstName + "," + sssNumber + "," + philhealthNumber + "," + pagibigNumber + "," + tin + "," + leaveTypeComboBox.getSelectedItem() + "," + startDateField.getText() + "," + endDateField.getText() + "\n");
                                writer.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            // Count the remaining leaves for the employee
                            int sickLeavesTaken = 0;
                            int vacationLeavesTaken = 0;
                            int emergencyLeavesTaken = 0;

                            try {
                                Scanner scanner = new Scanner(new File("leaveApplication.csv"));
                                scanner.useDelimiter(",|\\n");

                                while (scanner.hasNext()) {
                                    String employeeNum = scanner.next();
                                    String lastName = scanner.next();
                                    String firstName = scanner.next();
                                    String sssNumber = scanner.next();
                                    String philhealthNumber = scanner.next();
                                    String pagibigNumber = scanner.next();
                                    String tin = scanner.next();
                                    leaveType = scanner.next();
                                    startDate = scanner.next();
                                    endDate = scanner.next();

                                    if (employeeNum.equals(employeeNumber)) {
                                        if (leaveType.equals("Sick")) {
                                            sickLeavesTaken++;
                                        } else if (leaveType.equals("Vacation")) {
                                            vacationLeavesTaken++;
                                        } else if (leaveType.equals("Emergency")) {
                                            emergencyLeavesTaken++;
                                        }
                                    }
                                }

                                scanner.close();
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }

                            int sickLeavesRemaining = 5 - sickLeavesTaken;
                            int vacationLeavesRemaining = 10 - vacationLeavesTaken;
                            int emergencyLeavesRemaining = 5 - emergencyLeavesTaken;

                            // Display the remaining leaves for the employee
                            JOptionPane.showMessageDialog(leaveDialog, "Remaining Leaves:\nSick: " + sickLeavesRemaining + "\nVacation: " + vacationLeavesRemaining + "\nEmergency: " + emergencyLeavesRemaining, "Leave Application", JOptionPane.INFORMATION_MESSAGE);

                            leaveDialog.dispose();
                        }
                    });

                    leaveDialog.add(leaveFormPanel, BorderLayout.CENTER);
                    leaveDialog.pack();
                    leaveDialog.setLocationRelativeTo(null);
                    leaveDialog.setVisible(true);
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
                    String lastName = (String) model.getValueAt(selectedRow, 1);
                    String firstName = (String) model.getValueAt(selectedRow, 2);
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
            }
        });

        // Add the table and buttons to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(leaveButton);
        add(buttonPanel, BorderLayout.SOUTH);


        // Add the table and button panel to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and show the frame
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private int getLeaveDuration(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM-DD-YYY");
        try {
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);
            long durationMillis = end.getTime() - start.getTime();
            int days = (int) (durationMillis / (1000 * 60 * 60 * 24));
            return days + 1;
        } catch (ParseException e) {
            System.out.println("Error parsing dates: " + e.getMessage());
            return 0;
        }
    }

       public static void main(String[] args) {
        new LeaveApplication();


    }

}




