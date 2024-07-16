package com.rr.dreamtracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DreamTrackerApplicationTests {

    @Test
    void contextLoads() {
    }

}
