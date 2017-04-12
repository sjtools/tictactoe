package com.sjtools.tictactoe.board.decors;

import com.sjtools.tictactoe.board.ifc.TicTacToeBoardIfc;

import java.io.PrintStream;

/**
 * Created by sjtools on 16.02.2017.
 */
public interface TicTacToeBoardDecorIfc
{
    /**
     * Draws board.
     * @param stream PrintStream to draw board to
     * @param board board to draw
     */
    void displayBoard(PrintStream stream, TicTacToeBoardIfc board);
}
