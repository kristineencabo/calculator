
package employeeant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;


public class payroll extends JFrame{
    private JFrame frame;
    private JTextField employeeIdField;
    private JTextField employeeNameField;
    private JTextField employeenohrField;
    private JTextField salarymonthly;
    private JTextField salarymonthly1;
    private JTextField salarymonthly2;
    private JTextField salarymonthly3;
    private JTextField salarymonthly4;
    
    String csvFile = "employee.csv";
    String targetEmployeeNumber = "";
    String lastField = "";
    int numweek = 0;
    int monthlygross = 0;
    double ph = 0;
    double sss = 0;
    double tax = 0;
    double pagibig = 0;
    
    public payroll() {
        frame = new JFrame("Payroll System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 900);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(20, 1, 40, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(Color.white);

        JLabel employeeIdLabel = new JLabel("Employee ID:");
        employeeIdField = new JTextField(15);
        employeeIdField.setPreferredSize(new Dimension(150, employeeIdField.getPreferredSize().height));

        JLabel employeenameLabel = new JLabel("Name:");
        employeeNameField = new JTextField(15);
        employeeNameField.setPreferredSize(new Dimension(150, employeeNameField.getPreferredSize().height));

        JLabel employeenohrLabel = new JLabel("Number of hours work per week:");
        employeenohrField = new JTextField(15);
        employeenohrField.setPreferredSize(new Dimension(150, employeeNameField.getPreferredSize().height));
        
        JLabel salarymonthly = new JLabel("Monthly gross salary: ");
        JLabel salarymonthly1 = new JLabel("    Week 1: ");
        JLabel salarymonthly2 = new JLabel("    Week 2: ");
        JLabel salarymonthly3 = new JLabel("    Week 3: ");
        JLabel salarymonthly4 = new JLabel("    Week 4: ");
        JLabel salarymonthlynet = new JLabel("Monthly net salary: ");
        
        
        JLabel statutories = new JLabel("Statutories: ");
        JLabel statutories1 = new JLabel("  PAG-IBIG: ");
        JLabel statutories2 = new JLabel("  PhilHealth: ");
        JLabel statutories3 = new JLabel("  SSS: ");
        JLabel statutories4 = new JLabel("  Withholding Tax: ");
        
        
        
        inputPanel.add(employeeIdLabel);
        inputPanel.add(employeeIdField);
        inputPanel.add(employeenameLabel);
        inputPanel.add(employeeNameField);
        inputPanel.add(employeenohrLabel);
        inputPanel.add(employeenohrField);
        inputPanel.add(salarymonthly);
        inputPanel.add(salarymonthly1);
        inputPanel.add(salarymonthly2);
        inputPanel.add(salarymonthly3);
        inputPanel.add(salarymonthly4);
        inputPanel.add(salarymonthlynet);
        inputPanel.add(statutories);
        inputPanel.add(statutories1);
        inputPanel.add(statutories2);
        inputPanel.add(statutories3);
        inputPanel.add(statutories4);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(120, 30));
        calculateButton.setBackground(new Color(0, 153, 0));
        calculateButton.setForeground(Color.white);
        calculateButton.setFocusPainted(false);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton leaveButton = new JButton("Leave Form");
        leaveButton.setPreferredSize(new Dimension(120, 30));
        leaveButton.setBackground(new Color(0, 153, 0));
        leaveButton.setForeground(Color.white);
        leaveButton.setFocusPainted(false);
        leaveButton.setFont(new Font("Arial", Font.BOLD, 14));


        frame.add(calculateButton, BorderLayout.SOUTH);
        frame.add(leaveButton, BorderLayout.SOUTH);
        
        
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
               leave otherForm3 = new leave();
               otherForm3.setVisible(true);
            }
        });

        
        calculateButton.addActionListener(new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    targetEmployeeNumber = employeeIdField.getText();
                    String[] fields = line.split(",");
                    String employeeNumber = fields[0].trim(); // Assuming employee number is in the first field
                    if (employeeNumber.equals(targetEmployeeNumber)) {
                        lastField = fields[fields.length - 1].trim();
                         // Found the matching employee number, no need to continue reading the file
                        int lastFieldValue = (int) Double.parseDouble(lastField);
                        int hours = (int) Double.parseDouble(employeenohrField.getText());
                        monthlygross = hours * lastFieldValue * 4;
                        ph = 0.015 * monthlygross;
                        sss = 1125;
                        pagibig = 0.2 * monthlygross;
                        
                        if (monthlygross >= 20832 && monthlygross < 33333){
                            tax = (monthlygross - 20833) * 0.2;
                        } else if (monthlygross >= 33333 && monthlygross < 66667){
                            tax = 2500 + ((monthlygross - 33333) * 0.25);
                        } else if (monthlygross >= 66667 && monthlygross < 166667){
                            tax = 10833 + ((monthlygross - 66667) * 0.3);
                        } else if (monthlygross >= 166667 && monthlygross < 666667){
                            tax = 1408333.33 + ((monthlygross - 166667) * 0.32);
                        } else if (monthlygross >= 666667){
                            tax = 200833.33 + ((monthlygross - 666667) * 0.35);
                        }
                        
                        salarymonthly.setText("Monthly gross salary: Php. "+ monthlygross);
                        salarymonthly1.setText("    Week 1: Php. "+ (hours * lastFieldValue));
                        salarymonthly2.setText("    Week 2: Php. "+ (hours * lastFieldValue));
                        salarymonthly3.setText("    Week 3: Php. "+ (hours * lastFieldValue));
                        salarymonthly4.setText("    Week 4: Php. "+ (hours * lastFieldValue));
                        double monthlysalary = monthlygross - (ph + sss + pagibig + tax);
                        salarymonthlynet.setText("Monthly net salary: Php. "+ monthlysalary);
                        statutories.setText("Statutories: Php. " + (ph + sss + pagibig + tax));
                        statutories1.setText("  PAG-IBIG: Php. " + pagibig);
                        statutories2.setText("  PhilHealth: Php. " + ph);
                        statutories3.setText("  SSS: Php. " + sss);
                        statutories4.setText("  Withholding Tax: Php. " + tax);
                        frame.revalidate();
                        frame.repaint();
                        frame.setVisible(true);
                        
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payroll.csv", true))) {
                            writer.write( employeeIdField.getText() + "," + employeeNameField.getText() + "," +  hours + "," + lastFieldValue + "," + monthlygross + "," + monthlysalary + "," + (ph + sss + pagibig + tax) + "," + pagibig + "," + ph + "," + sss + "," + tax);
                            writer.newLine();
                            writer.flush();
                            JOptionPane.showMessageDialog(frame, "Data added to CSV successfully.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error occurred while adding data to CSV.");
                        }
                        
                        break;
                    }
                }
                
            } catch (IOException t) {
                t.printStackTrace();
            }
            }
            
        });
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new payroll();
            }
        });
    }
    
}
