import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PayrollGUI extends JFrame {

    private JTextField tfId, tfName, tfDept, tfDesg, tfBasicPay;
    private DefaultTableModel tableModel;

    public PayrollGUI() {

        setTitle("Employee Payroll System");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --------------------------- FORM ------------------------------
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        formPanel.add(new JLabel("Employee ID:"));
        tfId = new JTextField();
        formPanel.add(tfId);

        formPanel.add(new JLabel("Name:"));
        tfName = new JTextField();
        formPanel.add(tfName);

        formPanel.add(new JLabel("Department:"));
        tfDept = new JTextField();
        formPanel.add(tfDept);

        formPanel.add(new JLabel("Designation:"));
        tfDesg = new JTextField();
        formPanel.add(tfDesg);

        formPanel.add(new JLabel("Basic Pay:"));
        tfBasicPay = new JTextField();
        formPanel.add(tfBasicPay);

        add(formPanel, BorderLayout.WEST);

        // -------------------------- BUTTONS ---------------------------
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnAdd = new JButton("Add Employee");
        btnAdd.addActionListener(e -> addEmployee());
        buttonPanel.add(btnAdd);

        JButton btnUpdate = new JButton("Update Employee");
        btnUpdate.addActionListener(e -> updateEmployee());
        buttonPanel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete Employee");
        btnDelete.addActionListener(e -> deleteEmployee());
        buttonPanel.add(btnDelete);

        JButton btnView = new JButton("View Employees");
        btnView.addActionListener(e -> loadEmployees());
        buttonPanel.add(btnView);

        add(buttonPanel, BorderLayout.SOUTH);

        // --------------------------- TABLE -----------------------------
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Dept", "Designation", "Basic Pay"}, 0);
        JTable table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }


    // ===================== ADD ==========================
    private void addEmployee() {

        String sql = "INSERT INTO employee (id, name, department, designation, basic_pay) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(tfId.getText()));
            stmt.setString(2, tfName.getText());
            stmt.setString(3, tfDept.getText());
            stmt.setString(4, tfDesg.getText());
            stmt.setDouble(5, Double.parseDouble(tfBasicPay.getText()));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Added Successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    // ===================== UPDATE ==========================
    private void updateEmployee() {

        String sql = "UPDATE employee SET name=?, department=?, designation=?, basic_pay=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tfName.getText());
            stmt.setString(2, tfDept.getText());
            stmt.setString(3, tfDesg.getText());
            stmt.setDouble(4, Double.parseDouble(tfBasicPay.getText()));

            stmt.setInt(5, Integer.parseInt(tfId.getText()));

            int rows = stmt.executeUpdate();

            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Employee Updated Successfully!");
            else
                JOptionPane.showMessageDialog(this, "Employee ID Not Found!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    // ===================== DELETE ==========================
    private void deleteEmployee() {

        String sql = "DELETE FROM employee WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(tfId.getText()));

            int rows = stmt.executeUpdate();

            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
            else
                JOptionPane.showMessageDialog(this, "Employee ID Not Found!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    // ===================== SHOW TABLE ==========================
    private void loadEmployees() {

        tableModel.setRowCount(0); // Clear table

        String sql = "SELECT * FROM employee";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDouble("basic_pay")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    // ====================== MAIN ============================
    public static void main(String[] args) {
        new PayrollGUI();
    }
}
