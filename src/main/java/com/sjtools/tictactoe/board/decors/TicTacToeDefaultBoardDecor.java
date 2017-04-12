package com.sjtools.tictactoe.board.decors;

import com.sjtools.tictactoe.board.ifc.TicTacToeBoardIfc;

import java.io.PrintStream;

/**
 * Created by sjtools on 16.02.2017.
 */
public class TicTacToeDefaultBoardDecor implements TicTacToeBoardDecorIfc
{
    TicTacToeBoardGridDecorIfc gridDecor;

    public TicTacToeDefaultBoardDecor(TicTacToeBoardGridDecorIfc gridDecor)
    {
        this. gridDecor = gridDecor;
    }
    @Override
    public void displayBoard(PrintStream stream, TicTacToeBoardIfc board) {
        if (null == stream || null ==board || null==gridDecor)
            return ;
        String bFrame = "<= BOARD =>";
        StringBuilder sb = new StringBuilder();
        sb.append("(row0,col0)").append(bFrame);
        sb.append("(row0").append(",col").append(board.getColumns()-1).append(")\n");
        stream.println(sb.toString());
        gridDecor.displayGrid(stream, board);
        sb.setLength(0);
        sb.append("(row").append((board.getRows()-1)).append(",col0)");
        sb.append(bFrame);
        sb.append("(row").append((board.getRows()-1)).append(",col").append(board.getColumns()-1).append(")");
        stream.println(sb.toString());

    }
}
