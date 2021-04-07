package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Window class for the main algorithm visualizer Program
 *
 * @author brorie3
 *
 * This class creates the GUI components such as the menu and frame
 */
public class Window extends JFrame {

    private static JPanel panel;
    private static Grid grid;

    /**
     * default constructor
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1350, 960));
        setMinimumSize(new Dimension(100, 150));
        setTitle("Traversal Algorithm Visualizer");
        setResizable(false);
        setLocationRelativeTo(null);
        try {
            BufferedImage img = ImageIO.read(this.getClass().getResource("/images/traversal_icon.png"));
            setIconImage(img);
        } catch (IOException ignored){}

        GridLayout layout = new GridLayout(1, 1);
        panel = new JPanel(layout);
        add(panel);

        initGrid();
        initGameMenu();
    }


    /**
     * setup the game menu bar
     */
    public void initGameMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu start = new JMenu("Run");
        JMenu gridOptions = new JMenu("View");
        JMenu algorithmSpeed = new JMenu("Speed");
        JMenu location = new JMenu("Location");
        JMenu eraseOptions = new JMenu("Erase");

        JMenu traversalSpeed = new JMenu("Traversal speed");
        JMenu pathSpeed = new JMenu("Path speed");

        JMenuItem startPoint = new JMenuItem("Start point");
        JMenuItem endPoint = new JMenuItem("End point");

        JMenuItem clearGrid = new JMenuItem("Clear grid");
        JMenuItem undoLast = new JMenuItem("Undo last square");

        JMenuItem tslow = new JMenuItem("Slow");
        JMenuItem tmedium = new JMenuItem("Medium");
        JMenuItem tfast = new JMenuItem("Fast");
        JMenuItem tsfast = new JMenuItem("Super fast");

        JMenuItem pslow = new JMenuItem("Slow");
        JMenuItem pmedium = new JMenuItem("Medium");
        JMenuItem pfast = new JMenuItem("Fast");
        JMenuItem psfast = new JMenuItem("Super fast");

        JMenuItem breadthFirst = new JMenuItem("Breadth-first search");
        JMenuItem depthFirst = new JMenuItem("Depth-first search");
        JMenuItem aStar = new JMenuItem("A* (non-diagonal)");
        JMenuItem diagonalAStar = new JMenuItem("A* (diagonal)");

        JMenu colorSettings = new JMenu("Color settings");
        JMenu borderSettings = new JMenu("Border settings");

        JMenuItem highlightColor = new JMenuItem("Change drawing color");
        JMenuItem gridColor = new JMenuItem("Change grid color");
        JMenuItem algorithmColor = new JMenuItem("Change animation color");
        JMenuItem pathColor = new JMenuItem("Change A* path color");
        JMenuItem removeBorder = new JMenuItem("Remove borders");
        JMenuItem addBorder = new JMenuItem("Add borders");

        JMenu squareContent = new JMenu("Space settings");
        JMenuItem addCircle = new JMenuItem("Add circles");
        JMenuItem addX = new JMenuItem("Add xs");
        JMenuItem addHearts = new JMenuItem("Add hearts");
        JMenuItem addSmiley = new JMenuItem("Add smiley faces");
        JMenuItem removeText = new JMenuItem("Remove text");

        startPoint.addActionListener(e -> grid.setStartLocation());

        endPoint.addActionListener(e -> grid.setEndLocation());

        addSmiley.addActionListener(e -> grid.addSmileys());

        addHearts.addActionListener(e -> grid.addHearts());

        addX.addActionListener(e -> grid.addXs());

        addCircle.addActionListener(e -> grid.addCircles());

        removeText.addActionListener(e -> grid.removeText());

        addBorder.addActionListener(e -> grid.addBorders());

        removeBorder.addActionListener(e -> grid.removeBorders());

        algorithmColor.addActionListener(e -> grid.changeAlgorithmColor());

        pathColor.addActionListener(e -> grid.changePathColor());

        gridColor.addActionListener(e -> grid.changeGridColor());

        highlightColor.addActionListener(e -> grid.changePenColor());

        undoLast.addActionListener(e -> grid.undoLastSquare());

        tslow.addActionListener(e -> grid.setSpeed(100));

        tmedium.addActionListener(e -> grid.setSpeed(40));

        tfast.addActionListener(e -> grid.setSpeed(10));

        tsfast.addActionListener(e -> grid.setSpeed(1));

        pslow.addActionListener(e -> grid.setPathSpeed(100));

        pmedium.addActionListener(e -> grid.setPathSpeed(40));

        pfast.addActionListener(e -> grid.setPathSpeed(10));

        psfast.addActionListener(e -> grid.setPathSpeed(1));

        location.add(startPoint);
        location.add(endPoint);

        traversalSpeed.add(tslow);
        traversalSpeed.add(tmedium);
        traversalSpeed.add(tfast);
        traversalSpeed.add(tsfast);

        pathSpeed.add(pslow);
        pathSpeed.add(pmedium);
        pathSpeed.add(pfast);
        pathSpeed.add(psfast);

        algorithmSpeed.add(traversalSpeed);
        algorithmSpeed.add(pathSpeed);

        clearGrid.addActionListener(e -> {
            grid.enableButtons();
            grid.clearPaintedSquares();

        });

        undoLast.addActionListener(e -> grid.undoLastSquare());

        breadthFirst.addActionListener(e -> {
            grid.disableButtons();
            grid.breadthFirstSearch();
        });

        depthFirst.addActionListener(e -> {
            grid.disableButtons();
            grid.depthFirstSearch();
        });

        aStar.addActionListener(e -> {
            grid.disableButtons();
            grid.aStar();
        });

        diagonalAStar.addActionListener(e -> {
            grid.disableButtons();
            grid.diagonalAStar();
        });

        eraseOptions.add(clearGrid);
        eraseOptions.add(undoLast);

        start.add(breadthFirst);
        start.add(depthFirst);
        start.add(aStar);
        start.add(diagonalAStar);

        colorSettings.add(highlightColor);
        colorSettings.add(gridColor);
        colorSettings.add(algorithmColor);
        colorSettings.add(pathColor);
        gridOptions.add(colorSettings);

        borderSettings.add(removeBorder);
        borderSettings.add(addBorder);
        gridOptions.add(borderSettings);

        squareContent.add(addCircle);
        squareContent.add(addX);
        squareContent.add(addHearts);
        squareContent.add(addSmiley);
        squareContent.add(removeText);
        gridOptions.add(squareContent);

        menuBar.add(start);
        menuBar.add(gridOptions);
        menuBar.add(algorithmSpeed);
        menuBar.add(location);
        menuBar.add(eraseOptions);
        setJMenuBar(menuBar);

    }


    /**
     * setup the grid
     */
    public void initGrid() {
        JButton[][] board = new JButton[70][90];

        GridLayout layout = new GridLayout(70, 90);
        JPanel p = new JPanel(layout);

        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 90; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setBorder(new LineBorder(Color.decode("#838383")));
                board[i][j] = button;
                p.add(button);
            }
        }
        panel.add(p);
        grid = new Grid(this, board);

    }

}