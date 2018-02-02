import java.sql.*;


public class Student extends Person {

    int idStudent;
    int groupNumber;
    int ects;
    String state;

    public Student(String name, String surname, int groupNumber, int ects, String state) {
        super(name, surname);
        this.groupNumber = groupNumber;
        this.ects = ects;
        this.state = state;
        this.idStudent = getIdStudent();
    }

    public boolean passSemester(Student student) {
        if (this.ects >= Deanery.minECTS) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addStudentToDatabase() {
        Connection connection = null;
        String sql = "INSERT INTO student(NAME, SURNAME, IDGROUP, ECTS, STATE) VALUES(?, ?, ?, ?, ?);";
        int id = 0;
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
            prepstm.setInt(3, this.groupNumber);
            prepstm.setInt(4, this.ects);
            prepstm.setString(5, this.state);
            prepstm.execute();
            ResultSet rs = prepstm.getGeneratedKeys();
            if(rs.next())
            {
                int lastId = rs.getInt(1);
                idStudent = lastId;
            }
            System.out.println("You added a new student to database. Now there are "+ idStudent + " students in the database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the student");
            exc.printStackTrace();
        }
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void deleteStudentFromDatabase() {
        int chosenRecord = 16;
        Connection connection = null;
        try {
            Class.forName(Deanery.driverName);
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (Exception e) {
            System.out.println("Problem with connecting to the database " + e.getMessage());
        }
        try {
        Statement stmt = connection.createStatement();
        String surnameOfDeletedStudent = "Zabranny";
        String sql = "DELETE FROM student WHERE surname = '" + surnameOfDeletedStudent + "'";
        int deleteCount = stmt.executeUpdate(sql);
        sql = "DELETE FROM student WHERE surname = ?";
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setInt(1, chosenRecord);
            deleteCount = prepStmt.executeUpdate();
        } catch (SQLException exc) {
            System.err.println("Error when deleting student");
        }
    }


    public void changeStudentData() {
        Connection connection = null;

    }


}
