package com.smarthire.smarthire_backend;

import com.smarthire.smarthire_backend.entity.MockQuestion;
import com.smarthire.smarthire_backend.repository.MockQuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockQuestionSeeder implements CommandLineRunner {

    private final MockQuestionRepository repository;

    public MockQuestionSeeder(MockQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        seed("Easy", "Java",
                "What is OOP in Java? Explain its four main principles.",
                "object oriented programming,encapsulation,inheritance,polymorphism,abstraction");

        seed("Easy", "SQL",
                "What is SQL and what is it used for?",
                "structured query language,database,query,data");

        seed("Easy", "Spring Boot",
                "What is Spring Boot and why is it used?",
                "spring framework,auto configuration,starter,embedded server");

        seed("Easy", "Angular",
                "What is Angular and what is it used for?",
                "typescript,framework,web applications,components");

        seed("Medium", "Java",
                "What is the difference between method overloading and method overriding in Java?",
                "overloading,overriding,compile time,run time,inheritance");

        seed("Medium", "SQL",
                "What is the difference between WHERE and HAVING in SQL?",
                "where,having,group by,rows,groups");

        seed("Medium", "Spring Boot",
                "What is dependency injection in Spring Boot?",
                "dependency injection,ioc,bean,autowiring,container");

        seed("Medium", "Angular",
                "What are Angular components and how do they work?",
                "component,typescript,template,selector,data binding");

        seed("Hard", "Java",
                "Explain the difference between ArrayList and LinkedList in Java.",
                "arraylist,linkedlist,array,doubly linked list,random access,insertion");

        seed("Hard", "SQL",
                "Write and explain a SQL query to find the third highest salary.",
                "select,order by,limit,offset,salary,subquery");

        seed("Hard", "Spring Boot",
                "Explain how REST APIs are created in Spring Boot.",
                "rest api,controller,request mapping,getmapping,postmapping,response");

        seed("Hard", "Angular",
                "Explain Angular services and dependency injection.",
                "service,injectable,dependency injection,component,reusability");

        System.out.println("Mock interview question seeding completed.");
    }

    private void seed(
            String difficulty,
            String topic,
            String question,
            String expectedKeywords) {

        String role = "Software Engineer";

        List<MockQuestion> existing =
                repository.findByRoleAndDifficultyAndTopic(
                        role,
                        difficulty,
                        topic
                );

        if (!existing.isEmpty()) {
            return;
        }

        MockQuestion mockQuestion = new MockQuestion();

        mockQuestion.setRole(role);
        mockQuestion.setDifficulty(difficulty);
        mockQuestion.setTopic(topic);
        mockQuestion.setQuestion(question);
        mockQuestion.setExpectedKeywords(expectedKeywords);

        repository.save(mockQuestion);

        System.out.println(
                "Added mock question: "
                        + difficulty
                        + " - "
                        + topic
        );
    }
}
