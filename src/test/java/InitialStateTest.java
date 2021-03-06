import com.przemyslawjakubowski.*;
import com.przemyslawjakubowski.board.BoardRowsConfigurationState;
import com.przemyslawjakubowski.textOutput.LanguageFileReader;
import com.przemyslawjakubowski.textOutput.LanguageStrings;
import com.przemyslawjakubowski.textOutput.OutputConsumer;
import com.przemyslawjakubowski.player.Players;
import com.przemyslawjakubowski.mainStates.GameState;
import com.przemyslawjakubowski.mainStates.InitialState;
import com.przemyslawjakubowski.userInput.UserInputProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

public class InitialStateTest {

    @Test
    public void goToNextState_InitToGameOngoing(){
        // given
        GameState gameState = new InitialState();

        // when
        gameState = gameState.goToNextState();

        // then
        Assert.assertEquals(gameState.getClass(), BoardRowsConfigurationState.class);
    }

    @Test
    public void afterPerformActionOfInitState_2_playersArePresent(){
        // given
        String playersNames = "winner\nloser\n";
        System.setIn(new ByteArrayInputStream(playersNames.getBytes()));

        UserInputProvider userInputProvider = new UserInputProvider(new Scanner(System.in)::nextLine);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        OutputConsumer outputConsumer = new OutputConsumer(System.out::println);
        LanguageStrings languageStrings = LanguageFileReader.getLanguageStringsFromFile("ENG.lang", outputConsumer);
        outputConsumer = new OutputConsumer(System.out::println, languageStrings);

        // when
        XOGame game = new XOGame(userInputProvider, outputConsumer);

        InitialState initialState = new InitialState();
        initialState.performAction(userInputProvider, outputConsumer, game);

        // then
        Players players = game.getPlayers();

        Assert.assertTrue(players.getNextPlayer().getName().equals("winner"));
        Assert.assertTrue(players.getNextPlayer().getName().equals("loser"));

        // @After
        System.setIn(System.in);
    }
}
