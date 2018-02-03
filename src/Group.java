import java.sql.*;

public class Group {
    static int idGroup;
    int idLecturer;
    static int numberOfRows;
    static int numberOfGroups;

    public Group(int idLecturer) {
        this.idLecturer = idLecturer;
        this.idGroup = getIdGroup();
    }

    public static int checkNumberOfGroup() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM grouplist";
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
            System.err.println("Error when reading the number of group");
            exc.printStackTrace();
        }
        return numberOfRows;
    }

    public void addGroupToDatabase() {
        Connection connection = null;
        String sql = "INSERT INTO grouplist(IDLECTURER) VALUES(?);";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println();
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepstm.setInt(1, this.idLecturer);
            prepstm.execute();
            ResultSet rs = prepstm.getGeneratedKeys();
            if(rs.next())
            {
                int lastId = rs.getInt(1);
                idGroup = lastId;
            }
            numberOfGroups = checkNumberOfGroup();
            System.out.println("You added a new group to database. Now there are "+ numberOfGroups + " groups in the database.");
        } catch (SQLException exc) {
            System.err.println("Error when inserting the group");
            exc.printStackTrace();
        }
    }

    public static void deleteGroupFromDatabase(int idDeletedGroup) {
        Connection connection = null;
        String sql = "DELETE FROM grouplist WHERE IDGROUP = ?;";
        try {
            connection = DriverManager.getConnection(Deanery.url, Deanery.userID, Deanery.password);
        } catch (SQLException exc) {
            System.err.println("Problem with connecting to the database");
            exc.printStackTrace();
        }
        try {
            PreparedStatement prepstm = connection.prepareStatement(sql);
            prepstm.setInt(1, idDeletedGroup);
            int i = prepstm.executeUpdate();
            if (i > 0) {
                numberOfGroups = checkNumberOfGroup();
                System.out.println("You deleted a group from database. Now there are "+ numberOfGroups+ " groups in the database.");
            }
            else {
                System.out.println("Sorry, a group with the given id number does not exist");
            }
            connection.close();
        } catch (SQLException exc) {
            System.err.println("Error when deleting student");
            exc.printStackTrace();
        }
    }

    public int getIdGroup() {
        return idGroup;
    }
}
