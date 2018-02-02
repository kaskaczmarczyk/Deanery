import java.sql.*;

public class Deanery {

    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String database = "deanery";
    public static final String url = "jdbc:mysql://localhost/deanery";
    static String userID = "root";
    static String password = "";
    public static final int minECTS = 20;

    Connection connection;
    Statement stmt;

    public Deanery(){
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException exc) {
            System.err.println("No driver JDBC");
            exc.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, userID, password);
            stmt = connection.createStatement();
        } catch (SQLException exc) {
            System.err.println("Problem with opening the connection");
            exc.printStackTrace();
        }

        connect(database);
    }

    public boolean createTables() {
        String createStudent = "CREATE TABLE IF NOT EXISTS STUDENT (IDSTUDENT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME varchar(255), SURNAME varchar(255), IDGROUP int, ECTS int, STATE varchar)";
        try {
            stmt.execute(createStudent);
        } catch (SQLException exc) {
            System.err.println("Error when creating the table");
            exc.printStackTrace();
            return false;
        }
        return true;
    }

/*

    public boolean insertLecturer(Lecturer lecturer) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into Lecturer values(?,?,?,?);");
            prepStmt.setInt(1, IDLECTURER);
            prepStmt.setString(2, Lecturer.name);
            prepStmt.setString(3, Student.surname);
            prepStmt.setString(4, Student.nameOfSubject);
        } catch (SQLException exc) {
            System.err.println("Error when inserting the lecturer");
            exc.printStackTrace();
            return false;
        }
        return true;
    }*/

    public void showStudent() {
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Problem closing the connection");
            exc.printStackTrace();
        }
    }

}