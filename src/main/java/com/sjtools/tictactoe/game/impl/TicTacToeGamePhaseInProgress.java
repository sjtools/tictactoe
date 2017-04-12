package com.sjtools.tictactoe.game.impl;

import com.sjtools.tictactoe.game.ifc.TicTacToeGamePhaseIfc;

/**
 * Created by sjtools on 12.02.2017.
 */
public class TicTacToeGamePhaseInProgress implements TicTacToeGamePhaseIfc
{
    TicTacToeGame game;
    public TicTacToeGamePhaseInProgress(TicTacToeGame aGame)
    {
        game = aGame;
    }
    @Override
    public boolean canPlay() {
        return true;
    }

    @Override
    public boolean play(int playerId, int row, int col) {
        if (game.checkCell(playerId, row, col))
        {//checked
            if (game.isGameWon() ||
                    !game.isWinningOptionAvailable() ||
                    game.getNumberOfPossibleChecks()==0)
            {
                game.setState(new TicTacToeGamePhaseFinished(game));
            }
                return true;
        }
        return false;
    }

}
