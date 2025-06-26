package autoseater;

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
    public ResponseEntity<Map<String, List<Student>>> getStudentNames() {
        Map<String, List<Student>> response = new HashMap();

        response.put("students", mainApp.getStudents());

        return ResponseEntity.ok(response);
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

}