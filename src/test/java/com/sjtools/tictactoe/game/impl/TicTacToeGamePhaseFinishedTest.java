package com.sjtools.tictactoe.game.impl;

import com.sjtools.tictactoe.game.impl.TicTacToeGamePhaseFinished;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sjtools on 15.02.2017.
 */
public class TicTacToeGamePhaseFinishedTest {
    @Test
    public void canPlay_ShouldAlwaysReturnFalse() throws Exception {

        assertFalse(new TicTacToeGamePhaseFinished(null).canPlay());
    }

    @Test
    public void play_ShouldAlwaysReturnFalse() throws Exception {
        assertFalse(new TicTacToeGamePhaseFinished(null).play(0,0,0));
    }

}