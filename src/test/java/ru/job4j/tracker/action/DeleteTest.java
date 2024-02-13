package ru.job4j.tracker.action;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteTest {
    @Test
    public void whenItemWasDeletedSuccessfully() {
        var input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        var output = new StubOutput();

        var tracker = new MemTracker();
        tracker.add(new Item("Deleted item"));

        var deleteAction = new Delete(output);
        deleteAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Удаление заявки ===" + ln
                        + "Заявка удалена успешно." + ln
        );
    }

    @Test
    public void whenDeleteNonExistentItem() {
        var input = mock(Input.class);
        var output = new StubOutput();

        var tracker = new MemTracker();
        tracker.add(new Item("Deleted item"));

        var deleteAction = new Delete(output);
        deleteAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Удаление заявки ===" + ln
                        + "Ошибка удаления заявки." + ln
        );
    }
}
