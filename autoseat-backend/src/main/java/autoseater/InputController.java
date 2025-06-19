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

    @GetMapping("/studentnamesget")
    public ResponseEntity<Map<String, List<Student>>> getStudentNames() {
        Map<String, List<Student>> response = new HashMap();

        response.put("students", mainApp.getStudents());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/studentdatapost")
    public void updateStudentData(@RequestBody String studentList) {
        // System.out.println("Student List: " + studentList);

        // Map<String, String> response = new HashMap();

        // response.put("studentList", studentList);

        // return ResponseEntity.ok(response);
        System.out.println("Student List: " + studentList);

        String names[] = studentList.split("\n");

        mainApp.updateStudentList(names);
    }

}