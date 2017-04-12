package com.sjtools.tictactoe.game.impl;

import com.sjtools.tictactoe.board.impl.TicTacToeBoard;
import com.sjtools.tictactoe.game.impl.TicTacToeGame;
import com.sjtools.tictactoe.game.impl.TicTacToeGamePhaseFinished;
import com.sjtools.tictactoe.game.impl.TicTacToeGamePhaseInProgress;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by sjtools on 15.02.2017.
 */

public class TicTacToeGamePhaseInProgressTest {

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void canPlay_ShouldAlwaysReturnTrue() throws Exception {
        assertTrue(new TicTacToeGamePhaseInProgress(null).canPlay());
    }

    @Test
    public void play_ShouldReturnFalseWhenCellCannotBeChecked() throws Exception
    {
        TicTacToeGame game = Mockito.mock(TicTacToeGame.class);
        when(game.checkCell(anyInt(),anyInt(),anyInt())).thenReturn(false);
        TicTacToeGamePhaseInProgress testPhase = new TicTacToeGamePhaseInProgress(game);
        assertFalse("Expected false on checkCell with invalid params", testPhase.play(0,1,1));
    }

    @Test
    public void play_ShouldReturnTrueWhenCellCanBeChecked() throws Exception
    {
        TicTacToeGame game = Mockito.mock(TicTacToeGame.class);
        when(game.checkCell(anyInt(),anyInt(),anyInt())).thenReturn(true);
        TicTacToeGamePhaseInProgress testPhase = new TicTacToeGamePhaseInProgress(game);
        assertTrue("Expected true on checkCell with valid params", testPhase.play(0,1,1));
    }
    @Test
    public void play_ShouldSetFinishedStateOnGameWon() throws Exception
    {
        TicTacToeBoard board = Mockito.spy(new TicTacToeBoard());
        when(board.validateBoardInitParams(anyInt(),anyInt())).thenReturn(true);
        when(board.getRows()).thenReturn(1);
        when(board.getColumns()).thenReturn(1);
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS,board);
        TicTacToeGame  spyGame = Mockito.spy(game);
        when(spyGame.checkCell(anyInt(),anyInt(),anyInt())).thenReturn(true);
        TicTacToeGamePhaseInProgress testPhase = new TicTacToeGamePhaseInProgress(spyGame);
        //game won
        boolean gameWonRes = false;
        boolean gameWonFinState = false;
        when(spyGame.isGameWon()).thenReturn(true);
        when(spyGame.isWinningOptionAvailable()).thenReturn(true);
        when(spyGame.getNumberOfPossibleChecks()).thenReturn(0d);
        gameWonRes = testPhase.play(0,1,1);
        gameWonFinState = spyGame.getState() instanceof TicTacToeGamePhaseFinished;

        assertThat("Finish State was expected on game won",
                Arrays.asList(true, true),
                hasItems(
                        gameWonRes,             //won
                        gameWonFinState
                ));
    }
    @Test
    public void play_ShouldSetFinishedStateOnNoWinningOptions() throws Exception
    {
        TicTacToeBoard board = Mockito.spy(new TicTacToeBoard());
        when(board.validateBoardInitParams(anyInt(),anyInt())).thenReturn(true);
        when(board.getRows()).thenReturn(1);
        when(board.getColumns()).thenReturn(1);
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS,board);
        TicTacToeGame  spyGame = Mockito.spy(game);
        when(spyGame.checkCell(anyInt(),anyInt(),anyInt())).thenReturn(true);
        TicTacToeGamePhaseInProgress testPhase = new TicTacToeGamePhaseInProgress(spyGame);

        //no more winning options
        boolean gameNoWinOpRes = false;
        boolean gameNoWinOpFinState = false;
        when(spyGame.isGameWon()).thenReturn(false);
        when(spyGame.isWinningOptionAvailable()).thenReturn(false);
        when(spyGame.getNumberOfPossibleChecks()).thenReturn(1d);
        gameNoWinOpRes = testPhase.play(0,1,1);
        gameNoWinOpFinState = testPhase.game.getState() instanceof TicTacToeGamePhaseFinished;

        assertThat("Finish State was expected on no win options. ",
                Arrays.asList(true, true),
                hasItems(
                        gameNoWinOpRes,         //no more win options
                        gameNoWinOpFinState
                ));
    }
    @Test
    public void play_ShouldSetFinishedStateOnNoMoreMoves() throws Exception
    {
        TicTacToeBoard board = Mockito.spy(new TicTacToeBoard());
        when(board.validateBoardInitParams(anyInt(),anyInt())).thenReturn(true);
        when(board.getRows()).thenReturn(1);
        when(board.getColumns()).thenReturn(1);
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS,board);
        TicTacToeGame  spyGame = Mockito.spy(game);
        when(spyGame.checkCell(anyInt(),anyInt(),anyInt())).thenReturn(true);
        TicTacToeGamePhaseInProgress testPhase = new TicTacToeGamePhaseInProgress(spyGame);

        //no more winning options
        boolean gameNoMovesRes = false;
        boolean gameNoMovesFinState = false;
        when(spyGame.isGameWon()).thenReturn(false);
        when(spyGame.isWinningOptionAvailable()).thenReturn(true);
        when(spyGame.getNumberOfPossibleChecks()).thenReturn(0d);
        gameNoMovesRes  = testPhase.play(0,1,1);
        gameNoMovesFinState = testPhase.game.getState() instanceof TicTacToeGamePhaseFinished;

        assertThat("Finish State was expected on no moves. ",
                Arrays.asList(true, true),
                hasItems(
                        gameNoMovesRes,         //no more moves
                        gameNoMovesFinState
                ));
    }
}