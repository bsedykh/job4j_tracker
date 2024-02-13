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

class FindByIdTest {
    @Test
    public void whenFindItemSuccessfully() {
        var input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        var output = new StubOutput();

        var tracker = new MemTracker();
        var item = new Item("Found item");
        tracker.add(item);

        var findAction = new FindById(output);
        findAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявки по id ===" + ln
                        + item + ln
        );
    }

    @Test
    public void whenItemIsNotFound() {
        var input = mock(Input.class);
        var output = new StubOutput();

        var tracker = new MemTracker();
        var item = new Item("Found item");
        tracker.add(item);

        var findAction = new FindById(output);
        findAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявки по id ===" + ln
                        + "Заявка с введенным id: 0 не найдена." + ln
        );
    }
}
