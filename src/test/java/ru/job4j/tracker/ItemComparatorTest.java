package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemComparatorTest {
    @Test
    void whenAscByName() {
        List<Item> items = Arrays.asList(
                new Item("ccc"),
                new Item("aaa"),
                new Item("bbb")
        );
        List<Item> expected = Arrays.asList(
                new Item("aaa"),
                new Item("bbb"),
                new Item("ccc")
        );
        Collections.sort(items, new ItemAscByName());
        assertThat(items.get(0).getName()).isEqualTo(expected.get(0).getName());
        assertThat(items.get(1).getName()).isEqualTo(expected.get(1).getName());
        assertThat(items.get(2).getName()).isEqualTo(expected.get(2).getName());
    }

    @Test
    void whenDescByName() {
        List<Item> items = Arrays.asList(
                new Item("ccc"),
                new Item("aaa"),
                new Item("bbb")
        );
        List<Item> expected = Arrays.asList(
                new Item("ccc"),
                new Item("bbb"),
                new Item("aaa")
        );
        Collections.sort(items, new ItemDescByName());
        assertThat(items.get(0).getName()).isEqualTo(expected.get(0).getName());
        assertThat(items.get(1).getName()).isEqualTo(expected.get(1).getName());
        assertThat(items.get(2).getName()).isEqualTo(expected.get(2).getName());
    }
}
