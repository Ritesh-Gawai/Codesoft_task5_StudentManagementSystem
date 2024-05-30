import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class remove extends JFrame {
    private JTextField jTextFieldName, jTextFieldroll,
            jTextFieldcl, jTextFieldgrade;
    private Connection connection;

    remove() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        //panel.setVisible(true);
        this.add(panel);


        this.setSize(600, 600);
        //this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Student Manegment  System");
        this.setLocation(250, 60);

        JLabel title = new JLabel("Enter Required Details");
        title.setBounds(100, 30, 350, 30);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel.add(title);

        ImageIcon ic = new ImageIcon("img.png");
        JLabel image = new JLabel(ic);
        image.setBounds(-120, 10, 500, 450);
        image.setBackground(Color.white);
        panel.add(image);

        JLabel name = new JLabel("Name");
        name.setBounds(260, 100, 150, 30);
        name.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel.add(name);

        jTextFieldName = new JTextField();
        jTextFieldName.setBounds(370, 100, 160, 30);
        panel.add(jTextFieldName);

        JLabel rollnumber = new JLabel("Roll No");
        rollnumber.setBounds(260, 160, 150, 30);
        rollnumber.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel.add(rollnumber);

        jTextFieldroll = new JTextField();
        jTextFieldroll.setBounds(370, 160, 160, 30);
        panel.add(jTextFieldroll);


        JLabel cl = new JLabel("Class");
        cl.setBounds(260, 220, 150, 30);
        cl.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel.add(cl);


        jTextFieldcl = new JTextField();
        jTextFieldcl.setBounds(370, 220, 160, 30);
        panel.add(jTextFieldcl);


        JLabel grade = new JLabel("Grade");
        grade.setBounds(260, 280, 150, 30);
        grade.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel.add(grade);


        jTextFieldgrade = new JTextField();
        jTextFieldgrade.setBounds(370, 280, 160, 30);
        panel.add(jTextFieldgrade);

        JButton remove = new JButton("Remove Student");
        remove.setBounds(310, 390, 190, 40);
        remove.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        remove.setFocusable(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeStudent();
            }
        });
        panel.add(remove);

        JButton back = new JButton("Back");
        back.setBounds(70, 390, 190, 40);
        back.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        back.setFocusable(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new StudenInterface();
                setVisible(false);
            }
        });
        panel.add(back);
    }

    private void removeStudent() {
        String rollNumber = jTextFieldroll.getText().trim();
        String Name=jTextFieldName.getText().trim();
        String cl=jTextFieldcl.getText().trim();
        String Grade=jTextFieldgrade.getText().trim();
        if (Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Name.!");
            return;
        }
        if (cl.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Class.");
            return;
        }
        if (Grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade.");
            return;
        }
        if (rollNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Roll No.");
            return;
        }

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database");
            return;
        }

        try {
            String query = "DELETE FROM student WHERE rollno = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rollNumber);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Student with Roll No. " + rollNumber + " removed successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "No student found with Roll No. " + rollNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing student: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        jTextFieldName.setText("");
        jTextFieldroll.setText("");
        jTextFieldcl.setText("");
        jTextFieldgrade.setText("");
    }


}