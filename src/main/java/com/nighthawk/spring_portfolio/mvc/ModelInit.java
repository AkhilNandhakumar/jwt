package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.activities.Activities;
import com.nighthawk.spring_portfolio.mvc.activities.ActivitiesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.activities.ActivitiesDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;

import java.util.List;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired PersonDetailsService personService;
    @Autowired ActivitiesDetailsService activitiesRepo;
    // @Autowired ActivitiesJpaRepository activitiesRepo;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            // Person database is populated with test data
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                // findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Person> personFound = personService.list(person.getName(), person.getEmail());  // lookup
                if (personFound.size() == 0) {
                    personService.save(person);  // save

                    // // Each "test person" starts with a "test note"
                    // String text = "Test " + person.getEmail();
                    // Note n = new Note(text, person);  // constructor uses new person as Many-to-One association
                    // noteRepo.save(n);  // JPA Save                  
                }
            }
            Activities[] activitiesArray = Activities.init();
            for (Activities event : activitiesArray) {
                List<Activities> test = activitiesRepo.list(event.getEvent());  // lookup
                if (test.size() == 0) {
                activitiesRepo.save(event);
            }
            }
        };
    }
}

