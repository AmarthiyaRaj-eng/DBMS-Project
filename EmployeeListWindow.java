import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EmployeeListWindow extends JFrame {

    JTable table;
    DefaultTableModel model;

    public EmployeeListWindow() {
        setTitle("Employee List");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        table = new JTable(model);

        // Add column names
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Department");
        model.addColumn("Designation");
        model.addColumn("Basic Pay");

        loadData();

        JScrollPane pane = new JScrollPane(table);
        add(pane);
    }

    private void loadData() {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employee");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDouble("basic_pay")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from DB");
        }
    }
}
