package com.sjtools.tictactoe.utils;

import java.util.Scanner;

/**
 * Created by sjtools on 14.02.2017.
 */
public class TicTacToeGameConsoleInput implements TicTacToeGameInputIfc
{
    Scanner scanner = null;
    public TicTacToeGameConsoleInput()
    {
    }
    @Override
    public String getInput() throws EndOfAutoScannerInputException, IllegalStateException
    {

        if (scanner == null)
        {
            scanner = new Scanner(System.in);
        }
        return scanner.nextLine();
    }
}
