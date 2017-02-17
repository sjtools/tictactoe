package com.sjtools.tictactoe.impl;

import com.sjtools.tictactoe.ifc.TicTacToeBoardPropertiesIfc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 * Created by sjtools on 14.02.2017.
 */
public class TicTacToeBoardTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected =  IllegalArgumentException.class)
    public void constructor_ShouldRaiseThrowIllegaArgumentExceptionOnInvalidRow()throws Exception
    {
        new TicTacToeBoard(0, TicTacToeBoard.DEFAULT_COLUMNS);
    }
    @Test(expected =  IllegalArgumentException.class)
    public void constructor_ShouldRaiseThrowIllegaArgumentExceptionOnInvalidColumn()throws Exception
    {
        new TicTacToeBoard(TicTacToeBoard.DEFAULT_ROWS, 0);
    }
    @Test(expected =  IllegalArgumentException.class)
    public void constructor_ShouldRaiseThrowIllegaArgumentExceptionOnInvalidRowAndColumn()throws Exception
    {
        new TicTacToeBoard(0, 0);
    }
    @Test
    public void defConstructor_ShouldCreateBoard()throws Exception
    {
        TicTacToeBoard b = null;
        String msg = "";
        try {
            b = new TicTacToeBoard();
        }
        catch(Exception e)
        {
            msg = e.getMessage();
        }
        assertNotNull(msg,b);
    }
    @Test
    public void constructor_ShouldCreateBoardWithValidNonDefaultParams()throws Exception
    {
        TicTacToeBoard b = null;
        String msg = "";
        try {
            b = new TicTacToeBoard(TicTacToeBoard.DEFAULT_ROWS+100, TicTacToeBoard.DEFAULT_COLUMNS+10);
        }
        catch(Exception e)
        {
            msg = e.getMessage();
        }
        assertNotNull(msg,b);
    }
    @Test
    public void getRowsAndgetColumns_ShouldReportValuesGivenInConstructor()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(100,10);
        assertThat(Arrays.asList(100, 10), hasItems(b.getRows(), b.getColumns()));
    }

    @Test
    public void isDiagonal_ShouldReportFalseForEqualRowsAndColsAndOdd()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(99,99);
        assertTrue(b.isDiagonal());
    }

    @Test
    public void isDiagonal_ShouldReportFalseForEqualRowsAndColsButEven()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(100,100);
        assertFalse(b.isDiagonal());
    }


    @Test
    public void isDiagonal_ShouldReportFalseForNonEqualRowsAndCols()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(100,99);
        assertFalse(b.isDiagonal());
    }

    @Test
    public void getNumberOfPossibleChecks_shouldReportRowsTimesColumnsAfterInitialization()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertTrue(12*7==b.getNumberOfPossibleChecks());
    }

    @Test
    public void mangle_ShouldReportMangledValueForCheckmarkToMangle()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertEquals(b.mangleCheckMark(TicTacToeBoard.EMPTY_CELL_VALUE), TicTacToeBoard.MANGLED_CHECK_VALUE);
    }

    @Test
    public void mangle_ShouldReportCheckmarkNotToMangleValueOnInput()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertEquals(b.mangleCheckMark(TicTacToeBoard.EMPTY_CELL_VALUE +1), TicTacToeBoard.EMPTY_CELL_VALUE +1);
    }

    @Test
    public void unmangle_ShouldReportCheckmarkToMangleValueIfProperlyMangled()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertEquals(b.unmangleCheckMark(TicTacToeBoard.MANGLED_CHECK_VALUE), TicTacToeBoard.EMPTY_CELL_VALUE);
    }

    @Test
    public void manglThenUnmangle_ShouldReportTheSameValueForCheckmarkToMangle()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertEquals(TicTacToeBoard.EMPTY_CELL_VALUE,
                b.unmangleCheckMark(b.mangleCheckMark(TicTacToeBoard.EMPTY_CELL_VALUE)));
    }
    @Test
    public void manglThenUnmangle_ShouldReportTheSameValueForCheckmarkNotToMangle()throws Exception
    {
        TicTacToeBoard b = new TicTacToeBoard(12,7);
        assertEquals(TicTacToeBoard.EMPTY_CELL_VALUE +1,
                b.unmangleCheckMark(b.mangleCheckMark(TicTacToeBoard.EMPTY_CELL_VALUE +1)));
    }

    @Test
    public void isCoordsInBoardRange_ShouldReportFalseForCellsOutOfRange()
    {
        int row = 10;
        int col = 11;
        TicTacToeBoard b = new TicTacToeBoard(row,col);
        assertThat(Arrays.asList(false, false,false,false,false,false, false,false,false,false),
                        hasItems(
                                b.isCoordsInBoardRange(-1, -1), //both coords out of range
                                b.isCoordsInBoardRange(row, col),
                                b.isCoordsInBoardRange(row-1, -1), //row in range, col out of range
                                b.isCoordsInBoardRange(0, -1),
                                b.isCoordsInBoardRange(row-1, col),
                                b.isCoordsInBoardRange(0, col),
                                b.isCoordsInBoardRange(-1, col-1), //row out of range, col in range
                                b.isCoordsInBoardRange(row, col-1),
                                b.isCoordsInBoardRange(-1, 0),
                                b.isCoordsInBoardRange(row, 0)
                                ));
    }

    @Test
    public void isCoordsInBoardRange_ShouldReportTrueForCellsInRange()
    {
        int row = 10;
        int col = 11;
        TicTacToeBoard b = new TicTacToeBoard(row,col);
        assertThat(Arrays.asList(true, true,true,true,true,true, true,true,true),
                hasItems(
                        b.isCoordsInBoardRange(0, 0),             //corners
                        b.isCoordsInBoardRange(row-1, 0),
                        b.isCoordsInBoardRange(row-1, col-1),
                        b.isCoordsInBoardRange(0, col-1),
                        b.isCoordsInBoardRange(0, 1), //outer row/col
                        b.isCoordsInBoardRange(1, col-1),
                        b.isCoordsInBoardRange(row-1, 1),
                        b.isCoordsInBoardRange(1, 1),
                        b.isCoordsInBoardRange(1, 1) //somewhere in the middle
                ));
    }

 @Test
 public void isEmptyCell_ShouldReportFalseForAllCellsAfterInitialization() throws Exception
 {
     int row = 2;
     int col = 2;
     TicTacToeBoard board = new TicTacToeBoard(row,col);
     boolean res = true;
     for(int i = 0; i<row; i++)
         for(int j=0; j<col ; j++)
         {
             res &= board.isEmptyCell(i,j);
         }
     assertTrue("All cells should be empty upon init",res);
 }
    @Test
    public void checkCell_ShouldReturnTrueForCellWithCheckmarkToMangle() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        boolean check = board.checkCell(TicTacToeBoard.EMPTY_CELL_VALUE,1,1);
        boolean res = board.isEmptyCell(1,1);
        assertThat("Cell with checkmark==EMPTY_CELL_VALUE is checked and is not empty. ",
                Arrays.asList(true, false),hasItems(check,res));
    }
    @Test
    public void checkCell_ShouldReturnTrueForCellWithCheckmarkNotToMangle() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        boolean check = board.checkCell(TicTacToeBoard.EMPTY_CELL_VALUE +1,1,1);
        boolean res = board.isEmptyCell(1,1);
        assertThat("Cell with checkmark==EMPTY_CELL_VALUE is checked and is not empty. ",
                Arrays.asList(true, false),hasItems(check,res));
    }
    @Test
    public void checkCell_ShouldReturnFalseOnOutOfRangeCell() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        boolean check = board.checkCell(TicTacToeBoard.EMPTY_CELL_VALUE +1,row,col);
        assertFalse(check);
    }
    @Test
    public void getCellCheckValue_ShouldReturnValidValueIfCheckmarkMangled() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        board.checkCell(TicTacToeBoard.EMPTY_CELL_VALUE,1,1);
        assertEquals("Cell value should equal check value after cell is checked if checkmark eq empty",
                TicTacToeBoard.EMPTY_CELL_VALUE,board.getCellCheckValue(1,1));
    }
    @Test
    public void getCellCheckValue_ShouldReturnValidValueIfCheckmarkNotMangled() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        board.checkCell(TicTacToeBoard.EMPTY_CELL_VALUE +1,1,1);
        assertEquals("Cell value should equal check value after cell is checked if checkmark not eq empty",
                TicTacToeBoard.EMPTY_CELL_VALUE +1,board.getCellCheckValue(1,1));
    }
    @Test
    public void getCellCheckValue_ShouldReturnEmptyOnEmptyCellInRange() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        assertEquals("Cell value should be properly reported",
                TicTacToeBoardPropertiesIfc.CELL_IS_EMPTY,board.getCellCheckValue(1,1));
    }
    @Test
    public void getCellCheckValue_ShouldReturnEmptyForOutOfRangeCell() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        assertEquals("Out of range cell value should be reported as empty",
                TicTacToeBoardPropertiesIfc.CELL_IS_EMPTY,board.getCellCheckValue(row,col));
    }

    @Test
    public void clearBoard_ShouldEmptyAllCells() throws Exception
    {
        int row = 2;
        int col = 2;
        TicTacToeBoard board = new TicTacToeBoard(row,col);
        //check all cells
        for(int i = 0; i<row; i++)
            for(int j=0; j<col ; j++)
            {
                board.checkCell(123,i,j);
            }
        //clear
        board.clearBoard();
        //validate if empty
        boolean res = true;
        for(int i = 0; i<row; i++)
            for(int j=0; j<col ; j++)
            {
                res &= board.isEmptyCell(i,j);
            }
        assertThat(Arrays.asList(true,true),
                hasItems(res,board.getNumberOfPossibleChecks()==row*col));
    }

}