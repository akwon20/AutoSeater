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
    public ResponseEntity<Map<String, List<Student>>> getStudentData() {
        Map<String, List<Student>> response = new HashMap();

        response.put("students", mainApp.getStudents());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/studentnamesget")
    public List<String> getStudentNames() {
        String[] nameArr;
        List<String> names = new ArrayList<String>();
        List<Student> students = mainApp.getStudents();

        for (int i = 0; i < students.size(); i++) {
            names.add(students.get(i).getName());
        }

        // nameArr = names.toArray(new String[0]);

        // for (int j = 0; j < nameArr.length; j++) {
        //     System.out.println(nameArr[j]);
        // }

        // return ResponseEntity.ok(names);
        return names;
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

    @PostMapping("/constraintpost")
    public ResponseEntity<String> updateConstraints(@RequestBody List<String[]> constraintList) {

        // String[] newConstraints = ["Test", "Test 2"];

        // return ResponseEntity.ok("Constraints received! \n" + newConstraints);

        return ResponseEntity.ok("Constraint list received! \n" + constraintList);
    }

}