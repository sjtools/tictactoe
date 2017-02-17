package com.sjtools.tictactoe.decors;

import com.sjtools.tictactoe.ifc.TicTacToeBoardPropertiesIfc;

import java.io.PrintStream;

/**
 * Created by sjtools on 16.02.2017.
 */
public class TicTacToeDefaultBoardGridCellDecor implements TicTacToeBoardGridCellValueDecorIfc
{
    public TicTacToeDefaultBoardGridCellDecor()
    {
    }
    @Override
    public void displayGridCellValue(PrintStream stream, int cellValue) {
        if (null == stream)
            return;

        StringBuilder sb = new StringBuilder();
        sb.append((cellValue == TicTacToeBoardPropertiesIfc.CELL_IS_EMPTY ? " " : cellValue));
        stream.print(sb.toString());
    }
}
