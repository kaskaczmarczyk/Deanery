import java.sql.*;

public class Lecturer extends Person {
    static int idLecturer;
    String nameOfSubject;
    static int numberOfRows;
    static int numberOfLecturer;

    public Lecturer(String name, String surname, String nameOfSubject) {
        super(name, surname);
        this.nameOfSubject = nameOfSubject;
        this.idLecturer = getIdLecturer();
    }

    public static int checkNumberOfLecturer() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM lecturer";
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
            System.err.println("Error when reading the number of lecturer");
            exc.printStackTrace();
        }
        return numberOfRows;
    }

    public void addLecturerToDatabase() {
        Connection connection = null;
        String sql = "INSERT INTO lecturer(NAME, SURNAME) VALUES(?, ?);";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepstm.setString(1, this.name);
            prepstm.setString(2, this.surname);
            prepstm.execute();
            ResultSet rs = prepstm.getGeneratedKeys();
            if(rs.next())
            {
                int lastId = rs.getInt(1);
                idLecturer = lastId;
            }
            numberOfLecturer = checkNumberOfLecturer();
            System.out.println("You added a new lecturer to database. Now there are "+ numberOfLecturer + " lecturer in the database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the lecturer");
            exc.printStackTrace();
        }
    }

    public static void deleteLecturerFromDatabase(int idDeletedLecturer) {
            Connection connection = null;
            String sql = "DELETE FROM lecturer WHERE IDLECTURER = ?;";
            try {
                connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
            } catch (SQLException exc) {
                System.err.println("Problem with connecting to the database");
                exc.printStackTrace();
            }
            try {
                PreparedStatement prepstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prepstm.setInt(1, idDeletedLecturer);
                int i = prepstm.executeUpdate();
                if (i > 0) {
                    numberOfLecturer= checkNumberOfLecturer();
                    System.out.println("You deleted a lecturer from database. Now there are "+ numberOfLecturer + " lecturers in the database.");
                }
                else {
                    System.out.println("Sorry, a lecturer with the given id number does not exist");
                }
                connection.close();
            } catch (SQLException exc) {
                System.err.println("Error when deleting lecturer");
                exc.printStackTrace();
            }
    }

    public int getIdLecturer() {
        return idLecturer;
    }
}
