package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemComparatorTest {
    @Test
    void whenAscByName() {
        Item item1 = new Item("aaa");
        Item item2 = new Item("bbb");
        Item item3 = new Item("ccc");
        List<Item> items = Arrays.asList(item3, item1, item2);
        List<Item> expected = Arrays.asList(item1, item2, item3);
        Collections.sort(items, new ItemAscByName());
        assertThat(items).isEqualTo(expected);
    }

    @Test
    void whenDescByName() {
        Item item1 = new Item("aaa");
        Item item2 = new Item("bbb");
        Item item3 = new Item("ccc");
        List<Item> items = Arrays.asList(item3, item1, item2);
        List<Item> expected = Arrays.asList(item3, item2, item1);
        Collections.sort(items, new ItemDescByName());
        assertThat(items).isEqualTo(expected);
    }
}
