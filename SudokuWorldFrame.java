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
import info.gridworld.grid.Location;
import info.gridworld.gui.DisplayMap;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.World;
import info.gridworld.gui.*;
import info.gridworld.gui.GUIController;
import info.gridworld.gui.GridPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The WorldFrame displays a World and allows manipulation of its occupants.
 * <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */
public class SudokuWorldFrame<T> extends JFrame {
    /**
     * SudokuGUIController provides access to all
     * controls of the GUI, such as buttons, etc.
     */
    public SudokuGUIController<T> myControl;
    private JTextArea myMessageArea;
    private SudokuGridPanel myDisplay;
    private ResourceBundle myResources;
    private DisplayMap myDisplayMap;

    private ArrayList<JMenuItem> myMenuItemsDisabledDuringRun;
    private SudokuWorld<T> myWorld;

    private Set<Class> myGridClasses;
    private JMenu myNewGridMenu;

    private static int myCount = 0;

    /**
     * Constructs a WorldFrame that displays the occupants of a world
     * 
     * @param world the world to display
     */
    public SudokuWorldFrame(SudokuWorld<T> world) {
        System.out.println("Done with super constructor");
        myWorld = world;
        myCount++;
        myResources = ResourceBundle
                .getBundle(getClass().getName() + "Resources");

        try {
            System.setProperty("sun.awt.exception.handler",
                    GUIExceptionHandler.class.getName());
        } catch (SecurityException ex) {
            // will fail in an applet
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                myCount--;
                if (myCount == 0)
                    System.exit(0);
            }
        });

        myDisplayMap = new DisplayMap();
        String title = System.getProperty("info.gridworld.gui.frametitle");
        if (title == null)
            title = myResources.getString("frame.title");
        setTitle(title);
        setLocation(25, 15);

        URL appIconUrl = getClass().getResource("GridWorld.gif");
        ImageIcon appIcon = new ImageIcon(appIconUrl);
        setIconImage(appIcon.getImage());

        makeMenus();

        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.setLayout(new BorderLayout());
        setContentPane(content);

        myDisplay = new SudokuGridPanel(myDisplayMap, myResources);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (getFocusOwner() == null)
                    return false;
                String text = KeyStroke.getKeyStrokeForEvent(event).toString();
                final String PRESSED = "pressed ";
                int n = text.indexOf(PRESSED);
                if (n < 0)
                    return false;
                // filter out modifier keys; they are neither characters or actions
                if (event.getKeyChar() == KeyEvent.CHAR_UNDEFINED && !event.isActionKey())
                    return false;
                text = text.substring(0, n) + text.substring(n + PRESSED.length());
                boolean consumed = getWorld().keyPressed(text, myDisplay.getCurrentLocation());
                if (consumed)
                    repaint();
                return consumed;
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewport(new PseudoInfiniteViewport(scrollPane));
        scrollPane.setViewportView(myDisplay);
        content.add(scrollPane, BorderLayout.CENTER);

        myGridClasses = new TreeSet<Class>(new Comparator<Class>() {
            public int compare(Class a, Class b) {
                return a.getName().compareTo(b.getName());
            }
        });
        for (String name : world.getGridClasses())
            try {
                myGridClasses.add(Class.forName(name));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        Grid<T> gr = world.getGrid();
        myGridClasses.add(gr.getClass());

        makeNewGridMenu();

        myControl = new SudokuGUIController<T>(this, myDisplay, myDisplayMap, myResources);
        content.add(myControl.controlPanel(), BorderLayout.SOUTH);

        myMessageArea = new JTextArea(2, 35);
        myMessageArea.setEditable(false);
        myMessageArea.setFocusable(false);
        myMessageArea.setBackground(new Color(0xFAFAD2));
        content.add(new JScrollPane(myMessageArea), BorderLayout.NORTH);

        pack();
        repaint(); // to show message
        myDisplay.setGrid(gr);
        setTitle("Sudoku");
    }

    /**
     * Repaints the window, getting it ready before
     * the game starts. 
     */

    public void repaint() {
        String message = getWorld().getMessage();
        if (message == null)
            message = myResources.getString("message.default");
        myMessageArea.setText(message);
        myMessageArea.repaint();
        myDisplay.repaint(); // for applet
        super.repaint();
    }

    /**
     * Gets the world that this frame displays
     * 
     * @return the world
     */
    public World<T> getWorld() {
        return myWorld;
    }

    /**
     * Sets a new grid for this world. Occupants are transferred from
     * the old world to the new.
     * 
     * @param newGrid the new grid
     */
    public void setGrid(Grid<T> newGrid) {
        Grid<T> oldGrid = myWorld.getGrid();
        Map<Location, T> occupants = new HashMap<Location, T>();
        for (Location loc : oldGrid.getOccupiedLocations())
            occupants.put(loc, myWorld.remove(loc));

        myWorld.setGrid(newGrid);
        for (Location loc : occupants.keySet()) {
            if (newGrid.isValid(loc))
                myWorld.add(loc, occupants.get(loc));
        }

        myDisplay.setGrid(newGrid);
        repaint();
    }

    /**
     * Sets the enabled status of those menu items that are disabled when
     * running.
     * 
     * @param enable true to enable the menus
     */
    public void setRunMenuItemsEnabled(boolean enable) {
        for (JMenuItem item : myMenuItemsDisabledDuringRun)
            item.setEnabled(enable);
    }

    /**
     * Displays an error message
     * 
     * @param t        the throwable that describes the error
     * @param resource the resource whose .text/.title strings
     *                 should be used in the dialog
     */
    public void showError(Throwable t, String resource) {
        String text;
        try {
            text = myResources.getString(resource + ".text");
        } catch (MissingResourceException e) {
            text = myResources.getString("error.text");
        }

        String title;
        try {
            title = myResources.getString(resource + ".title");
        } catch (MissingResourceException e) {
            title = myResources.getString("error.title");
        }

        String reason = myResources.getString("error.reason");
        String message = text + "\n"
                + MessageFormat.format(reason, new Object[] { t });

        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    // Creates the drop-down menus on the frame.

    private JMenu makeMenu(String resource) {
        JMenu menu = new JMenu();
        configureAbstractButton(menu, resource);
        return menu;
    }

    private JMenuItem makeMenuItem(String resource, ActionListener listener) {
        JMenuItem item = new JMenuItem();
        configureMenuItem(item, resource, listener);
        return item;
    }

    private void configureMenuItem(JMenuItem item, String resource,
            ActionListener listener) {
        configureAbstractButton(item, resource);
        item.addActionListener(listener);
        try {
            String accel = myResources.getString(resource + ".accel");
            String metaPrefix = "@";
            if (accel.startsWith(metaPrefix)) {
                int menuMask = getToolkit().getMenuShortcutKeyMask();
                KeyStroke key = KeyStroke.getKeyStroke(KeyStroke.getKeyStroke(
                        accel.substring(metaPrefix.length())).getKeyCode(),
                        menuMask);
                item.setAccelerator(key);
            } else {
                item.setAccelerator(KeyStroke.getKeyStroke(accel));
            }
        } catch (MissingResourceException ex) {
            // no accelerator
        }
    }

    private void configureAbstractButton(AbstractButton button, String resource) {
        String title = myResources.getString(resource);
        int i = title.indexOf('&');
        int mnemonic = 0;
        if (i >= 0) {
            mnemonic = title.charAt(i + 1);
            title = title.substring(0, i) + title.substring(i + 1);
            button.setText(title);
            button.setMnemonic(Character.toUpperCase(mnemonic));
            button.setDisplayedMnemonicIndex(i);
        } else
            button.setText(title);
    }

    private void makeMenus() {
        JMenuBar mbar = new JMenuBar();
        JMenu menu;

        myMenuItemsDisabledDuringRun = new ArrayList<JMenuItem>();

        mbar.add(menu = makeMenu("menu.file"));

        myNewGridMenu = makeMenu("menu.file.new");
        menu.add(myNewGridMenu);
        myMenuItemsDisabledDuringRun.add(myNewGridMenu);

        menu.add(makeMenuItem("menu.file.quit", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));

        mbar.add(menu = makeMenu("menu.view"));

        menu.add(makeMenuItem("menu.view.up", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.moveLocation(-1, 0);
            }
        }));
        menu.add(makeMenuItem("menu.view.down", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.moveLocation(1, 0);
            }
        }));
        menu.add(makeMenuItem("menu.view.left", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.moveLocation(0, -1);
            }
        }));
        menu.add(makeMenuItem("menu.view.right", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.moveLocation(0, 1);
            }
        }));

        JMenuItem viewEditMenu;
        menu.add(viewEditMenu = makeMenuItem("menu.view.edit",
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        myControl.editLocation();
                    }
                }));
        myMenuItemsDisabledDuringRun.add(viewEditMenu);

        JMenuItem viewDeleteMenu;
        menu.add(viewDeleteMenu = makeMenuItem("menu.view.delete",
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        myControl.deleteLocation();
                    }
                }));
        myMenuItemsDisabledDuringRun.add(viewDeleteMenu);

        menu.add(makeMenuItem("menu.view.zoomin", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.zoomIn();
            }
        }));

        menu.add(makeMenuItem("menu.view.zoomout", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myDisplay.zoomOut();
            }
        }));

        mbar.add(menu = makeMenu("menu.help"));
        menu.add(makeMenuItem("menu.help.about", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutPanel();
            }
        }));
        menu.add(makeMenuItem("menu.help.help", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        }));
        menu.add(makeMenuItem("menu.help.license", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLicense();
            }
        }));

        setRunMenuItemsEnabled(true);
        setJMenuBar(mbar);
    }

    private void makeNewGridMenu() {
        myNewGridMenu.removeAll();
        SudokuMenuMaker<T> maker = new SudokuMenuMaker<T>(this, myResources, myDisplayMap);
        maker.addConstructors(myNewGridMenu, myGridClasses);
    }


    /**
     * Brings up a simple dialog with some general information.
     */
    private void showAboutPanel() {
        String html = MessageFormat.format(myResources
                .getString("dialog.about.text"), new Object[] { myResources.getString("version.id") });
        String[] props = { "java.version", "java.vendor", "java.home", "os.name", "os.arch", "os.version", "user.name",
                "user.home", "user.dir" };
        html += "<table border='1'>";
        for (String prop : props) {
            try {
                String value = System.getProperty(prop);
                html += "<tr><td>" + prop + "</td><td>" + value + "</td></tr>";
            } catch (SecurityException ex) {
                // oh well...
            }
        }
        html += "</table>";
        html = "<html>" + html + "</html>";
        JOptionPane.showMessageDialog(this, new JLabel(html), myResources
                .getString("dialog.about.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Brings up a window with a scrolling text pane that display the help
     * information.
     */
    private void showHelp() {
        JDialog dialog = new JDialog(this, myResources
                .getString("dialog.help.title"));
        final JEditorPane helpText = new JEditorPane();
        try {
            URL url = getClass().getResource("GridWorldHelp.html");

            helpText.setPage(url);
        } catch (Exception e) {
            helpText.setText(myResources.getString("dialog.help.error"));
        }
        helpText.setEditable(false);
        helpText.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent ev) {
                if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                    try {
                        helpText.setPage(ev.getURL());
                    } catch (Exception ex) {
                    }
            }
        });
        JScrollPane sp = new JScrollPane(helpText);
        sp.setPreferredSize(new Dimension(650, 500));
        dialog.getContentPane().add(sp);
        dialog.setLocation(getX() + getWidth() - 200, getY() + 50);
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Brings up a dialog that displays the license.
     */
    private void showLicense() {
        JDialog dialog = new JDialog(this, myResources
                .getString("dialog.license.title"));
        final JEditorPane text = new JEditorPane();
        try {
            URL url = getClass().getResource("GNULicense.txt");

            text.setPage(url);
        } catch (Exception e) {
            text.setText(myResources.getString("dialog.license.error"));
        }
        text.setEditable(false);
        JScrollPane sp = new JScrollPane(text);
        sp.setPreferredSize(new Dimension(650, 500));
        dialog.getContentPane().add(sp);
        dialog.setLocation(getX() + getWidth() - 200, getY() + 50);
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Nested class that is registered as the handler for exceptions on the
     * Swing event thread. The handler will put up an alert panel and dump the
     * stack trace to the console.
     */
    public class GUIExceptionHandler {
        public void handle(Throwable e) {
            e.printStackTrace();

            JTextArea area = new JTextArea(10, 40);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            area.setText(writer.toString());
            area.setCaretPosition(0);
            String copyOption = myResources.getString("dialog.error.copy");
            JOptionPane pane = new JOptionPane(new JScrollPane(area),
                    JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION, null,
                    new String[] { copyOption, myResources.getString("cancel") });
            pane.createDialog(WorldFrame.this, e.toString()).setVisible(true);
            if (copyOption.equals(pane.getValue())) {
                area.setSelectionStart(0);
                area.setSelectionEnd(area.getText().length());
                area.copy(); // copy to clipboard
            }
        }
    }
}
