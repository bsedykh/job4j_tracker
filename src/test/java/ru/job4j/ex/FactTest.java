package ru.job4j.ex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactTest {
    @Test
    public void whenFact0Then1() {
        int expected = 1;
        int result = new Fact().calc(0);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFact1Then1() {
        int expected = 1;
        int result = new Fact().calc(1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFact3Then6() {
        int expected = 6;
        int result = new Fact().calc(3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFactLessThanZeroThenException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Fact().calc(-1);
                });
        assertThat(exception.getMessage()).isEqualTo("N could not be less than 0");
    }
}
