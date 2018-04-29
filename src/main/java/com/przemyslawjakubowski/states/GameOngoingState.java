package com.przemyslawjakubowski.states;

import com.przemyslawjakubowski.*;
import com.przemyslawjakubowski.board.BoardStatus;
import com.przemyslawjakubowski.board.boardExceptions.IncorrectSymbolException;
import com.przemyslawjakubowski.gameConfiguration.configurationExceptions.BoardDimensionException;
import com.przemyslawjakubowski.gameConfiguration.BoardConfiguration;
import com.przemyslawjakubowski.player.Player;
import com.przemyslawjakubowski.player.Players;
import com.przemyslawjakubowski.print.Printer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameOngoingState implements GameState {

    @Override
    public void performAction(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {

        BoardStatus boardStatus = setBoardDimensions(userInput, output);
        setAmountOfSymbolsToWin(userInput, output, xoGame);


        MovesHandler movesHandler = new MovesHandler(boardStatus);
        Players players = xoGame.getPlayers();
        output.accept("Gramy PAAAANIE!");
        output.accept("Kto zaczyna?");

        Player player;
        Judge judge = new Judge(boardStatus, 3); // todo zmienic na konfigurowalna wartosc
        player = getStartingPlayer(userInput, output, players);

        while(!judge.isWinnerPresent()){
            Printer.print(boardStatus);
            movesHandler.handleMoves(userInput, output, player, judge);
            player = players.getOppositePlayer(player);
        }
    }

    private void setAmountOfSymbolsToWin(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {

    }

    private BoardStatus setBoardDimensions(Supplier<String> userInput, Consumer<String> output) {
        int columns = getAmountOfColumns(userInput, output);
        int rows = getAmountOfRows(userInput, output);
        return new BoardStatus(rows,columns);
    }

    private int getAmountOfRows(Supplier<String> userInput, Consumer<String> output) {
        output.accept("A plansza to jaka długa ma być?");
        BoardConfiguration boardConfiguration = new BoardConfiguration();

        int userRowsEntry = 0;
        try{
            userRowsEntry = Integer.parseInt(userInput.get());
            boardConfiguration.setRows(userRowsEntry);
        } catch (BoardDimensionException e) {
            output.accept(e.toString());
            getAmountOfRows(userInput, output);
        } catch (NumberFormatException e){
            output.accept("Niepoprawna wartość");
            getAmountOfRows(userInput, output);
        }
        return userRowsEntry;
    }

    private int getAmountOfColumns(Supplier<String> userInput, Consumer<String> output) {
        output.accept("A plansza to jaka szeroka ma być?");
        BoardConfiguration boardConfiguration = new BoardConfiguration();

        int userColumnsEntry = 0;
        try{
            userColumnsEntry = Integer.parseInt(userInput.get());
            boardConfiguration.setColumns(userColumnsEntry);
        } catch (BoardDimensionException e) {
            output.accept(e.toString());
            getAmountOfColumns(userInput, output);
        } catch (NumberFormatException e){
            output.accept("Niepoprawna wartość");
            getAmountOfColumns(userInput, output);
        }

        return userColumnsEntry;
    }

    private Player getStartingPlayer(Supplier<String> userInput, Consumer<String> output, Players players) {
        Player player = null;
        try {
            output.accept("Ktory gracz zaczyna? X czy O?");
            player = players.getPlayerBySymbol(userInput.get());
        } catch (IncorrectSymbolException exception){
            output.accept(exception.toString());
            getStartingPlayer(userInput, output, players);
        }
        return player;
    }

    @Override
    public GameState goToNextState() {
        return new GameFinishedState();
    }
}
