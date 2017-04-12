package com.sjtools.tictactoe.player;

/**
 * Created by Slawek on 4/12/2017.
 */
public enum TicTacToePlayerType
{
    COMPUTER("Computer"),
    HUMAN("Human");

    private String readableName;
    private TicTacToePlayerType(String readableName)
    {
        this.readableName = readableName;
    }
    @Override
    public String toString() {
        return this.readableName;
    }
}
