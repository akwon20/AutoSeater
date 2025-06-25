package autoseater;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutoseaterIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private InputController controller;

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
        assertThat(controller).isNotNull();
        System.out.println("contextLoads() successful");
	}

    @Test
    public void doesReturnHelloWorld() {
        assertThat(this.restTemplate.getForObject(
            "http://localhost:" +
            port +
            "/api" +
            "/helloworld",
            String.class)).contains("Hello World!");
    }


}