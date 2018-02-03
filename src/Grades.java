import java.sql.*;

public class Grades {

    int idGrade;
    int idStudent;
    int idSubject;
    double grade;


    public Grades(int grade) {
        this.grade = grade;
    }

    public static void checkGrade(double grade) {
        Double[] gradesTab = {2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
        for (int i = 0; i < gradesTab.length; i++) {
            if (grade == gradesTab[i]) {
                System.out.println("Grade " + grade + " is correct.");
                break;
            } else {
                continue;
            }
        }
    }

    public static void addGradeToDatabase(int idStudent, int idSubject, double grade) {
        Connection connection = null;
        String sql = "INSERT INTO grades(IDSTUDENT, IDSUBJECT, GRADE) VALUES(?, ?, ?);";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idStudent);
            prepstm.setInt(2, idSubject);
            prepstm.setDouble(3, grade);
            prepstm.executeUpdate();
            System.out.println("You added a new grade to database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the grade");
            exc.printStackTrace();
        }
    }

    public static void deleteGradeFromDatabase(int idGrade) {
        Connection connection = null;
        String sql = "DELETE FROM grades WHERE IDGRADE = ?;";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println("Problem with connecting to the database");
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idGrade);
            prepstm.executeUpdate();
            int i = prepstm.executeUpdate();
            if (i > 0) {
                System.out.println("You deleted a grade from database.");
            }
            else {
                System.out.println("Sorry, a grade with the given id number does not exist");
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when deleting grade");
            exc.printStackTrace();
        }
    }
}
