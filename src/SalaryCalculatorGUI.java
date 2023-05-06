import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SalaryCalculatorGUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel model;
    private JButton viewButton, deleteButton, updateButton;
    private int selectedRow;
    private ArrayList<Employee> employees;
    private ArrayList<Employee> deletedEmployees;
    private boolean isEditing;

    public SalaryCalculatorGUI() {
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
            }
        });

        // Add the table and buttons to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);


        // Add the table and button panel to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and show the frame
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SalaryCalculatorGUI();
    }

}

