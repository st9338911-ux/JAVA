package org.example;

public class TestAutomaton {

    public State process(String input) {
        State currentState = State.S;
        if (input == null || input.isEmpty()) {
            return currentState;
        }

        for (int i = 0; i < input.length(); i++) {
            currentState = nextState(currentState, input.charAt(i));
        }
        return currentState;
    }

    protected State nextState(State currentState, char ch) {
        return switch (currentState) {
            case S -> (ch == 'T') ? State.ONE : State.S;
            case ONE -> (ch == 'E') ? State.TWO : State.S;
            case TWO -> (ch == 'S') ? State.THREE : State.S;
            case THREE -> (ch == 'T') ? State.F : State.S;
            case F -> State.F;
        };
    }
}