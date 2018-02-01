import java.sql.Connection;
import java.sql.Statement;

public class InstructionsSQL {
    Statement stmt;

    String[] createTable = {"CREATE TABLE A (ID INTEGER, NAME CHAR(30))", "CREATE TABLE B (ID INTEGER, ADR CHAR(30)"};

/*    for (int i = 0; i< createTable.length; i++) {
        stmt.executeUpdate(createTable[i]);
    }
    stmt.executeUpdate("INSERT INTO A VALUES(1, 'Pies");*/
}
