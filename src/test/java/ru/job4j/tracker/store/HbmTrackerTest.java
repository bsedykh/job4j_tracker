package ru.job4j.tracker.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HbmTrackerTest {
    @AfterEach
    public void wipeTable() {
        try (var tracker = new HbmTracker()) {
            for (var item : tracker.findAll()) {
                tracker.delete(item.getId());
            }
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("item");
            tracker.add(item);
            assertThat(tracker.findById(item.getId())).isEqualTo(item);
        }
    }

    @Test
    public void whenReplaceItemThenNameMustBeUpdated() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("item");
            tracker.add(item);
            Item newItem = new Item("new item");
            boolean result = tracker.replace(item.getId(), newItem);
            assertThat(result).isTrue();
            assertThat(tracker.findById(item.getId()).getName())
                    .isEqualTo(newItem.getName());
        }
    }

    @Test
    public void whenReplaceMissingItemThenResultIsFalse() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("item");
            tracker.add(item);
            Item newItem = new Item("new item");
            int missingId = 100;
            boolean result = tracker.replace(missingId, newItem);
            assertThat(result).isFalse();
            assertThat(tracker.findById(item.getId())).isEqualTo(item);
        }
    }

    @Test
    public void whenFindAll() {
        try (var tracker = new HbmTracker()) {
            Item first = new Item("First");
            Item second = new Item("Second");
            tracker.add(first);
            tracker.add(second);
            List<Item> items = tracker.findAll();
            assertThat(items.size()).isEqualTo(2);
            assertThat(items.get(0)).isEqualTo(first);
            assertThat(items.get(1)).isEqualTo(second);
        }
    }

    @Test
    public void whenFindByName() {
        try (var tracker = new HbmTracker()) {
            Item first = new Item("First");
            Item second = new Item("Second");
            Item third = new Item("Second");
            tracker.add(first);
            tracker.add(second);
            tracker.add(third);
            List<Item> items = tracker.findByName("Second");
            assertThat(items.size()).isEqualTo(2);
            assertThat(items.get(0)).isEqualTo(second);
            assertThat(items.get(1)).isEqualTo(third);
        }
    }

    @Test
    public void whenDeleteItemIsSuccessful() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("item");
            tracker.add(item);
            int id = item.getId();
            tracker.delete(id);
            assertThat(tracker.findById(id)).isNull();
        }
    }

    @Test
    public void whenDeleteItemIsNotSuccessful() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("item");
            tracker.add(item);
            tracker.delete(1000);
            assertThat(tracker.findById(item.getId())).isEqualTo(item);
        }
    }
}
