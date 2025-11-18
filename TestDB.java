import java.sql.*;
public class TestDB {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Try to connect
            Connection c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/payroll_db", "root", "1234");

            System.out.println("✅ Connected!");
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
