import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculator {
    private JTextField txtStudentName;
    private JButton calculateButton;
    private JTextField txtM1;
    private JTextField txtM2;
    private JTextField txtTA;
    private JTextField txtTotalAverage;
    private JPanel Main;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GradeCalculator");
        frame.setContentPane(new GradeCalculator().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public GradeCalculator() {
    calculateButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int M1,M2,TA,TotalAverage;
            double Avg;

            M1 = Integer.parseInt(txtM1.getText());
            M2 = Integer.parseInt(txtM2.getText());
            TA = Integer.parseInt(txtTA.getText());

            TotalAverage = M1 + M2 + TA;

            txtTotalAverage.setText(String.valueOf(TotalAverage));

        }
    });
}
}
