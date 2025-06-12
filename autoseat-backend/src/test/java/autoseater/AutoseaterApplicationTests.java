package autoseater;

// import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoseaterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    public void checkStudentList() {
        List<Student> testStudentList = new ArrayList<Student>();

        for (int i = 0; i < 3; i++) {
            testStudentList.add(new Student(i, "Student " + i));
        }

        Assert.assertEquals(3, testStudentList.size());
    }

}
