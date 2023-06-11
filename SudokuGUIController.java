/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 

 */

import info.gridworld.grid.*;
import info.gridworld.gui.DisplayMap;
import info.gridworld.world.World;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JComboBox;

import javax.swing.*;

/**
 * The GUIController controls the behavior in a WorldFrame. <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */

public class SudokuGUIController<T> {
    public static final int INDEFINITE = 0, FIXED_STEPS = 1, PROMPT_STEPS = 2;

    private static final int MIN_DELAY_MSECS = 10, MAX_DELAY_MSECS = 1000;
    private static final int INITIAL_DELAY = MIN_DELAY_MSECS
            + (MAX_DELAY_MSECS - MIN_DELAY_MSECS) / 2;

    private Timer timer;

    /**
     * These are buttons that are used in the game and whether
     * they are on or off has to be controlled by the other classes.
     */
    public JButton notesButton, undoButton, clearButton;
    private JComponent controlPanel;
    private SudokuGridPanel display;
    private SudokuWorldFrame<T> parentFrame;
    private int numStepsToRun, numStepsSoFar;
    private ResourceBundle resources;
    private DisplayMap displayMap;
    private boolean running;
    private Set<Class> occupantClasses;
    /**
     * Our dropdown box to select level of game
     * for Sudoku. Other classes need access to it
     * to easily enable and disable it.
     */
    public JComboBox box;

    /**
     * Creates a new controller tied to the specified display and gui
     * frame.
     * 
     * @param parent     the frame for the world window
     * @param disp       the panel that displays the grid
     * @param displayMap the map for occupant displays
     * @param res        the resource bundle for message display
     */
    public SudokuGUIController(SudokuWorldFrame<T> parent, SudokuGridPanel disp,
            DisplayMap displayMap, ResourceBundle res) {
        // System.out.println("SUDOKU CONSTRUCTOR CALLED");
        resources = res;
        display = disp;
        parentFrame = parent;
        this.displayMap = displayMap;

        makeControls();

        occupantClasses = new TreeSet<Class>(new Comparator<Class>() {
            public int compare(Class a, Class b) {
                return a.getName().compareTo(b.getName());
            }
        });

        World<T> world = parentFrame.getWorld();
        Grid<T> gr = world.getGrid();
        for (Location loc : gr.getOccupiedLocations())
            addOccupant(gr.get(loc));
        for (String name : world.getOccupantClasses())
            try {
                occupantClasses.add(Class.forName(name));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        // timer = new Timer(INITIAL_DELAY, new ActionListener() {
        // public void actionPerformed(ActionEvent evt) {
        // setNotesMode();
        // }
        // });

        display.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                Grid<T> gr = parentFrame.getWorld().getGrid();
                Location loc = display.locationForPoint(evt.getPoint());
                if (loc != null && gr.isValid(loc) && !isRunning()) {
                    display.setCurrentLocation(loc);
                    locationClicked();
                }
            }
        });
        // clear();
    }

    /**
     * Toggles notes mode whenever the
     * "Notes" button is pressed.
     */
    public void setNotesMode() {
        // parentFrame.getWorld().step();
        // parentFrame.repaint();
        if (SudokuGrid.notes) {
            SudokuGrid.notes = false;
            notesButton.setText("Notes (off)");
        } else {
            SudokuGrid.notes = true;
            notesButton.setText("Notes (on)");
        }
    }

    private void addOccupant(T occupant) {
        Class cl = occupant.getClass();
        do {
            if ((cl.getModifiers() & Modifier.ABSTRACT) == 0)
                occupantClasses.add(cl);
            cl = cl.getSuperclass();
        } while (cl != Object.class);
    }

    /**
     * "Undoes" the previously typed number by
     * removing it from SudokuGrid. 
     */
    public void undo() {
        System.out.println("undo happening");
        // System.out.println(grid.moves);
        Location loc = SudokuGrid.moves.pop();
        System.out.println(loc);
        System.out.println(Sudoku.gr.remove(loc));
        if (SudokuGrid.moves.isEmpty()) {
            undoButton.setEnabled(false);
            clearButton.setEnabled(false);
        }
        System.out.println("undo done");
    }

    /**
     * Clears all the numbers typed ONLY by the user
     * by removing them from the grid.
     */
    public void clear() {
        clearButton.setEnabled(false);
        undoButton.setEnabled(false);
        while (!SudokuGrid.moves.isEmpty()) {
            Sudoku.gr.remove(SudokuGrid.moves.pop());
        }
        SudokuGrid.attempts = 5;
        Sudoku.gr.setMessage("Attempts have been reset to " + SudokuGrid.attempts + ".");
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Builds the panel with the various controls (buttons and
     * the dropdown).
     */
    private void makeControls() {
        controlPanel = new JPanel();
        notesButton = new JButton("Notes (off)");
        undoButton = new JButton("Undo");
        clearButton = new JButton("Clear");
        String[] levels = { "New Game (select difficulty)", "Easy", "Medium", "Hard" };
        box = new JComboBox<>(levels);

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createEtchedBorder());

        Dimension spacer = new Dimension(5, notesButton.getPreferredSize().height + 10);

        controlPanel.add(Box.createRigidArea(spacer));

        controlPanel.add(notesButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(undoButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(clearButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(box);
        undoButton.setEnabled(false);
        notesButton.setEnabled(true);
        clearButton.setEnabled(false);

        controlPanel.add(Box.createRigidArea(spacer));

        notesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNotesMode();
            }
        });
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        box.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int i = box.getSelectedIndex();
                        if (i == 1) {
                            // generate easy grid
                            System.out.println("easy");
                            GridGenerator.generate();
                            GridGenerator.removeNumbers("easy");
                            box.setEnabled(false);
                            notesButton.setEnabled(true);
                            SudokuGrid.gameRunning = true;
                        } 
                        else if (i == 2) {
                            // generate medium grid
                            System.out.println("medium");
                            GridGenerator.generate();
                            GridGenerator.removeNumbers("medium");
                            //MediumGrid.removeNumbers();
                            box.setEnabled(false);
                            notesButton.setEnabled(true);
                            SudokuGrid.gameRunning = true;
                        } 
                        else if (i == 3) {
                            // generate hard grid
                            System.out.println("hard");
                            GridGenerator.generate();
                            GridGenerator.removeNumbers("hard");
                            //HardGrid.removeNumbers();
                            box.setEnabled(false);
                            notesButton.setEnabled(true);
                            SudokuGrid.gameRunning = true;
                        }
                    }
                });
    }

    /**
     * Returns the panel containing the controls.
     * 
     * @return the control panel
     */
    public JComponent controlPanel() {
        return controlPanel;
    }

    /**
     * Callback on mousePressed when editing a grid.
     */
    private void locationClicked() {
        World<T> world = parentFrame.getWorld();
        Location loc = display.getCurrentLocation();
        parentFrame.repaint();
    }

    /**
     * Edits the contents of the current location, by displaying the constructor
     * or method menu.
     */
    public void editLocation() {
        World<T> world = parentFrame.getWorld();

        Location loc = display.getCurrentLocation();
        if (loc != null) {
            T occupant = world.getGrid().get(loc);
            if (occupant == null) {
                SudokuMenuMaker<T> maker = new SudokuMenuMaker<T>(parentFrame, resources,
                        displayMap);
                JPopupMenu popup = maker.makeConstructorMenu(occupantClasses,
                        loc);
                Point p = display.pointForLocation(loc);
                popup.show(display, p.x, p.y);
            } else {
                SudokuMenuMaker<T> maker = new SudokuMenuMaker<T>(parentFrame, resources,
                        displayMap);
                JPopupMenu popup = maker.makeMethodMenu(occupant, loc);
                Point p = display.pointForLocation(loc);
                popup.show(display, p.x, p.y);
            }
        }
        parentFrame.repaint();
    }

    /**
     * Edits the contents of the current location, by displaying the constructor
     * or method menu.
     */
    public void deleteLocation() {
        World<T> world = parentFrame.getWorld();
        Location loc = display.getCurrentLocation();
        if (loc != null) {
            world.remove(loc);
            parentFrame.repaint();
        }
    }
}
