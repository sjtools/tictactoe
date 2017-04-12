package com.sjtools.tictactoe.player;

import com.sjtools.tictactoe.player.ifc.TicTacToePlayerIfc;
import com.sjtools.tictactoe.player.impl.TicTacToeDefaultComputerPlayer;
import com.sjtools.tictactoe.player.impl.TicTacToeDefaultHumanPlayer;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Slawek on 4/12/2017.
 */
public class TicTacToePlayerFactory {
    private static TicTacToePlayerFactory factory = null;
    private Map<TicTacToePlayerType, String> playerTypes = new HashMap<>();

    public static TicTacToePlayerFactory getInstance()
    {
        if (factory==null)
        {
            synchronized(TicTacToePlayerFactory.class)
            {
                if (factory==null)
                {
                    factory = new TicTacToePlayerFactory();
                    factory.loadConfig();
                }
            }
        }
        return factory;

    }

    private TicTacToePlayerFactory() {}

    private void loadConfig()
    {
        //can load types from config file here
        //
        //

        if (true)//there is no custom config, load defaults
            loadDefaultPlayerTypes();
        }

    private void loadDefaultPlayerTypes()
    {
        for (TicTacToePlayerType pType : TicTacToePlayerType.values())
        {
            if (pType == TicTacToePlayerType.COMPUTER)
                this.playerTypes.put(pType, TicTacToeDefaultComputerPlayer.class.getCanonicalName());
            else
                this.playerTypes.put(pType, TicTacToeDefaultHumanPlayer.class.getCanonicalName());

        }
    }

    public synchronized void reloadConfig()
    {
        loadConfig();
    }

    public synchronized TicTacToePlayerIfc createPlayer(TicTacToePlayerType type)
    {
        String playerClass = this.playerTypes.get(type);
        if (playerClass==null)
            throw new IllegalArgumentException("Player type " + type + " is not handled");
        Object player = null;
        try
        {
            player = Class.forName(playerClass).newInstance();
        }
        catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException e)
        {
            throw new IllegalStateException(e);
        }
        if (player instanceof TicTacToePlayerIfc)
            return (TicTacToePlayerIfc)player;
        else
            throw new IllegalStateException("Invalid player type instantiated. Expected implementation of " +
                    TicTacToePlayerIfc.class.getCanonicalName()+". " +
                    "Created " + player.getClass().getCanonicalName());
    }
}
