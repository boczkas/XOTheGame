package com.przemyslawjakubowski;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameOngoingState implements GameState {



    @Override
    public void performAction(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {

        output.accept("A tablica to jaka wielka ma być?");

        Board board = new Board(3,3);

        output.accept("Gramy PAAAANIE!");

        board.handleMoves(userInput, output);
    }

    @Override
    public GameState goToNextState() {
        System.out.println();
        return new GameFinishedState();
    }
}
