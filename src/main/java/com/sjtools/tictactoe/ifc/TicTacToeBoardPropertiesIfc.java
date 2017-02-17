package com.sjtools.tictactoe.ifc;

/**
 * Created by sjtools on 14.02.2017.
 */
public interface TicTacToeBoardPropertiesIfc {

    //returned as value for an empty cell
    int CELL_IS_EMPTY = Integer.MIN_VALUE;

    /**
     * @return TRUE if board is diagonal (row==col and are odd value>1), FALSE otherwise
     */
    boolean isDiagonal();

    /**
     * @return number of rows
     */
    int getRows();

    /**
     * @return number of columns
     */
    int getColumns();

    /**
     * @return number of possible checks still
     */
    double getNumberOfPossibleChecks();

    /**
     * Get cell value
     * @param cellRow
     * @param cellCol
     * @return cell value, CELL_IS_EMPTY if out of range {@link #isCoordsInBoardRange(int, int)}
     */
    int getCellCheckValue(int cellRow, int cellCol) throws IllegalArgumentException;

    /**
     * Check if cell is empty
     * @param cellRow cell row coord
     * @param cellCol cell col coord
     * @return TRUE if cell is empty, FALSE if checked already or out of range {@link #isCoordsInBoardRange(int, int)}
     */
    boolean isEmptyCell(int cellRow, int cellCol);

    /**
     * Check if cell coords are in board range
     * @param cellRow cell row coord
     * @param cellCol cell col coord
     * @return TRUE if in board range, FALSE otherwise
     */
    boolean isCoordsInBoardRange(int cellRow, int cellCol);

    /**
     * Check if cell is on down diagonal of the board
     * Board must be diagonal and cell must be in range to check it's position
     * @param cellRow
     * @param cellCol
     * @return TRUE if it is, FALSE otherwise
     */
    default boolean isCellOnDownDiagonal(int cellRow, int cellCol)
    {
        return isCoordsInBoardRange(cellRow, cellCol) &&
                isDiagonal() &&
                cellRow == cellCol;
    }
    /**
     * Check if cell is on up diagonal of the board
     * Board must be diagonal and cell must be in range to check it's position
     * @param cellRow
     * @param cellCol
     * @return TRUE if it is, FALSE otherwise
     */
    default boolean isCellOnUpDiagonal(int cellRow, int cellCol)
    {
        return isCoordsInBoardRange(cellRow, cellCol) &&
                isDiagonal() &&
                    (cellRow == (-1)*cellCol+ (getRows()-1));

    }

}
