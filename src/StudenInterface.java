import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudenInterface extends JFrame {
    private Connection connection;
    private JTextField jTextFieldName;
    private JTextField jTextFieldroll;
    private JTextField jTextFieldcl;
    private JTextField jTextFieldgrade;

    StudenInterface() {

        connection = DatabaseConnection.getConnection();
        if (connection != null) {
            crateDB();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database");
            System.exit(1);
        }
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

        JLabel title=new JLabel("Student Management System");
        title.setBounds(85,20,600,50);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        panel.add(title);

        ImageIcon ic = new ImageIcon("rm_bg.png");
        JLabel image = new JLabel(ic);
        image.setBounds(-120, -10, 500, 450);
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


        JButton add = new JButton("ADD a Student");
        add.setBounds(50, 390, 190, 40);
        add.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        add.setFocusable(false);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addStudent();
            }
        });
        panel.add(add);

        JButton remove = new JButton("Remove a Student");
        remove.setBounds(310, 390, 190, 40);
        remove.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        remove.setFocusable(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                new remove();
                setVisible(false);
            }
        });
        panel.add(remove);

        JButton serch = new JButton("Search Student");
        serch.setBounds(50, 470, 190, 40);
        serch.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        serch.setFocusable(false);
        serch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                serchStudent();
            }
        });
        panel.add(serch);

        JButton disply = new JButton("Display All Student");
        disply.setBounds(310, 470, 190, 40);
        disply.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        disply.setFocusable(false);
        disply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                displyStudent();
            }
        });
        panel.add(disply);

    }

        private void crateDB() {
            try {
                String query = "CREATE TABLE IF NOT EXISTS student (name VARCHAR(300), rollno int PRIMARY KEY, class VARCHAR(22), grade VARCHAR(22))";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private void addStudent () {
            try {

                String Name = jTextFieldName.getText();
                String rollNo = jTextFieldroll.getText();
                String studentClass = jTextFieldcl.getText();
                String Grade = jTextFieldgrade.getText();

                String query = "INSERT INTO student (name, rollno, class, grade) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Name);
                preparedStatement.setString(2, rollNo);
                preparedStatement.setString(3, studentClass);
                preparedStatement.setString(4, Grade);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                 clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        private void serchStudent() {
            try {
                String rollNo = jTextFieldroll.getText();
                String query = "SELECT * FROM student WHERE rollno = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, rollNo);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    jTextFieldName.setText(resultSet.getString("name"));
                    jTextFieldcl.setText(resultSet.getString("class"));
                    jTextFieldgrade.setText(resultSet.getString("grade"));
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with the given Roll No.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void displyStudent() {
            try {
                String query = "SELECT * FROM student";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                StringBuilder sb = new StringBuilder();
                while (resultSet.next()) {
                    sb.append("Name: ").append(resultSet.getString("name")).append(", ");
                    sb.append("Roll No: ").append(resultSet.getString("rollno")).append(", ");
                    sb.append("Class: ").append(resultSet.getString("class")).append(", ");
                    sb.append("Grade: ").append(resultSet.getString("grade")).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    private void clearFields() {
        jTextFieldName.setText("");
        jTextFieldroll.setText("");
        jTextFieldcl.setText("");
        jTextFieldgrade.setText("");
    }


    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudenInterface();
            }
        });
    }
}