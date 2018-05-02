package com.przemyslawjakubowski.states;

import com.przemyslawjakubowski.*;
import com.przemyslawjakubowski.board.BoardStatus;
import com.przemyslawjakubowski.player.Player;
import com.przemyslawjakubowski.player.Players;
import com.przemyslawjakubowski.print.Printer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameOngoingState implements GameState {

    @Override
    public void performAction(Supplier<String> userInput, Consumer<String> output, XOGame xoGame) {

        BoardStatus boardStatus = xoGame.getBoardStatus();
        MovesHandler movesHandler = new MovesHandler(boardStatus);
        Players players = xoGame.getPlayers();

        Judge judge = new Judge(boardStatus, xoGame.getSymbolsToWin());
        Player player = players.getStartingPlayer();

        while(!(judge.isWinnerPresent() || judge.checkTie())){
            Printer.printBoard(boardStatus, output);
            movesHandler.handleMoves(userInput, output, player, judge);
            player = players.getNextPlayer();
        }

        printWinningMessage(player, judge, output, boardStatus);
        addPointsFromRound(player, players, judge);
    }

    private void addPointsFromRound(Player currentPlayer, Players players, Judge judge) {
        if(judge.isWinnerPresent()){
            currentPlayer.increaseScoreForWin();
        }
        else{
            currentPlayer.increaseScoreForTie();
            currentPlayer = players.getNextPlayer();
            currentPlayer.increaseScoreForTie();
        }
    }

    private void printWinningMessage(Player player, Judge judge, Consumer<String> output, BoardStatus boardStatus) {
        Printer.printBoard(boardStatus, output);
        if(judge.isWinnerPresent()){
            output.accept("Rundę wygrywa gracz: " + player.getName() + " !\n");
        }
        else{
            output.accept("Remis!\n");
        }
    }

    @Override
    public GameState goToNextState() {
        return new RoundFinishedState();
    }
}
