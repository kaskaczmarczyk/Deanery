import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Student extends Person {

    int groupNumber;
    int ECTS;
    String state;

    public Student(String name, String surname, int groupNumber, int ECTS, String state) {
        super(name, surname);
        this.groupNumber = groupNumber;
        this.ECTS = ECTS;
        this.state = state;
    }

    public boolean passSemester(Student student) {
        if (this.ECTS >= Deanery.minECTS) {
            return true;
        }
        else {
            return false;
        }
    }


    public void addToDatabase() {
        Connection con = null;
        Statement stat = null;
        try {
            Class.forName(Deanery.driverName);
            con = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);

            stat = con.createStatement();
            String addSQL = "INSERT INTO " + Deanery.database + " (IDSTUDENT, NAME, SURNAME, IDGROUP, ECTS, STATE) " +
                    "VALUES ("
                    + null + ", "
                    + "'" + this.name + "'"
                    + "'" + this.surname + "'"
                    + "'" + this.groupNumber + "'"
                    + "'" + this.ECTS + "'"
                    + "'" + this.state + "'"
                    + " );";
            stat.executeUpdate(addSQL);
            stat.close();
            con.close();
            System.out.println("Command: \n" + addSQL + "\n done.");
        } catch (Exception e) {
            System.out.println("You cann't add data " + e.getMessage());
        }
    }


    public void delete(Student student) {

    }


    public void edit(Student student) {

    }


}
