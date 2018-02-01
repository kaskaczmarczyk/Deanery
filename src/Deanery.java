import java.sql.*;

public class Deanery {

    public static String driverName = "com.mysql.jdbc.Driver";
    public static String database = "deanery";
    static String url = "jdbc:mysql://localhost/deanery";
    static String userID = "root";
    static String password = "";
    public static final int minECTS = 20;

    public static void main(String[] args) {

        try {
            Connection connection = connect(Deanery.database);
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM Student";
            ResultSet rs = stmt.executeQuery(SQL);
            System.out.printf("%-2s %-11s %-19s %-5s %-5s %-8s %n", "ID", "NAME", "SURNAME", "GROUP", "ECTS","STATE");

            while(rs.next()) {
                int idStudent = rs.getInt("IDSTUDENT");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                int idGroup = rs.getInt("IDGROUP");
                int ects = rs.getInt("ECTS");
                String state = rs.getString("STATE");

                System.out.printf("%-3d", idStudent);
                System.out.printf("%-12s", name);
                System.out.printf("%-20s", surname);
                System.out.printf("%-6d", idGroup);
                System.out.printf("%-6d", ects);
                System.out.printf("%-8s %n", state);

            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }
    public static Connection connect(String database) {
        Connection connection = null;
        try {
            Class.forName(Deanery.driverName);
            connection = DriverManager.getConnection(url, userID, password);              //załadowanie sterownika JDBC
            System.out.println("Connection with database: " + database);
        }
        catch (Exception exc) {
            System.out.println("Error in connection with the database: \n" + exc.getMessage());
            return null;
        }
        return connection;
    }

}