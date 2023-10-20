package ru.job4j.pojo;

public class Library {
    public static void main(String[] args) {
        Book[] books = {
                new Book("Clean code", 300),
                new Book("Harry Potter", 500),
                new Book("Algorithms and data structures", 600),
                new Book("Twilight", 700)
        };
        for (int i = 0; i < books.length; i++) {
            System.out.println("Name: " + books[i].getName() + ", page count: " + books[i].getPageCount());
        }
        Book temp = books[0];
        books[0] = books[3];
        books[3] = temp;
        for (int i = 0; i < books.length; i++) {
            System.out.println("Name: " + books[i].getName() + ", page count: " + books[i].getPageCount());
        }
        for (int i = 0; i < books.length; i++) {
            if ("Clean code".equals(books[i].getName())) {
                System.out.println("Name: " + books[i].getName() + ", page count: " + books[i].getPageCount());
            }
        }
    }
}
