package ru.job4j.pojo;

import java.time.LocalDate;

public class College {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Ivan Ivanov");
        student.setGroup("A10");
        student.setAdmissionDate(LocalDate.of(2000, 9, 1));
        System.out.println("Name: " + student.getName()
                + ", group: " + student.getGroup()
                + ", admission date: " + student.getAdmissionDate());
    }
}
