import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class RegistrationForm extends JFrame implements ActionListener {

  private JTextField nameField, regNoField;
  private JLabel name, regNo, gender, check, city, messageLabel;
  private JRadioButton radioButtonMale, radioButtonFemale, radioButtonOthers;
  private ButtonGroup bg;
  private JCheckBox checkbox;
  private JList list;
  private JButton insertDB, updateDB;
  private JPanel formPanel;
  private Connection connection;
  private JCheckBox checkbox2, checkbox3;

  public RegistrationForm() {
    // Labels
    name = new JLabel("Name");
    regNo = new JLabel("Registration No.");
    gender = new JLabel("Select Gender");
    check = new JLabel("Select your preferred Languages");
    city = new JLabel("Select city");
    // textfields
    nameField = new JTextField("Enter your Name", 5);
    regNoField = new JTextField("Enter your Reg. No.", 5);
    // RadioButtons for gender
    radioButtonMale = new JRadioButton("Male");
    radioButtonFemale = new JRadioButton("Female");
    radioButtonOthers = new JRadioButton("Others");
    bg = new ButtonGroup();
    bg.add(radioButtonMale);
    bg.add(radioButtonFemale);
    bg.add(radioButtonOthers);
    formPanel = new JPanel();
    formPanel.setSize(300, 400);
    formPanel.setVisible(true);
    // Checkbox
    checkbox = new JCheckBox("C++");
    checkbox2 = new JCheckBox("JAVA");
    checkbox3 = new JCheckBox("Python");
    formPanel.add(name);
    formPanel.add(nameField);
    formPanel.add(regNo);
    formPanel.add(regNoField);
    formPanel.add(gender);
    JPanel radioPanel = new JPanel();
    radioPanel.add(radioButtonMale);
    radioPanel.add(radioButtonFemale);
    radioPanel.add(radioButtonOthers);
    formPanel.add(radioPanel);
    formPanel.add(check);
    JPanel checkJPanel = new JPanel();
    checkJPanel.setLayout(new FlowLayout());
    checkJPanel.add(checkbox);
    checkJPanel.add(checkbox2);
    checkJPanel.add(checkbox3);
    formPanel.add(checkJPanel);
    String arr[] = { "Varanasi", "Gorakpur", "Allahabad", "Lucknow" };
    list = new JList(arr);
    formPanel.add(city);
    formPanel.add(list);
    insertDB = new JButton("Submit");
    updateDB = new JButton("Update");
    insertDB.addActionListener(this);
    updateDB.addActionListener(this);
    formPanel.setLayout(new GridLayout(6, 2, 10, 10));
    formPanel.add(insertDB);
    formPanel.add(updateDB);
    messageLabel = new JLabel("");
    add(formPanel);
    add(messageLabel, BorderLayout.EAST);
    setLayout(new BorderLayout());
    setSize(500, 500);
    setVisible(true);
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection =
        DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/mydatabase",
          "root",
          ""
        );
    } catch (Exception ex) {
      messageLabel.setText("Error connecting to database: " + ex.getMessage());
      System.out.println(ex.getMessage());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      Statement statement = connection.createStatement();
      String name = nameField.getText();
      String gender;
      if (radioButtonMale.isSelected()) {
        gender = "Male";
      } else if (radioButtonFemale.isSelected()) {
        gender = "Female";
      } else {
        gender = "Other";
      }
      String city = list.getSelectedValue().toString();
      String favSub = "";
      if (checkbox.isSelected()) {
        favSub += "C++";
      }
      if (checkbox2.isSelected()) {
        favSub += ",JAVA";
      }
      if (checkbox3.isSelected()) {
        favSub += ",Python";
      }
      int id = Integer.parseInt(regNoField.getText());
      if (e.getSource() == insertDB) {
        statement.executeUpdate(
          "INSERT INTO regform (name, gender, id, city, favSub) VALUES ('" +
          name +
          "', '" +
          gender +
          "', '" +
          id +
          "', '" +
          city +
          "', '" +
          favSub +
          "')"
        );
        messageLabel.setText("Record added successfully");
        System.out.println("record added");
      } else if (e.getSource() == updateDB) {
        statement.executeUpdate(
          "UPDATE mytable SET name = '" +
          name +
          "', gender = " +
          gender +
          " WHERE id = " +
          id
        );
        messageLabel.setText("Record updated successfully");
      }
    } catch (Exception ex) {
      messageLabel.setText("Error performing operation: " + ex.getMessage());
      System.out.println("record not added" + ex.getMessage());
    }
  }

  public static void main(String[] args) {
    new RegistrationForm();
  }
}
