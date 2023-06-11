/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */


import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.world.World;


import javax.swing.JFrame;

/**
 * 
 * A <code>World</code> is the mediator between a grid and the GridWorld GUI.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class SudokuWorld<T> extends World<T>
{
    private JFrame myFrame;
    private SudokuWorldFrame sudokuFrame;
    /**
     * Stores the appropriate grid for this class. Is
     * accessed by the SudokuGrid class, which extends 
     * this class.
     */
    public SudokuGrid sudGrid;

    /**
     * 
     * @param gr
     */
    public SudokuWorld(SudokuGrid gr)
    {
        this(new BoundedGrid<>(9, 9));
        sudGrid = gr;
    }

    /**
     * 
     * @param g
     */

    public SudokuWorld(Grid<T> g)
    {
        super(g);
    }

    /**
     * 
     */

    public void show() {
        if (myFrame == null)
        {
            myFrame = new SudokuWorldFrame<T>(this);
            sudokuFrame = ((SudokuWorldFrame)myFrame);
            System.out.println(this);
            myFrame.setVisible(true);
        }
        else
            myFrame.repaint();
    }

    /**
     * 
     * @return
     */

    public SudokuWorldFrame<T> getFrame() {
        return sudokuFrame;
    }

    /**
     * 
     * @return
     */

    public SudokuGrid grid() {
        return sudGrid;
    }

}
