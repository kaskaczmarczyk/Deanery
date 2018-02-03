import java.sql.*;

public class Changer {

    String nameOfTable;
    int numberOfRows;
    String nameOfIndeks;
    int numberOfElements;

    public int checkNumberOfElements() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM '" + nameOfTable + "'";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                numberOfRows = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException exc) {
            System.err.println("Error when reading the number of " + nameOfTable + "s.");
            exc.printStackTrace();
        }
        return numberOfRows;
    }

    public void deleteFromDatabase(int idDeleted) {
        Connection connection = null;
        String sql = "DELETE FROM '" + nameOfTable + "' WHERE '" + nameOfIndeks + "' = ?;";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println("Problem with connecting to the database.");
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idDeleted);
            int i = prepstm.executeUpdate();
            if (i > 0) {
                numberOfElements = checkNumberOfElements();
                System.out.println("You deleted a " + nameOfTable +" from database. Now there are "+ numberOfElements + " " + nameOfTable + "s in the database.");
            }
            else {
                System.out.println("Sorry, a " + nameOfTable + " with the given id number does not exist");
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when deleting " + nameOfTable + ".");
            exc.printStackTrace();
        }
    }
}