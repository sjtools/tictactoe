package com.sjtools.tictactoe.app;

import com.sjtools.tictactoe.board.decors.TicTacToeBoardDecorIfc;
import com.sjtools.tictactoe.board.decors.TicTacToeDefaultBoardDecor;
import com.sjtools.tictactoe.board.decors.TicTacToeDefaultBoardGridCellDecor;
import com.sjtools.tictactoe.board.decors.TicTacToeDefaultBoardGridDecor;
import com.sjtools.tictactoe.board.decors.*;
import com.sjtools.tictactoe.board.ifc.TicTacToeBoardIfc;
import com.sjtools.tictactoe.board.impl.TicTacToeBoard;
import com.sjtools.tictactoe.game.impl.TicTacToeGame;
import com.sjtools.tictactoe.utils.TicTacToeGameConsoleInput;
import com.sjtools.tictactoe.utils.TicTacToeGameInputIfc;

/**
 * Created by sjtools on 11.02.2017.
 */
public class TicTacToeGameApp
{

    TicTacToeGame game = null;
    TicTacToeBoardIfc board = null;
    TicTacToeBoardDecorIfc boardDisplayDecor = null;
    TicTacToeGameInputIfc inputScanner;

    public TicTacToeGameApp()
    {
        game = null;
    }

    public void setInputScanner(TicTacToeGameInputIfc scanner)
    {
        inputScanner = scanner;
    }

    protected boolean initialize()
    {
        int players = -1;
        int rows = 0;
        int cols =  0;

        System.out.println("\n");

        while (players < TicTacToeGame.DEFAULT_PLAYERS || players > 10) {
            System.out.print("Tell the number of players (2-10):");
            try {
                players = Integer.parseInt(getInput());
            } catch (NumberFormatException e) {
                System.out.println(e);
                //nothing to do
            }
        }
        while (rows <3) {
            System.out.print("Tell the board rows (3-255):");
            try {
                rows = Integer.parseInt(getInput());
            } catch (NumberFormatException e) {
                //nothing to do
            }
       }
        while (cols<3) {
            System.out.print("Tell the board columns (3-255):");
            try {
                cols = Integer.parseInt(getInput());
            } catch (NumberFormatException e) {
                //nothing to do
            }
        }


        System.out.print("Press <f> for fancy display. Default will be used otherwise.");
        setDisplayDecor("f".equalsIgnoreCase(getInput()));

        boolean result = false;
        try {
            board = new TicTacToeBoard(rows, cols);
            game = new TicTacToeGame(players,board);
            result = true;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            game = null;
            board = null;
        }
        return result;
    }

    private void setDisplayDecor(boolean fancyDecor)
    {
        TicTacToeDefaultBoardGridDecor gridDecor = new TicTacToeDefaultBoardGridDecor(
                                                        new TicTacToeDefaultBoardGridCellDecor());
        boardDisplayDecor = new TicTacToeDefaultBoardDecor(gridDecor);
        if (fancyDecor)
        {
            gridDecor.setCellDelim("||");
        }
    }

    public TicTacToeGame getGame() {
        return game;
    }

    protected boolean nextCheck()
    {
        game.setCurrentPlayer(game.getNextPlayer());
        int nextWinnigOptionsStill = game.getNumOfWinningOptionAvailableForPlayer(game.getCurrentPlayer());
        System.out.println("Player <" + game.getCurrentPlayer() + "> it's your move.");
        if (nextWinnigOptionsStill==0)
        {
            System.out.println("Unfortunately you cannot win. But, you can make game harder for other players.");
        }
        while (true) {
            System.out.println("Press <q> to finish the game or <row,col> (e.g. 0,0) to check a cell:");
            String in = getInput();
            if ("q".equalsIgnoreCase(in))
            {
                return false;
            }
            int coords[] = {-1,-1};
            getCoord(in, coords);
            if (!game.isCoordsInBoardRange(coords[0],coords[1]))
            {
                System.out.println("Coordinates are not in board's range.");
                continue;
            }
            if (!game.isEmptyCell(coords[0],coords[1]))
            {
                System.out.println("Cell is already checked.");
                continue;
            }
            if (game.play(game.getCurrentPlayer(), coords[0], coords[1]))
            {//checked
                break;
            }
        }
        return true;
    }

    protected void getCoord(String in, int[] coords)
    {
        if (coords==null || coords.length!=2)
            return;
        try
        {
            String s[] = in.split(",");
            coords[0] = Integer.parseInt(s[0]);
            coords[1] = Integer.parseInt(s[1]);
        }
        catch(NumberFormatException e)
        {
            //nothing to do
        }
    }

    protected void printResult()
    {
        System.out.println("GAME FINISHED");
        if (game.isGameWon())
        {
            System.out.println("Player <" + game.getWinner() + "> won. Congratulations!!!!");
        }
        else
        {
            if (!game.isWinningOptionAvailable())
                System.out.println("No more winning options available for players.");
            if (game.getNumberOfPossibleChecks() == 0)
                System.out.println("Board is full.");
        }

    }

    protected void displayBoard()
    {
        boardDisplayDecor.displayBoard(System.out, board);
    }


    protected String getInput()
    {
        if (inputScanner == null)
        {
            return null;
        }
        return inputScanner.getInput();
    }

    public void playTicTacToeGame()
    {
        System.out.println("\n\n");
        System.out.println("==============================================================================");
        System.out.println("                       Welcome to TicTacToe game.");
        System.out.println("==============================================================================");
        System.out.println("Number of players: 2 to 10.");
        System.out.println("        Player Id will be from <0> to <number_of_players - 1>");
        System.out.println("Board size:  3x3 to 255x255 (rows x columns).");
        System.out.println("        Cell coordinates: top-right=(0,0) -> bottom-left=(rows-1, columns-1).");
        System.out.println("\n\n");
        while(true) {
            while (true) {
                System.out.println("Press <n> to start a new game.");
                System.out.print("Press <q> to quit.");
                String in = getInput();
                if ("n".equalsIgnoreCase(in)) {
                    if (initialize())
                    {
                        break;
                    }
                }
                if ("q".equals(in)) {
                    System.out.printf("BYE");
                    return;
                }
            }
            while (getGame().canPlay()) {
                displayBoard();
                if (!nextCheck()) {
                    break;
                }
            }
            displayBoard();
            printResult();
            System.out.printf("\n");
        }
    }
    public static void main(String[] args)
    {
        TicTacToeGameApp app = new TicTacToeGameApp();
        app.setInputScanner(new TicTacToeGameConsoleInput());
        app.playTicTacToeGame();
    }
}
