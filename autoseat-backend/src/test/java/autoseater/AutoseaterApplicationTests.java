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

    @Test
    public void checkStudentId() {
        List<Student> testStudentList = new ArrayList<Student>();

        int testId;

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        testId = AutoseaterApplication.getStudentIdByName(testStudentList, testStudentList.get(2).getName());

        System.out.println(testId);

        Assertions.assertNotNull(testId);
        Assertions.assertEquals(2, testId);
    }

    @Test
    public void checkStudentIdFail() {
        List<Student> testStudentList = new ArrayList<Student>();

        int testId;

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        testId = AutoseaterApplication.getStudentIdByName(testStudentList, testStudentList.get(0).getName());

        System.out.println(testId);

        Assertions.assertNotNull(testId);
        Assertions.assertNotEquals(2, testId);
    }

    @Test
    public void checkStudentName() {
        List<Student> testStudentList = new ArrayList<Student>();

        String testName;

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        testName = AutoseaterApplication.getStudentNameById(testStudentList, testStudentList.get(2).getIdNum());

        System.out.println(testName);

        Assertions.assertNotNull(testName);
        Assertions.assertEquals("Student 2", testName);
    }

    @Test
    public void checkStudentNameFail() {
        List<Student> testStudentList = new ArrayList<Student>();

        String testName;

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        testName = AutoseaterApplication.getStudentNameById(testStudentList, testStudentList.get(0).getIdNum());

        System.out.println(testName);

        Assertions.assertNotNull(testName);
        Assertions.assertNotEquals("Student 2", testName);
    }
}
