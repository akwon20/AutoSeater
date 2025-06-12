package autoseater;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class InputController {

    @GetMapping("/studentnames")
    public ResponseEntity<Map<String, String[]>> getStudentNames() {
        Map<String, String[]> response = new HashMap();

        return ResponseEntity.ok(response);
    }

}