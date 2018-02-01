public class Lecturer extends Person {
    String nameOfSubject;

    public Lecturer(String name, String surname, String nameOfSubject) {
        super(name, surname);
        this.nameOfSubject = nameOfSubject;
    }
}
