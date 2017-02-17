package com.sjtools.tictactoe.ifc;

/**
 * Created by sjtools on 14.02.2017.
 */


public interface TicTacToeBoardActionsIfc {
    /**
     * Validate board size params
     * @param bRows
     * @param bColumns
     * @return TRUE if ok, FALSE otherwise
     */
    boolean validateBoardInitParams(int bRows, int bColumns);

    /**
     * Check cell.
     * Cell can be checked if params are valid and it is not checked yet
     * @param checkmark to use
     * @param cellRow   row coord to check
     * @param cellCol   cell coord to check
     * @return TRUE if successfully checked, FALSE otherwise
     */
    boolean checkCell(int checkmark, int cellRow, int cellCol);

    /**
     * clear board to initial state
     */
    void clearBoard();
}
