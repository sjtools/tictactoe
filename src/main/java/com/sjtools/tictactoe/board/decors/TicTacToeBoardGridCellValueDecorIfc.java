package com.sjtools.tictactoe.board.decors;

import java.io.PrintStream;

/**
 * Created by sjtools on 16.02.2017.
 */
public interface TicTacToeBoardGridCellValueDecorIfc
{
    /**
     * draw single cell
     * @param stream PrintStream to write
     * @param cellValue cell value
     */
    void displayGridCellValue(PrintStream stream, int cellValue);
}
