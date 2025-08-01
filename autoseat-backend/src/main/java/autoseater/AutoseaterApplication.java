package autoseater;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.variables.IntVar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoseaterApplication {

    private Assigner assigner;

    private int countRows, countCols;
    private IntVar[][] seats;
    private List<Student> students;
    private List<String[]> rules;
    private List<Integer[]> pairsForbidden_id;
    private List<Integer[]> pairsAllowed_id;

    public AutoseaterApplication() {
        assigner = new Assigner();
        countRows = 0;
        countCols = 0;
        seats = null;
        students = new ArrayList<Student>();
        rules = new ArrayList<String[]>();
        pairsForbidden_id = new ArrayList<Integer[]>();
        pairsAllowed_id = new ArrayList<Integer[]>();
    }

    public String getGreeting() {
        return "Hello World!";
    }

    public void setCountRows(int count) {
        countRows = count;
    }

    public void setCountCols(int count) {
        countCols = count;
    }

    public void updateStudentList(String[] names) {
        if (!students.isEmpty()) {
            students.clear();
        }

        for (int i = 0; i < names.length; i++) {
            students.add(new Student(i, names[i]));
        }
    }

    public void addRule(String name1, String condition, String name2) {
        int id1 = getStudentIdByName(students, name1);
        int id2 = getStudentIdByName(students, name2);

        Integer pair[] = {id1, id2};

        if (condition.equals("=")) {
            pairsAllowed_id.add(pair);
        }
        else if (condition.equals("!=")) {
            pairsForbidden_id.add(pair);
        }
    }

    public void resetRules() {
        pairsAllowed_id.clear();
        pairsForbidden_id.clear();
    }

    public void setConstraints(List<String[]> constraintList) {
        rules = constraintList;
    }

    public int getCountRows() {
        return countRows;
    }

    public int getCountCols() {
        return countCols;
    }

    public List<Student> getStudents() {
        return students;
    }

    public static Integer getStudentIdByName(List<Student> studentList, String name) {
        for (Student student : studentList) {
            if (student.getName().equals(name)) {
                return student.getIdNum();
            }
        }

        return null;
    }

    public static String getStudentNameById(List<Student> studentList, int id) {
        for (Student student : studentList) {
            if (student.getIdNum() == id) {
                return student.getName();
            }
        }

        return "";
    }

    public List<String[]> getConstraints() {
        return rules;
    }

    public List<Integer[]> getPairsIdAllowed() {
        return pairsAllowed_id;
    }

    public List<Integer[]> getPairsIdForbidden() {
        return pairsForbidden_id;
    }

	public static void main(String[] args) {
		SpringApplication.run(AutoseaterApplication.class, args);

        // int countStudent, countRows, countCols, countConst, countAdj;
        // IntVar[][] seats;
        // List<Student> students = new ArrayList<Student>();
        // List<Integer[]> pairsForbidden_id = new ArrayList<Integer[]>();
        // List<Integer[]> pairsAllowed_id = new ArrayList<Integer[]>();

        // scanner = new Scanner(System.in);
        // Assigner assigner = new Assigner();

        // System.out.println("Enter the number of students: ");
        // countStudent = setCount();

        // if (countStudent < 1) {
        //     System.out.println("ERR: There must be at least 1 student!");
        //     return;
        // }

        // for (int i = 0; i < countStudent; i++) {
        //     System.out.println("Enter name for Student " + i + ": ");
        //     String name = scanner.nextLine();
        //     students.add(new Student(i, name));
        // }

        // System.out.println("Enter the number of rows: ");
        // countRows = setCount();

        // System.out.println("Enter the number of columns: ");
        // countCols = setCount();

        // System.out.println("Number of rows: " + countRows);
        // System.out.println("Number of columns: " + countCols);

        // if ((countRows < 1) || (countCols < 1)) {
        //     System.out.println("ERR: There must be at least 1 row or column!");
        //     return;
        // }

        // System.out.println("Enter the number of constraints: ");
        // countConst = setCount();

        // System.out.println("Enter the adjacent seatings: ");
        // countAdj = setCount();

        // System.out.println("Number of constraints: " + countConst);
        // System.out.println("Number of adjacent seatings: " + countAdj);

        // for (int j = 0; j < students.size(); j++) {
        //     System.out.println(students.get(j).getName());
        // }

        // pairsForbidden_id = setSeatingPairs(students, countConst);

        // for (Integer[] forbidden_pair : pairsForbidden_id) {
        //     int forbidden_val1 = forbidden_pair[0];
        //     int forbidden_val2 = forbidden_pair[1];

        //     System.out.println("Constraint: {" + getStudentNameById(students, forbidden_val1) + ", " + getStudentNameById(students, forbidden_val2) + "}");
        // }

        // pairsAllowed_id = setSeatingPairs(students, countAdj);

        // for (Integer[] allowed_pair : pairsAllowed_id) {
        //     int allowed_val1 = allowed_pair[0];
        //     int allowed_val2 = allowed_pair[1];

        //     System.out.println("Adjacency: {" + getStudentNameById(students, allowed_val1) + ", " + getStudentNameById(students, allowed_val2) + "}");
        // }

        // scanner.close();
        // seats = assigner.assignSeats(countRows, countCols, pairsForbidden_id, pairsAllowed_id);

        // for (int i = 0; i < countRows; i++) {
        //     for (int j = 0; j < countCols; j++) {
        //         System.out.print(getStudentNameById(students, seats[i][j].getValue()) + "\t");
        //     }
        //     System.out.println();
        // }
    }

}
