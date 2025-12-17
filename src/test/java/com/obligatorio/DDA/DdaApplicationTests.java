package com.obligatorio.DDA;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "OPENAI_API_KEY=dummy-test-key"
})
class DdaApplicationTests {

	@Test
	void contextLoads() {
	}

}
