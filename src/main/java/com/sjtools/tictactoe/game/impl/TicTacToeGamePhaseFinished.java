package com.sjtools.tictactoe.game.impl;

import com.sjtools.tictactoe.game.ifc.TicTacToeGamePhaseIfc;

/**
 * Created by sjtools on 13.02.2017.
 */
public class TicTacToeGamePhaseFinished implements TicTacToeGamePhaseIfc
{
    TicTacToeGame game;
    public TicTacToeGamePhaseFinished(TicTacToeGame aGame)
    {
        game = aGame;
    }

    @Override
    public boolean canPlay() {
        return false;
    }

    @Override
    public boolean play( int playerId, int row, int col) {
        return false;
    }

}
