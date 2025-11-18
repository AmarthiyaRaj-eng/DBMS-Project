public class TestConnection {
    public static void main(String[] args) {
        var con = DatabaseConnection.getConnection();
        if (con == null)
            System.out.println("Still null — connection failing");
        else
            System.out.println("Connected successfully!");
    }
}
