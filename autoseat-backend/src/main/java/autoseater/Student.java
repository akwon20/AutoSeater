package autoseater;

public class Student {

    private int idNum;
    private String name;
    private int seatNum;

    public Student() {
        this.idNum = 0;
        this.name = "null";
        this.seatNum = -1;
    }

    public Student(int idNum, String name) {
        this.idNum = idNum;
        this.name = name;
        this.seatNum = -1;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getIdNum() {
        return this.idNum;
    }

    public String getName() {
        return this.name;
    }

    public int getSeatNum() {
        return this.seatNum;
    }
}