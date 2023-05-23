
package employeeant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author jpaha
 */

    public class enployee extends JFrame {
        JTable table = new JTable(new DefaultTableModel());
        JLabel empInfoLabel = new JLabel();
        JTextField empNumberField = new JTextField(15);
        

        
    public enployee() {
        setTitle("Employee System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel empNumberLabel = new JLabel("Employee Number:");
        
        JButton retrieveButton = new JButton("Retrieve");
        JButton payroll = new JButton("Payroll");

        topPanel.add(empNumberLabel);
        topPanel.add(empNumberField);
        topPanel.add(retrieveButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        table = new JTable(); // Create the table instance

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        

        bottomPanel.add(empInfoLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        
        retrieveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                employeeret();
            }
        });
        
        
        
        loadCSVData("employee.csv");
        topPanel.add(payroll);
        
        
        payroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                payroll otherForm2 = new payroll();
                otherForm2.setVisible(true);
            }
        });
        
        setVisible(true);
    }

    private void loadCSVData(String csvFilePath) {
        DefaultTableModel model = new DefaultTableModel(); // Create a new DefaultTableModel instance
        model.setColumnIdentifiers(new String[]{"Employee Number", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"});

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setModel(model); // Set the model to the table
    }
    
    private void employeeret() {
        String employeeNumber = empNumberField.getText();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing table data

        try (BufferedReader reader = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > 0 && rowData[0].equals(employeeNumber)) {
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
           SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                   new enployee();
               }
           });
       }
}
