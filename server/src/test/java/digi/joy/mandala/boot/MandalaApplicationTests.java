package digi.joy.mandala.boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MandalaTestConfig.class)
@ActiveProfiles("test")
class MandalaApplicationTests {

    @Test
    void contextLoads() {

    }

}
