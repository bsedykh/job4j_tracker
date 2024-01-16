package ru.job4j.tracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.tracker.store.SqlTracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class SqlTrackerTest {
    private static Connection connection;

    @BeforeAll
    public static void initConnection() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("db/liquibase_test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }

    @Test
    public void whenReplaceItemThenNameMustBeUpdated() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        Item newItem = new Item("new item");
        boolean result = tracker.replace(item.getId(), newItem);
        assertThat(result).isTrue();
        assertThat(tracker.findById(item.getId()).getName())
                .isEqualTo(newItem.getName());
    }

    @Test
    public void whenReplaceMissingItemThenResultIsFalse() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        Item newItem = new Item("new item");
        int missingId = 100;
        boolean result = tracker.replace(missingId, newItem);
        assertThat(result).isFalse();
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }

    @Test
    public void whenFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        Item first = new Item("First");
        Item second = new Item("Second");
        tracker.add(first);
        tracker.add(second);
        List<Item> items = tracker.findAll();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0)).isEqualTo(first);
        assertThat(items.get(1)).isEqualTo(second);
    }

    @Test
    public void whenFindByName() {
        SqlTracker tracker = new SqlTracker(connection);
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

    @Test
    public void whenDeleteItemIsSuccessful() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        int id = item.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id)).isNull();
    }

    @Test
    public void whenDeleteItemIsNotSuccessful() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.delete(1000);
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }
}
