package autoseater;

public class Student {

    private int idNum;
    private String name;

    public Student() {
        this.idNum = 0;
        this.name = "null";
    }

    public Student(int idNum, String name) {
        this.idNum = idNum;
        this.name = name;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdNum() {
        return this.idNum;
    }

    public String getName() {
        return this.name;
    }
}