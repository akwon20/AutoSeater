package autoseater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class InputController {

    private final AutoseaterApplication mainApp;

    public InputController(AutoseaterApplication mainApp) {
        this.mainApp = mainApp;
    }

    @GetMapping("/helloworld")
    public ResponseEntity<Map<String, String>> getHelloWorld() {
        Map<String, String> response = new HashMap();
        String output = mainApp.getGreeting();

        response.put("message", output);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/studentdataget")
    public List<Student> getStudentData() {
        return mainApp.getStudents();
    }

    @GetMapping("/studentnamesget")
    public List<String> getStudentNames() {
        String[] nameArr;
        List<String> names = new ArrayList<String>();
        List<Student> students = mainApp.getStudents();

        for (int i = 0; i < students.size(); i++) {
            names.add(students.get(i).getName());
        }

        return names;
    }

    @GetMapping("/constraintsget")
    public List<String[]> getConstraints() {
        return mainApp.getConstraints();
    }

    @GetMapping("/seatassignmentsget")
    public String[][] getSeatingAssignments() {
        Map<String, String[][]> response = new HashMap();
        String seats[][]  = mainApp.assignSeats();

        for (int i = 0; i < mainApp.getCountRows(); i++) {
            for (int j = 0; j < mainApp.getCountCols(); j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }

        response.put("seats", seats);

        return seats;

    }

    @PostMapping("/studentdatapost")
    public ResponseEntity<String> updateStudentData(@RequestBody Map<String, String> studentList) {
        String listValue = "";

        for (Map.Entry<String, String> entry : studentList.entrySet()) {
            listValue = entry.getValue();
        }

        System.out.println("Student List: " + listValue);

        String names[] = listValue.split("\\n");

        System.out.println("List size: " + names.length);

        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }

        mainApp.updateStudentList(names);

        return ResponseEntity.ok("Student data received! \n" + names);
    }

    @PostMapping("/constraintspost")
    public ResponseEntity<String> updateConstraints(@RequestBody List<String[]> constraintList) {
        mainApp.setConstraints(constraintList);

        if (!mainApp.getPairsIdAllowed().isEmpty() || !mainApp.getPairsIdForbidden().isEmpty()) {
            mainApp.resetRules();
        }

        for (int i = 0; i < constraintList.size(); i++) {
            String name1 = constraintList.get(i)[0];
            String condition = constraintList.get(i)[1];
            String name2 = constraintList.get(i)[2];

            mainApp.addRule(name1, condition, name2);
        }

        return ResponseEntity.ok("Constraint list received! \n" + constraintList);
    }

    @PostMapping("rowcolcountpost")
    public ResponseEntity<String> updateRowColCount(@RequestBody Map<String, Integer> countRowCol) {
        Integer rowVal = countRowCol.get("rows");
        Integer colVal = countRowCol.get("cols");

        if ((rowVal != null) && (colVal != null)) {
            int rows = rowVal;
            int cols = colVal;

            mainApp.setCountRows(rows);
            mainApp.setCountCols(cols);

            System.out.println("Rows: " + mainApp.getCountRows());
            System.out.println("Cols: " + mainApp.getCountCols());

            return ResponseEntity.ok("Row/Column count received!");

        }

        return ResponseEntity.badRequest().body("Row/Column count NOT received!");
    }
}