package de.mh.walter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMain {

    //TODO: use utf-8 as table encoding
    //TODO: implement tarpit and/or quota mechanism
    //TODO: extract register validation
    //TODO: replace system.out with proper logging
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

}
