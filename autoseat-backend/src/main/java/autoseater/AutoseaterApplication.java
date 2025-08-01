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

    public List<String[]> assignSeats() {
        List<String[]> seatNames = new ArrayList<String[]>();

        if (assigner.getModel() != null) {
            assigner.resetModel();
        }

        seats = assigner.assignSeats(countRows, countCols, pairsForbidden_id, pairsAllowed_id);

        for (int i = 0; i < countRows; i++) {
            String seatCols[] = new String[countCols];

            for (int j = 0; j < countCols; j++) {
                seatCols[j] = getStudentNameById(students, seats[i][j].getValue());
            }
            seatNames.add(seatCols);
        }

        return seatNames;
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
    }

}
