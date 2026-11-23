package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedTestAutomatonTest {

    private FixedTestAutomaton automaton;

    @BeforeEach
    void setUp() {
        automaton = new FixedTestAutomaton();
    }

    @ParameterizedTest(name = "Кейс з накладанням префікса: \"{0}\" -> Очікуваний стан: {1}")
    @CsvSource({
            "'TTEST', F",
            "'TTTEST', F",
            "'TETEST', F",
            "'TETETEST', F",
            "'TTETEST', F",
            "'abcTTESTxyz', F",
            "'abcTETESTxyz', F",
            "'TT', ONE",
            "'TTT', ONE",
            "'TET', ONE",
            "'TETET', ONE",
            "'TETE', TWO",
            "'TETES', THREE",
            "'abcTESTabc', F",
            "'abcTES', THREE",
            "'abcdef', S",
            "'', S"
    })
    void shouldHandleOverlappingPrefixesCorrectly(String input, State expectedState) {
        assertEquals(expectedState, automaton.process(input));
    }
}