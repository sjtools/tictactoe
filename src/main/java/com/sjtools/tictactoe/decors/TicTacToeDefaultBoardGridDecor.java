package com.sjtools.tictactoe.decors;

import com.sjtools.tictactoe.ifc.TicTacToeBoardIfc;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by sjtools on 16.02.2017.
 */
public class TicTacToeDefaultBoardGridDecor implements TicTacToeBoardGridDecorIfc
{
    TicTacToeBoardGridCellValueDecorIfc cellDecor;
    String cellDelim = "|";
    public TicTacToeDefaultBoardGridDecor(TicTacToeBoardGridCellValueDecorIfc cellDecor)
    {
        this.cellDecor = cellDecor;
    }
    @Override
    public void displayGrid(PrintStream stream, TicTacToeBoardIfc board) {
        if (null == stream || null ==board || null==cellDecor)
            return ;
        for(int i = 0 ; i<board.getRows(); i++)
        {
            stream.print(cellDelim);
            for (int j=0; j< board.getColumns(); j++)
            {
                int v = board.getCellCheckValue((byte)i,(byte)j);
                cellDecor.displayGridCellValue(stream, v);
                stream.print(cellDelim);
            }
            stream.print("\n");
        }
    }
    public void setCellDelim(String delim)
    {
        cellDelim = delim;
    }
}
