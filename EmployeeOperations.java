import java.sql.*;
import java.util.Scanner;

public class EmployeeOperations {

    public void addEmployeeInteractive(Scanner sc) {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Department: ");
            String dept = sc.nextLine();
            System.out.print("Designation: ");
            String desig = sc.nextLine();
            System.out.print("Basic Pay: ");
            double basic = sc.nextDouble();

            String q = "INSERT INTO employee(emp_id, name, department, designation, basic_pay) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setString(4, desig);
            ps.setDouble(5, basic);
            ps.executeUpdate();
            System.out.println("✅ Employee added!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewEmployees() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String q = "SELECT emp_id, name, department, designation, basic_pay FROM employee";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            System.out.println("ID | Name | Dept | Designation | BasicPay");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %.2f\n",
                    rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
