package de.mh.walter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMain {

    //TODO: use utf-8 as table encoding
    //TODO: implement tarpit and/or quota mechanism
    //TODO: extract register validation
    //TODO: extract auth need path check
    //TODO: replace system.out with proper logging
    //TODO: allow multiple keys per user (current implementation is dumb) -> remove old ones?
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

}
