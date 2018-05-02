package com.przemyslawjakubowski.player;

import com.przemyslawjakubowski.XOGame;
import com.przemyslawjakubowski.board.boardExceptions.IncorrectSymbolException;
import com.przemyslawjakubowski.player.Player;
import com.przemyslawjakubowski.player.Players;
import com.przemyslawjakubowski.states.GameConfigurationState;
import com.przemyslawjakubowski.states.GameOngoingState;
import com.przemyslawjakubowski.states.GameState;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StartingPlayerConfigurationState implements GameConfigurationState {

    boolean startingPlayerSetCorrectly = true;

    @Override
    public void performAction(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {

        tryToSetConfiguration(userInput, output, xoGame);
    }

    private void tryToSetConfiguration(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {
        try {
            Players players = xoGame.getPlayers();
            output.accept("Ktory gracz zaczyna? X czy O?");
            Player player = players.getPlayerBySymbol(askUserForInput(userInput));
            players.setStartingPlayer(player);
        } catch (IncorrectSymbolException exception){
            output.accept(exception.toString());
            startingPlayerSetCorrectly = false;
        }
    }

    @Override
    public GameState goToNextState() {
        if(startingPlayerSetCorrectly){
            return new GameOngoingState();
        }
        return new StartingPlayerConfigurationState();
    }

    @Override
    public String askUserForInput(Supplier<String> userInput) {
        return userInput.get();
    }
}