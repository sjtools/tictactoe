package com.sjtools.tictactoe.utils;

import com.sjtools.tictactoe.app.TicTacToeGameApp;

import java.util.*;

/**
 * Created by sjtools on 14.02.2017.
 */
public class TicTacToeGameAutomatedInput implements TicTacToeGameInputIfc
{
    Map<String, TicTacToeGameAutomatedInput> scannerMap = new HashMap<>();
    Queue<String> scanner = null;
    boolean endOfInputExAlreadyIssued = false;
    public TicTacToeGameAutomatedInput()
    {
    }

    @Override
    public String getInput() throws EndOfAutoScannerInputException
    {
        String res = null;
        if (scanner!=null)
            res = scanner.poll();
        else
            res =  null;
        if (res == null)
        {
            if (!endOfInputExAlreadyIssued)
            {
                endOfInputExAlreadyIssued = true;
                //try to give control back to app
                throw new EndOfAutoScannerInputException("End of auto input scenario reached");
            }
            else
            {//this is needed as otherwise app may be stuck in input loop forever
                throw new IllegalStateException("AutomatedInput invoked past the scenario. Hardstop needed.");
            }
        }
        return res;
    }



    public void feedScannerMap(String scannerId, Queue<String> listOfInputs)
    {
        TicTacToeGameAutomatedInput newScanner = new TicTacToeGameAutomatedInput();
        newScanner.feedScanner(listOfInputs);
        scannerMap.put(scannerId, newScanner);
    }

    private void feedScanner(Queue<String> listOfInputs)
    {
        scanner = listOfInputs;
    }
    public TicTacToeGameAutomatedInput getScannerFromMap(String scannerID)
    {
        return scannerMap.get(scannerID);
    }

    public static void main(String[] args)
    {
        TicTacToeGameApp app = new TicTacToeGameApp();
        TicTacToeGameAutomatedInput autoInput = new TicTacToeGameAutomatedInput();
        Queue<String> q = new LinkedList<>();
        q.add("n");
        q.add("2");
        autoInput.feedScannerMap("0",q);

        q = new LinkedList<>();
        q.add("n");
        q.add("2");
        q.add("3");
        q.add("3");
        q.add("x");
        q.add("1,1");
        q.add("0,0");
        q.add("q");
        q.add("q");
        autoInput.feedScannerMap("1",q);

        app.setInputScanner(autoInput.getScannerFromMap("0"));
        app.playTicTacToeGame();

        app = new TicTacToeGameApp();
        app.setInputScanner(autoInput.getScannerFromMap("1"));
        app.playTicTacToeGame();

    }
}
