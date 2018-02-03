import java.sql.*;

public class Student extends Person {

    static int idStudent;
    static int numberOfStudents;
    static int numberOfRows;
    int groupNumber;
    int ects;
    String state;

    public Student(String name, String surname, int groupNumber, int ects, String state) {
        super(name, surname);
        this.groupNumber = groupNumber;
        this.ects = ects;
        this.state = state;
    }

    public boolean passSemester(Student student) {
        if (this.ects >= Deanery.minECTS) {
            return true;
        }
        else {
            return false;
        }
    }

    public static int checkNumberOfStudents() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM student";
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
            System.err.println("Error when reading the number of students.");
            exc.printStackTrace();
        }
        return numberOfRows;
    }

    public void addStudentToDatabase() {
        Connection connection = null;
        String sql = "INSERT INTO student(NAME, SURNAME, IDGROUP, ECTS, STATE) VALUES(?, ?, ?, ?, ?);";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setString(1, this.name);
            prepstm.setString(2, this.surname);
            prepstm.setInt(3, this.groupNumber);
            prepstm.setInt(4, this.ects);
            prepstm.setString(5, this.state);
            prepstm.execute();
//            numberOfStudents = checkNumberOfStudents();
            System.out.println("You added a new student to database. Now there are "+ numberOfStudents + " students in the database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the student.");
            exc.printStackTrace();
        }
    }

    public static void deleteStudentFromDatabase(int idDeletedStudent) {
            Connection connection = null;
            String sql = "DELETE FROM student WHERE IDSTUDENT = ?;";
            try {
                connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
            } catch (SQLException exc) {
                System.err.println("Problem with connecting to the database.");
                exc.printStackTrace();
            }
            try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idDeletedStudent);
            int i = prepstm.executeUpdate();
            if (i > 0) {
                numberOfStudents = checkNumberOfStudents();
                System.out.println("You deleted a student from database. Now there are "+ numberOfStudents + " students in the database.");
            }
            else {
                System.out.println("Sorry, a student with the given id number does not exist");
            }
            connection.close();
            } catch (SQLException exc) {
                System.err.println("Error when deleting student.");
                exc.printStackTrace();
            }
    }

}
