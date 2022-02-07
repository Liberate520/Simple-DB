package ru.samsung.simpledb;

public class Person {
    long id;
    String name;
    int points;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
