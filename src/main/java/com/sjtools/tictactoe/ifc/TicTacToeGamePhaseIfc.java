package com.sjtools.tictactoe.ifc;

/**
 * Created by sjtools on 11.02.2017.
 */
public interface TicTacToeGamePhaseIfc
{
    /**
     * must be implemented by each state to tell if play is possible
     * @return TRUE/FALSE
     */
    default boolean canPlay()
    {
        return false;
    }
    /**
     * @param playerId player playing
     * @param row cell row
     * @param col cell col
     * @return TRUE if could play, FALSE otherwise
     */
    boolean play(int playerId, int row, int col);
}
