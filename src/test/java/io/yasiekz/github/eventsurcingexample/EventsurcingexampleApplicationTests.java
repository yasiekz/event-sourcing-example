package io.yasiekz.github.eventsurcingexample;

import io.yasiekz.github.eventsurcingexample.initializer.MongoFromDockerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = MongoFromDockerInitializer.class)
@SpringBootTest
class EventsurcingexampleApplicationTests {

    @Test
    void contextLoads() {
    }

}
