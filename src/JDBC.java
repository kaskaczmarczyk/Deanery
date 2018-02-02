public class JDBC {

    public static void main(String[] args) {
/*
        System.out.println("What you want do do?");
        System.out.println("if you want to add a new student to the database, press AS");
*/

        Student newStudent = new Student("DD", "CC", 6, 9, "nzal");
        newStudent.addStudentToDatabase();
    }
}
