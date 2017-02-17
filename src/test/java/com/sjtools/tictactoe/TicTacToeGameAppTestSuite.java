package com.sjtools.tictactoe;
import com.sjtools.tictactoe.app.TicTacToeGameAppTest;
import com.sjtools.tictactoe.impl.TicTacToeBoardTest;
import com.sjtools.tictactoe.impl.TicTacToeGamePhaseFinishedTest;
import com.sjtools.tictactoe.impl.TicTacToeGamePhaseInProgressTest;
import com.sjtools.tictactoe.impl.TicTacToeGameTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TicTacToeBoardTest.class,
        TicTacToeGameTest.class,
        TicTacToeGamePhaseInProgressTest.class,
        TicTacToeGamePhaseFinishedTest.class,
        TicTacToeGameAppTest.class
})

/**
 * Created by sjtools on 15.02.2017.
 */
public class TicTacToeGameAppTestSuite
{
}
