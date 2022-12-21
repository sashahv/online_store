package com.store.onlinestore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OnlineStoreApplicationTests {
    Calculator underTest = new Calculator();

    @Test
    void itShouldAddNumbers() {
        int numberOne = 20;
        int numberTwo = 30;

        int result = underTest.add(numberOne, numberTwo);

        int expected = 50;
        assertThat(result).isEqualTo( expected);
    }

    class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }
}
