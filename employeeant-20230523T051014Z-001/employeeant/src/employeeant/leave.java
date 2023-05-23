/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package employeeant;

import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jpaha
 */
public class leave extends JFrame {

    private JFrame frame;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField remainingLeaveField;
    private JComboBox<String> leaveTypeComboBox;
    String csvFile = "leave.csv";
    String sick;
    String vl;
    String emer;

    String day;
    String day1;
    double rem;

    int sickValue;
    int vlValue;
    int emerValue;
    SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd");
    String leaveType;

    String endDate;
    String startDate;

    public leave() {
        frame = new JFrame("Leave Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setSize(600, 700);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(11, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        JLabel leaveTypeLabel = new JLabel("Leave Type:");
        JComboBox<String> leaveTypeComboBox = new JComboBox<>(new String[]{"Sick", "Emergency", "Vacation"});
        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateField = new JTextField();
        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateField = new JTextField();
        JLabel remainingLeaveLabel = new JLabel("Remaining Leave:");
        JTextField remainingLeaveField = new JTextField();
        remainingLeaveField.setEditable(false);
        JLabel idNumberLabel = new JLabel("ID Number:");
        JTextField idNumberField = new JTextField();

        inputPanel.add(idNumberLabel);
        inputPanel.add(idNumberField);
        inputPanel.add(leaveTypeLabel);
        inputPanel.add(leaveTypeComboBox);
        inputPanel.add(startDateLabel);
        inputPanel.add(startDateField);
        inputPanel.add(endDateLabel);
        inputPanel.add(endDateField);
        inputPanel.add(remainingLeaveLabel);
        inputPanel.add(remainingLeaveField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0, 153, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                leaveType = leaveTypeComboBox.getSelectedItem().toString();
                startDate = startDateField.getText();
                endDate = endDateField.getText();

                try {
                    // Parse the date string using the input format
                    Date dateStart = inputFormat.parse(startDate);
                    // Format the parsed date to extract the day as a string
                    day = outputFormat.format(dateStart);
                    // Parse the date string using the input format
                    Date dateEnd = inputFormat.parse(endDate);
                    // Format the parsed date to extract the day as a string
                    day1 = outputFormat.format(dateEnd);
                } catch (ParseException r) {
                    r.printStackTrace();
                }
                double sickValue = 0.0;
                double vlValue = 0.0;
                double emerValue = 0.0;
                String idNumber = idNumberField.getText();
                int day2 = (int) Double.parseDouble(day1) - (int) Double.parseDouble(day);
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String line;
                    String lastLine = null;
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        lastLine = line;
                    }

                    if (lastLine != null) {
                        String[] fields = lastLine.split(",");
                        String employeeNumber = fields[0].trim();
                        System.out.println(employeeNumber);

                        if (employeeNumber.equals(idNumber)) {
                            sick = fields[1].trim();
                            sickValue = Double.parseDouble(sick);

                            vl = fields[2].trim();
                            vlValue = Double.parseDouble(vl);

                            emer = fields[3].trim();
                            emerValue = Double.parseDouble(emer);

                            if (leaveTypeComboBox.getSelectedItem().toString().equals("Sick")) {
                                sickValue = sickValue - day2;
                                System.out.println(sickValue);
                                rem = sickValue;
                            } else if (leaveTypeComboBox.getSelectedItem().toString().equals("Emergency")) {
                                vlValue = vlValue - day2;
                                System.out.println(vlValue);
                                rem = vlValue;
                            } else {
                                emerValue = emerValue - day2;
                                rem = emerValue;
                            }
                        }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("leave.csv", true)))) {
                        writer.write(idNumber + "," + sickValue + "," + vlValue + "," + emerValue + "," + leaveTypeComboBox.getSelectedItem().toString() + "," + startDate + "," + endDate);
                        writer.newLine();
                        writer.flush();
                        JOptionPane.showMessageDialog(frame, "Data added to CSV successfully.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error occurred while adding data to CSV.");
                    }

                } catch (IOException t) {
                    t.printStackTrace();
                }

                double remainingLeave = rem; // Example remaining leave
                remainingLeaveField.setText(Double.toString(remainingLeave));
            }

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new leave();
            }
        });
    }
}
