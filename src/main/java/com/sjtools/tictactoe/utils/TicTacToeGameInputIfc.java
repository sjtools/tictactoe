package com.sjtools.tictactoe.utils;

/**
 * Created by sjtools on 14.02.2017.
 */
public interface TicTacToeGameInputIfc
{
    String getInput() throws EndOfAutoScannerInputException, IllegalStateException;
}
