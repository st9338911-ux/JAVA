package org.example;

public class Main {
    public static void main(String[] args) {
        TestAutomaton automaton = new TestAutomaton();
        System.out.println("abcTESTabc -> " + automaton.process("abcTESTabc")); // F
        System.out.println("abcTES -> " + automaton.process("abcTES"));         // 3
        System.out.println("TTEST (базовий) -> " + automaton.process("TTEST")); // 1

        FixedTestAutomaton fixedAutomaton = new FixedTestAutomaton();
        System.out.println("TTEST (виправлений) -> " + fixedAutomaton.process("TTEST"));   // F
        System.out.println("TETEST (виправлений) -> " + fixedAutomaton.process("TETEST")); // F
    }
}