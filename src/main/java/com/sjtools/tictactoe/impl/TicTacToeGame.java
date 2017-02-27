package com.sjtools.tictactoe.impl;

import com.sjtools.tictactoe.ifc.TicTacToeBoardIfc;
import com.sjtools.tictactoe.ifc.TicTacToeBoardPropertiesIfc;
import com.sjtools.tictactoe.ifc.TicTacToeGamePhaseIfc;

import java.util.*;

/**
 * Created by sjtools on 10.02.2017.
 */
public class TicTacToeGame implements TicTacToeGamePhaseIfc, TicTacToeBoardPropertiesIfc
{

    // board game is won/solved if cells are checked:
    //  - full row by the same player
    // - full column by the same player
    // - full diagonal by the same player (only when board row == columns and are odd value)
    //      diagonals are: up (top-left -> bottom-right) and down (bottom-left->top-right)

    public static int DEFAULT_PLAYERS = 2;

    // internal ids used to populate board game logic
    private static int INVALID_PLAYER = -1;
    private static int UP_DIAGONAL_OPTION = -1;
    private static int DOWN_DIAGONAL_OPTION = -2;

    //board players
    int players=0;

    // board
    TicTacToeBoardIfc board = null;

    //current player
    int currentPlayer = INVALID_PLAYER;

    //INVALID_PLAYER if not won yet
    int winner = INVALID_PLAYER;

    //possible winning options for players
    //Map<playerId, Map<optionId,optionValue>>
    Map<Integer,Map<String,Object>> winningOptionsForPlayers;

    //if there are any more winning options for any players
    boolean isWinningOptionAvailable = false;

    // game state
    TicTacToeGamePhaseIfc state = null;

    /**
     * Create game for given players and with given board
     * @param bPlayers must be >=DEFAULT_PLAYERS
     * @param aBoard board to play on must be !=NULL
     * @throws IllegalArgumentException if params are invalid
     */
    public TicTacToeGame(int bPlayers, TicTacToeBoardIfc aBoard) throws IllegalArgumentException
    {
        if (aBoard == null)
        {
            throw new IllegalArgumentException("Game must have non null board");
        }
        board = aBoard;
        if (bPlayers < DEFAULT_PLAYERS)
        {
            throw new IllegalArgumentException("Game must have >=" + DEFAULT_PLAYERS + " players. players=" + bPlayers);
        }
        this.players = bPlayers;
        initialize();
    }

    /**
     * Initialize game. Use any time to start over
     */
    public void initialize()
    {
        board.clearBoard();
        setCurrentPlayer(INVALID_PLAYER);
        setWinner(INVALID_PLAYER);
        generateWinningOptionsForPlayers();
        setState(new TicTacToeGamePhaseInProgress(this));
    }

    public void setState (TicTacToeGamePhaseIfc newState)
    {
        state = newState;
    }
    public TicTacToeGamePhaseIfc getState()
    {
        return state;
    }

    @Override
    public boolean canPlay() {
        return state.canPlay();
    }

    @Override
    public boolean play(int playerId, int row, int col) {
        return state.play(playerId, row, col);
    }

    /**
     * @return TRUE if already won, FALSE otherwise
     */
    public boolean isGameWon()
    {
        return getWinner()!=INVALID_PLAYER;
    }

    /**
     * Get current player, INVALID_PLAYER if game hasn't start yet
     * @return
     */
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Set current player
     * @param player to set
     */
    public void setCurrentPlayer(int player)
    {
        currentPlayer = player;
    }
    /**
     * Get next player:
     * - if current is INVALID_PLAYER (start) tben 0
     * - otherwise (current + 1 ) mod number of players
     * @return
     */
    public int getNextPlayer()
    {
        int nextp;
        if (currentPlayer == INVALID_PLAYER)
        {
            nextp = 0;
        }
        else
            nextp = (currentPlayer+1)%players;
        return nextp;
    }
    /**
     * @return number of players
     */
    public int getNumberOfPlayers()
    {
        return players;
    }

    /**
     * check if playerId is within a pool of players
     * @param playerId
     * @return TRUE if it is, FALSE otherwise
     */
    public boolean isValidPlayerId(int playerId)
    {
        return (playerId >= 0 && playerId < players);
    }
    /**
     * @return int who won, otherwise INVALID_PLAYER
     */
    public int getWinner()
    {
        return winner;
    }

    /**
     * set winner
     * @param player
     */
    public void setWinner(int player)
    {
        winner = player;
    }
    /**
     * @return number of rows
     */
    @Override
    public int getRows()
    {
        return board.getRows();
    }

    /**
     * @return number of columns
     */
    @Override
    public int getColumns()
    {
        return board.getColumns();
    }


    /**
     * check if winning options are still available
     * @return TRUE/FALSE
     */
    public boolean isWinningOptionAvailable()
    {
        return isWinningOptionAvailable;
    }

    /**
     * set flag about winning options still available
     * @param areWinningOptiosStill boolean
     */
    protected void setIsWinningOptionsAvailable(boolean areWinningOptiosStill)
    {
        isWinningOptionAvailable = areWinningOptiosStill;
    }
    /**
     * Get number of winning options for player.
     * @param playerId player to check options for
     * @return number of options, 0 if playerID is not valid {@link #isValidPlayerId(int)}
     */
    public int getNumOfWinningOptionAvailableForPlayer(int playerId)
    {
        if (!isValidPlayerId(playerId) || null==winningOptionsForPlayers.get(playerId))
        {
            return 0;
        }
        return winningOptionsForPlayers.get(playerId).size();
    }

    /**
     * Get winning options for player.
     * @param playerId player to check options for
     * @return Map of options <Integer,?>, empty if playerID is not valid or no options {@link #isValidPlayerId(int)}
     */
    public Map<String,?> getWinningOptionAvailableForPlayer(int playerId)
    {
        if (!isValidPlayerId(playerId) || null==winningOptionsForPlayers.get(playerId))
        {
            return new HashMap<>();
        }
        return winningOptionsForPlayers.get(playerId);
    }

    /**
     * @return number of possible checks still
     */
    @Override
    public double getNumberOfPossibleChecks()
    {
        return board.getNumberOfPossibleChecks();
    }

    @Override
    public boolean isDiagonal() {
        return board.isDiagonal();
    }

    @Override
    public boolean isEmptyCell(int cellRow, int cellCol) {
        return board.isEmptyCell(cellRow,cellCol);
    }

    @Override
    public boolean isCoordsInBoardRange(int cellRow, int cellCol) {
        return board.isCoordsInBoardRange(cellRow,cellCol);
    }

    /**
     * Populate winning options available for players
     */
    protected void generateWinningOptionsForPlayers()
    {
        winningOptionsForPlayers = new HashMap<>();
        for (int i = 0; i< players ; i++)
        {
            generateWinningOptionsForPlayer(i);
        }
        setIsWinningOptionsAvailable(checkWinningOptionsAvailable());
    }

    /**
     * Populate winning options for player. Does nothing if player not in pool of players
     * Upon initialization each player gets:
     * - one option per each row
     * - one option per each column
     * - one option per up and down diaqonal if board is diagonal
     * @param playerId is zero-based
     */
    protected void generateWinningOptionsForPlayer(int playerId)
    {
        Map<String,Object> winningOptions = new HashMap<>();
        winningOptionsForPlayers.put(playerId, winningOptions);
        //can win by checking each row
        for(int i = 0; i < board.getRows(); i ++)
        {
            winningOptions.put(getRowOptionKey(i) , getRows());
        }
        //can win by checking each column, unless this is 1x1 board for which
        // winning option was already accounted for in rows above
        if (board.getRows() != 1 && board.getColumns() !=1) {
            for (int i = 0; i < board.getColumns(); i++) {
                winningOptions.put(getColumnOptionKey(i), getColumns());
            }
        }
        //add diagonal winning options for diagonal board
        if (board.isDiagonal())
        {
            winningOptions.put(getDownDiagonalOptionKey(), getRows());
            winningOptions.put(getUPDiagonalOptionKey(), getRows());
        }
    }

    /**
     * Check cell.
     * Cell can be checked if params are valid and game is not already won and it is not checked yet
     * After check is done board is updated (may be winning check or board full or no more options)
     * @param playerId who checks (zero-based)
     * @param cellRow   row coord to check
     * @param cellCol   cell coord to check
     * @return TRUE if successfully checked, FALSE otherwise
     */
    public boolean checkCell(int playerId, int cellRow, int cellCol)
    {
        if (!isValidPlayerId(playerId) ||
                isGameWon() ||
                !board.checkCell(playerId, cellRow, cellCol))
        {
            return false;
        }
        else
        {
            updateOptionsPool(playerId, getOptionKeysForCell(cellRow, cellCol));
            return true;
        }
    }


    /**
     * Update board after check, based on options relevant to the check.
     * Does nothing if params are invalid
     * @param playerWhoChecked player to update board for
     * @param optionKeysForCell relevant options
     */
    protected void updateOptionsPool(int playerWhoChecked, List<String> optionKeysForCell)
    {
        if (!isValidPlayerId(playerWhoChecked) ||
                isGameWon() ||
                optionKeysForCell == null ||
                optionKeysForCell.size()==0)
            return;

        /* Per each relevant option:
            - update cells checked for that option for the player
            - if player checked all cells for that option, then set game as won already
            - remove that option for all other players (they cannot win here)
            - check if there are any winning options still
         */
        Map<String, Object> winningOptionsForPlayer = winningOptionsForPlayers.get(playerWhoChecked);
        for (String key : optionKeysForCell)
        {
            if (null!=winningOptionsForPlayer)
            {
                //update winning option for player who checked
                Integer optionValue = (Integer) winningOptionsForPlayer.get(key);
                if (optionValue != null) {
                    int unCheckedCellsInOption = optionValue - 1;
                    winningOptionsForPlayer.put(key, unCheckedCellsInOption);
                    if (unCheckedCellsInOption == 0) {//HURRAYYY :-))
                        setWinner(playerWhoChecked);
                    }
                }
            }
            for( int i =0; i < players ; i++)
            {//remove this option for all other players
                if (i==playerWhoChecked)
                {
                    continue;
                }
                if (null != winningOptionsForPlayers.get(i))
                {
                    winningOptionsForPlayers.get(i).remove(key);
                }
            }
        }
        //check if there are any winning options still
        setIsWinningOptionsAvailable(checkWinningOptionsAvailable());
    }

    protected boolean checkWinningOptionsAvailable() {
        //see if there are any winning options available still
        boolean checkWinOptions = false;
        for (int i = 0; i < players ; i++)
        {
            if (null != winningOptionsForPlayers.get(i) &&
                    winningOptionsForPlayers.get(i).size()>0)
            {//at least one has winning options still
                checkWinOptions = true;
                break;
            }
        }
        return checkWinOptions;
    }

    /**
     * get cell content
     * @param cellRow
     * @param cellCol
     * @return playerId who checked, or CELL_IS_EMPTY if cell is empty or coords out of range
     */
    @Override
    public int getCellCheckValue(int cellRow, int cellCol)
    {
        return board.getCellCheckValue(cellRow, cellCol);
    }


    /**
     * Get list of options keys for given cell:
     * - key for row option
     * - key for column option
     * - key for down diagonal if board is diagonal and cell is on down diagonal
     * - key for up diagonal if board is diagonal and cell is on up diagonal
     * @param cellRow cell row coord
     * @param cellCol cell col coord
     * @return List of option keys (empty if none or invalid params)
     */
    protected List<String> getOptionKeysForCell(int cellRow, int cellCol)
    {
        List<String> res = new LinkedList<>();
        res.add(getRowOptionKey(cellRow));
        res.add(getColumnOptionKey(cellCol));
        if (board.isCellOnDownDiagonal(cellRow,cellCol))
        {
            res.add(getDownDiagonalOptionKey());
        }
        if (board.isCellOnUpDiagonal(cellRow,cellCol))
        {
            res.add(getUPDiagonalOptionKey());
        }
        return res;
    }

    /**
     * get row option key
     * @param cellRow row to get its key for
     * @return option key relevant to given row
     */
    protected String getRowOptionKey(int cellRow)
    {

        return "r"+cellRow;
    }

    /**
     * get column option key
     * @param cellCol column to get its key for
     * @return option key relevant to given column
     */
    protected String getColumnOptionKey(int cellCol)
    {
        return "c"+cellCol;
    }

    /** get up diagonal option key
     * @return up diagonal option key
     */
    protected String getUPDiagonalOptionKey()
    {
        return "d"+UP_DIAGONAL_OPTION;
    }

    /**
     * get down diagonal option key
     * @return down diagonal option key
     */
    protected String getDownDiagonalOptionKey()
    {
        return "d"+DOWN_DIAGONAL_OPTION;
    }
}
