package com.sjtools.tictactoe.impl;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by sjtools on 15.02.2017.
 */
public class TicTacToeGameTest
{
    @Test(expected =  IllegalArgumentException.class)
    public void contructor_ShouldThrowIllegaArgumentExceptionOnInvalidBoard()throws Exception
    {
        new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS, null);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void constructor_ShoudThrowIllegaArgumentExceptionOnInvalidPlayers()throws Exception
    {
        TicTacToeBoard b = Mockito.mock(TicTacToeBoard.class);
        new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS-1, b);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void constructor_ShouldThrowIllegaArgumentExceptionOnAllInvalidInitParams()throws Exception
    {
        new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS-1, null);
    }

    @Test
    public void constructor_ShouldCreateAndReturnDefaultSettingsOnDefaultPlayersParam()throws Exception
    {
        TicTacToeBoard b = Mockito.mock(TicTacToeBoard.class);
        when(b.getColumns()).thenReturn(TicTacToeBoard.DEFAULT_COLUMNS);
        when(b.getRows()).thenReturn(TicTacToeBoard.DEFAULT_ROWS);
        TicTacToeGame g = new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS, b);
        assertTrue("Game should be created with default players",
                g.getNumberOfPlayers()==TicTacToeGame.DEFAULT_PLAYERS &&
                        g.getRows() == TicTacToeBoard.DEFAULT_ROWS &&
                        g.getColumns() == TicTacToeBoard.DEFAULT_COLUMNS);
    }

    @Test
    public void constructor_ShouldCreateAndReturnValidSettingsOnNonDefaultParams()throws Exception
    {
        TicTacToeBoard b = Mockito.mock(TicTacToeBoard.class);
        when(b.getColumns()).thenReturn(TicTacToeBoard.DEFAULT_COLUMNS+17);
        when(b.getRows()).thenReturn(TicTacToeBoard.DEFAULT_ROWS+15);

        TicTacToeGame g = new TicTacToeGame(TicTacToeGame.DEFAULT_PLAYERS+123, b);
        assertTrue("Game should be created with non default players",
                g.getNumberOfPlayers()==(TicTacToeGame.DEFAULT_PLAYERS+123) &&
                        g.getRows() == (TicTacToeBoard.DEFAULT_ROWS +15) &&
                        g.getColumns() == (TicTacToeBoard.DEFAULT_COLUMNS + 17));

    }

}