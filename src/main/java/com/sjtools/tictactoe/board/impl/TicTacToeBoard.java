/**
 * Created by sjtools on 09.02.2017.
 */
package com.sjtools.tictactoe.board.impl;


import com.sjtools.tictactoe.board.ifc.TicTacToeBoardIfc;

import java.io.PrintStream;

public class TicTacToeBoard implements TicTacToeBoardIfc {

    //default board settings
    public static int DEFAULT_ROWS = 3;
    public static int DEFAULT_COLUMNS = 3;


    //empty cell value
    protected static int EMPTY_CELL_VALUE = 0;
    //mangled check if originally equal empty cell
    protected static int MANGLED_CHECK_VALUE = TicTacToeBoardIfc.CELL_IS_EMPTY +1;

    // board size
    private int rows, columns;
    // actual board
    int board[][];

    // number of available checks
    private double numMovesAllowed;

    /**
     * Default constructor 3x3
     */
    public TicTacToeBoard()
    {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS);
    }
    /**
     * Construct board
     * @param bRows must be >0
     * @param bColumns must be >0
     * @throws IllegalArgumentException if params are invalid
     */
    public TicTacToeBoard(int bRows, int bColumns) throws IllegalArgumentException
    {
        if (!validateBoardInitParams(bRows,bColumns))
        {
            throw
                    new IllegalArgumentException(" "+
                    "Board must have rows>0, columns>0" +
                    " rows=" + bRows +
                    " columns=" + bColumns
                    );
        }
        this.rows = bRows;
        this.columns = bColumns;
        board = new int[rows][columns];
        clearBoard();
    }

    /**
     * Validate board size params
     * @param bRows
     * @param bColumns
     * @return TRUE if ok, FALSE otherwise
     */
    @Override
    public boolean validateBoardInitParams(int bRows, int bColumns)
    {
        return !(bRows < 1 || bColumns < 1);
    }

    @Override
    public void clearBoard()
    {
        for(int i=0; i<rows; i++)
            for(int j=0;j<columns;j++)
            {
                board[i][j] = EMPTY_CELL_VALUE;
            }
        numMovesAllowed = rows*columns;
    }

    /**
     * @return TRUE if board is diagonal (row==col and are odd value>1), FALSE otherwise
     */
    @Override
    public boolean isDiagonal()
    {
        return rows==columns && rows % 2 == 1 && rows>1;
    }

    /**
     * @return number of rows
     */
    @Override
    public int getRows()
    {
        return rows;
    }

    /**
     * @return number of columns
     */
    @Override
    public int getColumns()
    {
        return columns;
    }

    /**
     * @return number of possible checks still
     */
    @Override
    public double getNumberOfPossibleChecks()
    {
        return numMovesAllowed;
    }

    /**
     * Check cell.
     * Cell can be checked if params are valid and it is not checked yet
     * @param checkmark to use
     * @param cellRow   row coord to check
     * @param cellCol   cell coord to check
     * @return TRUE if successfully checked, FALSE otherwise {@link #getNumberOfPossibleChecks()} ,  {@link #isCoordsInBoardRange(int, int)}
     */
    @Override
    public boolean checkCell(int checkmark, int cellRow, int cellCol)
    {
        if (!isEmptyCell(cellRow, cellCol))
        {
            return false;
        }
        else
        {
            board[cellRow][cellCol] = mangleCheckMark(checkmark);
            this.numMovesAllowed--;
            return true;
        }
    }


    /**
     * Get cell value
     * @param cellRow
     * @param cellCol
     * @return cell value, CELL_IS_EMPTY if out of range {@link #isCoordsInBoardRange(int, int)}
     */
    @Override
    public int getCellCheckValue(int cellRow, int cellCol)
    {
        if (isEmptyCell(cellRow, cellCol) || !isCoordsInBoardRange(cellRow,cellCol))
        {
            return CELL_IS_EMPTY;
        }
        else
        {
            return unmangleCheckMark(board[cellRow][cellCol]);
        }
    }

    /**
     * Check if cell is empty
     * @param cellRow cell row coord
     * @param cellCol cell col coord
     * @return TRUE if cell is empty, FALSE if checked already or out of range {@link #isCoordsInBoardRange(int, int)}
     */
    @Override
    public boolean isEmptyCell(int cellRow, int cellCol)
    {
        if (!isCoordsInBoardRange(cellRow, cellCol))
        {
            return false;
        }
        return board[cellRow][cellCol] == EMPTY_CELL_VALUE ;
    }

    /**
     * Check if cell coords are in board range
     * @param cellRow cell row coord
     * @param cellCol cell col coord
     * @return TRUE if in board range, FALSE otherwise
     */
    public boolean isCoordsInBoardRange(int cellRow, int cellCol)
    {
        return !(cellRow < 0 || cellRow >= rows || cellCol < 0 || cellCol >= columns);
    }

    /**
     * get checkmark to avoid overriding empty cell value (0)
     * @param checkmark
     * @return checkmark to put into cell
     */
    protected int mangleCheckMark(int checkmark)
    {
        // 0 is empty cell, so checkmark==0 gets mangled
        return (checkmark== EMPTY_CELL_VALUE) ? MANGLED_CHECK_VALUE : checkmark;
    }

    /**
     * get real checkmark if it was mangled
     * @param checkmark
     * @return original value of a checkmark
     */
    protected int unmangleCheckMark(int checkmark)
    {
        // if checkmark was mangled, gets reverted into empty check value
        return (checkmark==MANGLED_CHECK_VALUE) ? EMPTY_CELL_VALUE : checkmark;
    }

    public void displayBoard(PrintStream stream)
    {
        if (stream ==null || board== null)
            return;
        String bFrame = "<= BOARD =>";
        StringBuilder sb = new StringBuilder();
        sb.append("(row0,col0)").append(bFrame);
        sb.append("(row0").append(",col").append(getColumns()-1).append(")\n");
        for(int i = 0 ; i<getRows(); i++)
        {
            sb.append("|");
            for (int j=0; j< getColumns(); j++)
            {
                int v = getCellCheckValue((byte)i,(byte)j);
                sb.append( (v == TicTacToeBoard.CELL_IS_EMPTY) ? " " : v);
                sb.append("|");
            }
            sb.append("\n");
        }
        sb.append("(row").append((getRows()-1)).append(",col0)");
        sb.append(bFrame);
        sb.append("(row").append((getRows()-1)).append(",col").append(getColumns()-1).append(")");
        stream.println(sb.toString());
    }
}
