package com.przemyslawjakubowski.mainStates;

import com.przemyslawjakubowski.board.BoardRowsConfigurationState;
import com.przemyslawjakubowski.gameConfiguration.configurationExceptions.IncorrectPointsForTieException;
import com.przemyslawjakubowski.gameConfiguration.configurationExceptions.IncorrectPointsForWonException;
import com.przemyslawjakubowski.textOutput.OutputConsumer;
import com.przemyslawjakubowski.textOutput.OutputOption;
import com.przemyslawjakubowski.player.Point;
import com.przemyslawjakubowski.player.Player;
import com.przemyslawjakubowski.player.Symbol;
import com.przemyslawjakubowski.XOGame;
import com.przemyslawjakubowski.userInput.UserInputProvider;

import java.util.function.Supplier;

public class InitialState implements GameState {
    @Override
    public void performAction(UserInputProvider userInput, OutputConsumer output, XOGame xoGame) {
        try{
            xoGame.setAmountOfPointsForWinRound(new Point(3));
            xoGame.setAmountOfPointsForTieRound(new Point(1));
        }
        catch (IncorrectPointsForTieException e){
            e.printExceptionMessage(output);
        }
        catch (IncorrectPointsForWonException e){
            e.printExceptionMessage(output);
        }


        output.accept(OutputOption.STARTING);

        String playerName;
        output.accept(OutputOption.FIRST_PLAYER_NAME_QUESTION);

        while ((playerName = userInput.get()).equals("")){
            output.accept(OutputOption.EMPTY_PLAYER_NAME);
            output.accept(OutputOption.FIRST_PLAYER_NAME_QUESTION);
        }

        xoGame.addPlayer(new Player(playerName, Symbol.X, new Point(0)));

        output.accept(OutputOption.SECOND_PLAYER_NAME_QUESTION);
        while ((playerName = userInput.get()).equals("")){
            output.accept(OutputOption.EMPTY_PLAYER_NAME);
            output.accept(OutputOption.SECOND_PLAYER_NAME_QUESTION);
        }


        xoGame.addPlayer(new Player(playerName, Symbol.O, new Point(0)));

    }

    @Override
    public GameState goToNextState() {
        return new BoardRowsConfigurationState();
    }
}
