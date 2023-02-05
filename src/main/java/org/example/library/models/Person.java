package org.example.library.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

public class Person {

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, [A-Z]\\w+", message = "Your name should be in this format: First Name, Middle Name, Last Name")
    private String name;
    @Min(1901)
    private int yearOfBirth;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person() {
    }
    public Person(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
