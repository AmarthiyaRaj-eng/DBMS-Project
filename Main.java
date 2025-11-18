import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            EmployeeOperations ops = new EmployeeOperations();
            SalaryCalculator calc = new SalaryCalculator();
            OUTER:
            while (true) {
                System.out.println("\n--- Employee Payroll System ---");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Calculate Salary");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1 -> ops.addEmployeeInteractive(sc);
                    case 2 -> ops.viewEmployees();
                    case 3 -> calc.calculateSalaryInteractive(sc);
                    default -> {
                        break OUTER;
                    }
                }
            }
        }
    }
}
