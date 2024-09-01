package com.rr.dreamtracker;

import com.rr.dreamtracker.controller.DreamController;
import com.rr.dreamtracker.security.SecurityConfig;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.beans.beancontext.BeanContext;


@SpringBootApplication
@NoArgsConstructor
public class DreamTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamTrackerApplication.class, args);

//        System.out.println(ApplicationContextProvider.getApplicationContext().getBean(DreamController.class));

    }


}
