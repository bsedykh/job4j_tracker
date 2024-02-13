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

class FindByNameTest {
    @Test
    public void whenFindItemSuccessfully() {
        var input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Found item");
        var output = new StubOutput();

        var tracker = new MemTracker();
        var item = new Item("Found item");
        tracker.add(item);

        var findAction = new FindByName(output);
        findAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявок по имени ===" + ln
                        + item + ln
        );
    }

    @Test
    public void whenItemIsNotFound() {
        var input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Other item");
        var output = new StubOutput();

        var tracker = new MemTracker();
        var item = new Item("Found item");
        tracker.add(item);

        var findAction = new FindByName(output);
        findAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявок по имени ===" + ln
                        + "Заявки с именем: Other item не найдены." + ln
        );
    }
}
