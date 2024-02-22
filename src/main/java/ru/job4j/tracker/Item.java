package ru.job4j.tracker;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class Item {
    private int id;
    private String name;
    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now();

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(int id, String name, LocalDateTime created) {
        this(id, name);
        this.created = created;
    }
}
