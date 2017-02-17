package com.sjtools.tictactoe.app;

import com.sjtools.tictactoe.app.TicTacToeGameApp;
import com.sjtools.tictactoe.utils.EndOfAutoScannerInputException;
import com.sjtools.tictactoe.utils.TicTacToeGameAutomatedInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Full scale integration testing for TicTacToe Application
 * Input scenarios can be provided and tested automatically against the actual application
 */
public class TicTacToeGameAppTest {

    TicTacToeGameAutomatedInput autoInput;
    // old System.out
    PrintStream oldOut;

    @Before
    public void setUp() throws Exception
    {
        //intercept System.out to avoid printing
        // This is rather risky and bold move as will impact the whole JVM run-time,
        // but for the sake of exercise....
        //
        // Save the old System.out
        oldOut = System.out;
        // Create a null stream to get rid of regular output
        OutputStream nullStream = new OutputStream(){
            @Override
            public void write(int b) throws IOException
            {
                //effectively /dev/null
            }
        };
        PrintStream ps = new PrintStream(nullStream);
        System.setOut(ps);


        // example application run scenarios
        // this can be done in Before for all scenarios
        // or in each test specifically, as shown in other examples below
        autoInput = new TicTacToeGameAutomatedInput();
        Queue<String> q = new LinkedList<>();
        autoInput.feedScannerMap("GameWonByPlayer0",q);
        q.add("n");
        q.add("2");
        q.add("3");
        q.add("3");
        q.add("x");
        q.add("0,0");
        q.add("1,1");
        q.add("0,1");
        q.add("1,2");
        q.add("0,2");
        q.add("1,0");
        q.add("q");

        q = new LinkedList<>();
        autoInput.feedScannerMap("GameWonByPlayer1",q);
        q.add("n");
        q.add("2");
        q.add("3");
        q.add("3");
        q.add("x");
        q.add("0,0");
        q.add("1,1");
        q.add("0,2");
        q.add("0,1");
        q.add("2,0");
        q.add("2,1");
        q.add("q");
    }

    @After
    public void tearDown() throws Exception
    {
        autoInput = null;
        //restore System.out
        System.out.flush();
        System.setOut(oldOut);
    }

    /**
     * Utility method to run app.
     * Wraps input break through EndOfAutoScannerInputException.
     *
     * !!!!!MUST BE USED FOR APP execution!!!!
     *
     * See below in examples.
     * If is not used, and app is run directly, then EndOfAutoScannerInputException
     * will not be caught on auto input end {@link TicTacToeGameAutomatedInput}.
     * Test will fail with exception, instead of allowing to examine app status.
     *
     * - Always use playApp(app)
     * - Never use app.playTicTacToeGame() directly
     *
     * @param app
     * @throws Exception
     */
    private void playApp(TicTacToeGameApp app) throws Exception
    {
        if (app!=null)
        {
            try
            {
                app.playTicTacToeGame();
            }
            catch (Exception e)
            {
                if (!(e instanceof EndOfAutoScannerInputException))
                {
                    throw e;
                }
            }
        }

    }
    @Test
    public void app_ShouldBeWonByPlayer0() throws Exception
    {

        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(autoInput.getScannerFromMap("GameWonByPlayer0"));
        playApp(app);
        assertTrue("Expected isGameWon == TRUE, FALSE received ",app.getGame().isGameWon());
        assertEquals("Player 0 expected", 0, app.getGame().getWinner());
    }

    @Test
    public void app_ShouldBeWonByPlayer1() throws Exception
    {
        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(autoInput.getScannerFromMap("GameWonByPlayer1"));
        playApp(app);
        assertTrue("Expected isGameWon == TRUE, FALSE received ",app.getGame().isGameWon());
        assertEquals("Player 1 expected", 1, app.getGame().getWinner());
    }

    @Test
    public void app_ShouldFinishMainLoopWithGameEnded() throws Exception
    {
        TicTacToeGameAutomatedInput input = new TicTacToeGameAutomatedInput();
        Queue<String> q = new LinkedList<>();
        input.feedScannerMap("GameFinished",q);
        q.add("n");
        q.add("2");
        q.add("3");
        q.add("3");
        q.add("x");
        q.add("q");
        q.add("q");

        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(input.getScannerFromMap("GameFinished"));
        playApp(app);
        assertFalse("Expected isGameWon == FALSE, TRUE received ",app.getGame().isGameWon());
    }
    @Test
    public void app_ShouldFinishGameButAppNotEndedAutoInputExceptionUsedToGetControlBackToTest() throws Exception
    {
        TicTacToeGameAutomatedInput input = new TicTacToeGameAutomatedInput();
        Queue<String> q = new LinkedList<>();
        input.feedScannerMap("GameFinishedWithInputExceptionToGetControl",q);
        q.add("n");
        q.add("2");
        q.add("3");
        q.add("3");
        q.add("x");
        q.add("q");
        // With this line commented out app stays in input loop, but
        // AutoInput generates EndOfAutoScannerInputException when empty to
        // pass control back to test.
        // q.add("q");

        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(input.getScannerFromMap("GameFinishedWithInputExceptionToGetControl"));
        playApp(app);
        assertFalse("Expected isGameWon == FALSE, TRUE received ",app.getGame().isGameWon());
    }
    @Test
    public void app_GameUnfinishedScenarioInterruptedByAutoInputException() throws Exception
    {
        TicTacToeGameAutomatedInput input = new TicTacToeGameAutomatedInput();
        Queue<String> q = new LinkedList<>();
        input.feedScannerMap("GameUnFinishedWithInputExceptionToGetControl",q);
        q.add("n");
        q.add("2");
        //this scenario ends after providing number of players, then AutoInputException is issues
        //control should get back to this test.
        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(input.getScannerFromMap("GameUnFinishedWithInputExceptionToGetControl"));
        playApp(app);
        assertTrue("Expected control received",true);
    }
}