package com.example.todolist.model;

/** @brief this class aim to represent a "person"
 *  person will be : User , Visitor . . .
 *  @author Izemmouren Ilyes
 */
public abstract class Person {
    protected String fullName;
    protected int age;

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    abstract void displayInformation();
}

