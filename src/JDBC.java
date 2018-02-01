import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    static String database = "deanery";
    String dataFromDatabase;
    static String driverName = "com.mysql.jdbc.Driver";


    public void connect() {
        String url = "jdbc:mysql://localhost/127.0.0.1/deanery?user=kasia&passowrd=kasia";
        String userID = "somebody";
        String password = "password";
        Connection con = null;
        String query = "Select * FROM Student";

        try {
            con = DriverManager.getConnection(url, userID, password);        //załadowanie sterownika JDBC
            Class.forName(driverName);
            System.out.println("Connection with database " + database);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                showDataFromDatabase(rs);
            }

            con.close();
        }
        catch (ClassNotFoundException exc) {
            System.out.println("Problem with the controller");
        }

        catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("Login problem. \nPlease check name of database, user ID and password.");
            System.out.println("SQLException: " + exc.getMessage());
            System.out.println("SQLState: " + exc.getSQLState());
            System.out.println("VendorError: " + exc.getErrorCode());
        }
        catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("No driver class");
            System.exit(1);
        }
    }

    private void showDataFromDatabase(ResultSet rs) {
        try {
            dataFromDatabase = rs.getString(1);
            System.out.println("\n" + dataFromDatabase + " ");
            dataFromDatabase = rs.getString(2);
            System.out.println("\n" + dataFromDatabase + " ");
            dataFromDatabase = rs.getString(3);
            System.out.println("\n" + dataFromDatabase + " ");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
