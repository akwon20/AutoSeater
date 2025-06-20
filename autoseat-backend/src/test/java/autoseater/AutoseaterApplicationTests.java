package autoseater;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoseaterApplicationTests {

	@Test
    public void checkStudentList() {
        List<Student> testStudentList = new ArrayList<Student>();

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        System.out.println(testStudentList.size());

        Assertions.assertEquals(3, testStudentList.size());
    }

    @Test
    public void checkStudentListFail() {
        List<Student> testStudentList = new ArrayList<Student>();

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        System.out.println(testStudentList.size());

        Assertions.assertNotEquals(2, testStudentList.size());
    }
}
