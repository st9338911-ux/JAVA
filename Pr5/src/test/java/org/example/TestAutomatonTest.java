package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAutomatonTest {

    private TestAutomaton automaton;

    @BeforeEach
    void setUp() {
        automaton = new TestAutomaton();
    }

    @ParameterizedTest(name = "Вхід: \"{0}\" -> Очікуваний стан: {1}")
    @CsvSource({
            "'', S",
            "'a', S",
            "'abcdef', S",
            "'12345!@#', S",
            "'test', S",
            "'TeSt', S",
            "'abcTestabc', S",
            "'T', ONE",
            "'TE', TWO",
            "'TES', THREE",
            "'TEST', F",
            "'abcTESTabc', F",
            "'abcTES', THREE",
            "'xyzT', ONE",
            "'xyzTE', TWO",
            "'xyzTES', THREE",
            "'TA', S",
            "'TEA', S",
            "'TESA', S",
            "'TESXabc', S",
            "'TESTa', F",
            "'TEST12345', F",
            "'TEST_TEST', F",
            "'abcTESTdefTES', F"
    })
    void shouldTransitionToExpectedState(String input, State expectedState) {
        assertEquals(expectedState, automaton.process(input));
    }

    @ParameterizedTest(name = "Null вхід -> Стан S")
    @NullSource
    void shouldReturnStartStateWhenInputIsNull(String input) {
        assertEquals(State.S, automaton.process(input));
    }
}