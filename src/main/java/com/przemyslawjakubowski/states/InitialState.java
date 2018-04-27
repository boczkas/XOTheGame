package com.przemyslawjakubowski.states;

import com.przemyslawjakubowski.Player;
import com.przemyslawjakubowski.XOGame;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InitialState implements GameState {
    @Override
    public void performAction(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {
        output.accept("Bedziem zaczynac!\n");
        output.accept("Podaj imię pierwszego gracza!\n");
        xoGame.addPlayer(new Player(userInput.get()));
        output.accept("Podaj imię drugiego gracza!\n");
        xoGame.addPlayer(new Player(userInput.get()));
    }

    @Override
    public GameState goToNextState() {
        System.out.println();
        return new GameOngoingState();
    }
}