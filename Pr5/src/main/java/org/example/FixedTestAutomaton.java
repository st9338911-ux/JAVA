package org.example;

public class FixedTestAutomaton extends TestAutomaton {

    @Override
    protected State nextState(State currentState, char ch) {
        return switch (currentState) {
            case S -> (ch == 'T') ? State.ONE : State.S;
            case ONE -> {
                if (ch == 'E') yield State.TWO;
                if (ch == 'T') yield State.ONE;
                yield State.S;
            }
            case TWO -> {
                if (ch == 'S') yield State.THREE;
                if (ch == 'T') yield State.ONE;
                yield State.S;
            }
            case THREE -> (ch == 'T') ? State.F : State.S;
            case F -> State.F;
        };
    }
}