package com.rr.dreamtracker;

import org.springframework.boot.SpringApplication;

public class TestDreamTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.from(DreamTrackerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
