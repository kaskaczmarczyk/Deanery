import java.sql.*;

public class Subject {
    static int idSubject;
    String nameOfSubject;
    static int numberOfRows;
    static int numberOfSubjects;

    public Subject(String nameOfSubject) {
        this.nameOfSubject = nameOfSubject;
        this.idSubject = getIdSubject();
    }

    public static int checkNumberOfSubject() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM subject";
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
            System.err.println("Error when reading the number of subjects");
            exc.printStackTrace();
        }
        return numberOfRows;
    }

    public void addSubjectToDatabase() {
        Connection connection = null;
        String sql = "INSERT INTO subject(NAMEOFSUBJECT) VALUES(?);";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepstm.setString(1, this.nameOfSubject);
            prepstm.execute();
            ResultSet rs = prepstm.getGeneratedKeys();
            if(rs.next())
            {
                int lastId = rs.getInt(1);
                idSubject = lastId;
            }
            numberOfSubjects = checkNumberOfSubject();
            System.out.println("You added a new subject to database. Now there are "+ numberOfSubjects + " subjects in the database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the subject");
            exc.printStackTrace();
        }
    }

    public static void deleteSubjectFromDatabase(int idDeletedSubject) {
        Connection connection = null;
        String sql = "DELETE FROM subject WHERE IDSUBJECT = ?;";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println("Problem with connecting to the database");
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idDeletedSubject);
            int i = prepstm.executeUpdate();
            if (i > 0) {
                numberOfSubjects = checkNumberOfSubject();
                System.out.println("You deleted a subject from database. Now there are "+ numberOfSubjects+ " subjects in the database.");
            }
            else {
                System.out.println("Sorry, a subject with the given id number does not exist");
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when deleting student");
            exc.printStackTrace();
        }
    }

    public int getIdSubject() {
        return idSubject;
    }
}
