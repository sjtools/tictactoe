package com.sjtools.tictactoe;
import com.sjtools.tictactoe.app.TicTacToeGameAppTest;
import com.sjtools.tictactoe.board.impl.TicTacToeBoardTest;
import com.sjtools.tictactoe.game.impl.TicTacToeGamePhaseFinishedTest;
import com.sjtools.tictactoe.game.impl.TicTacToeGamePhaseInProgressTest;
import com.sjtools.tictactoe.game.impl.TicTacToeGameTest;
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
