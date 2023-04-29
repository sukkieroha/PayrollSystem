import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SalaryCalculatorGUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel model;

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

        // Add a button to view employee details and pay details
        JButton viewButton = new JButton("View Details");
        viewButton.setEnabled(false);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and display the employee details and pay details
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeNumber = (String) model.getValueAt(selectedRow, 0);
                    String lastName = (String) model.getValueAt(selectedRow, 1);
                    String firstName = (String) model.getValueAt(selectedRow, 2);
                    String sssNo = (String) model.getValueAt(selectedRow, 3);
                    String philHealthNo = (String) model.getValueAt(selectedRow, 4);
                    String pagibigNo = (String) model.getValueAt(selectedRow, 5);
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
                    employeeDetailsPanel.add(new JLabel(sssNo));
                    employeeDetailsPanel.add(new JLabel("PhilHealth No.:"));
                    employeeDetailsPanel.add(new JLabel(philHealthNo));
                    employeeDetailsPanel.add(new JLabel("TIN:"));
                    employeeDetailsPanel.add(new JLabel(tin));
                    employeeDetailsPanel.add(new JLabel("Pagibig No.:"));
                    employeeDetailsPanel.add(new JLabel(pagibigNo));

                    // Create a panel for the pay details
                    JPanel payDetailsPanel = new JPanel(new GridLayout(2, 2));
                    payDetailsPanel.setBorder(BorderFactory.createTitledBorder("Pay Details"));
                    payDetailsPanel.add(new JLabel("Pay Period:"));
                    payDetailsPanel.add(new JTextField());
                    payDetailsPanel.add(new JLabel("Salary:"));
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

        // Add a selection listener to enable/disable the view button
        employeeTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                boolean isSelected = (employeeTable.getSelectedRow() != -1);
                viewButton.setEnabled(isSelected);
            }
        });

        // Add the table and button to the frame
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(viewButton, BorderLayout.SOUTH);

        // Set the size and show the frame
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SalaryCalculatorGUI();
    }
}