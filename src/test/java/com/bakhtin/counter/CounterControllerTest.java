package com.bakhtin.counter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getCounter() {
        ResponseEntity<Integer> response = template.getForEntity(base.toString(),Integer.class);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void postCounter() {
        int count = 5;
        for (int i = 1; i <= count; i++) {
            ResponseEntity<Integer> response = template.postForEntity(base.toString(), null, Integer.class);
            assertThat(response.getBody()).isEqualTo(i);
        }
    }

    @Test
    void deleteCounter() {
        ResponseEntity<Integer> response = template.getForEntity(base.toString(),Integer.class);
        assertThat(response.getBody()).isEqualTo(0);
    }
}
