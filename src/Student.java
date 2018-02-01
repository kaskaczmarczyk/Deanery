import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Student extends Person implements ManagePerson {

    int groupNumber;

    public Student(String name, String surname, int groupNumber) {
        super(name, surname);
        this.groupNumber = groupNumber;
    }

    @Override
    public void add(Person person) {
        Connection con = null;
        Statement stat = null;
        try {
            Class.forName(JDBC.driverName);
            con = DriverManager.getConnection("com.mysql.jdbc.Driver" + JDBC.database + ".db");

            stat = con.createStatement();
            String addSQL = "INSERT INTO " + JDBC.database + " (IDSTUDENT, NAME, SURNAME, IDGROUP) " + "VALUES ("
                    + null + ", "
                    + "'" + this.name + "'"
                    + "'" + this.surname + "'"
                    + "'" + this.groupNumber + "'"
                    + " );";
            stat.executeUpdate(addSQL);
            stat.close();
            con.close();
            System.out.println("Command: \n" + addSQL + "\n done.");
        } catch (Exception e) {
            System.out.println("You cann't add data " + e.getMessage());
        }
    }

    @Override
    public void delete(Person person) {

    }

    @Override
    public void edit(Person person) {

    }


}
