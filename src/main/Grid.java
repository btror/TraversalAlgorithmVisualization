package main;

import astar.AStar;
import bfs.Bfs;
import dfs.Dfs;
import diagonalastar.DiagonalAStar;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Grid class for the Main Class
 *
 * @author brorie3
 * <p>
 * This class builds the grid and contains the logic for the main algorithms
 */
public class Grid {

    private static JButton[][] grid = new JButton[70][90];
    private static final ArrayList<JButton> paintedButtons = new ArrayList<>();
    private static int speed = 10;
    private static int pathSpeed = 10;
    private static boolean enableButtons = true;
    private static Color penColor = Color.RED;
    private static Color gridColor = Color.WHITE;
    private static Color algorithmColor = Color.BLUE;
    private static Color pathColor = Color.GREEN;
    private static Color penBorderColor = Color.decode("#838383");
    private static Color gridBorderColor = Color.decode("#838383");
    private static Color algorithmBorderColor = Color.decode("#838383");
    private static boolean bordersPresent = true;
    private static JFrame frame;

    private static final int[] startLocation = new int[2]; // x y
    private static final int[] endLocation = new int[2]; // x y

    /**
     * Constructor
     *
     * @param board the grid of the window
     */
    public Grid(JFrame f, JButton[][] board) {
        grid = board;
        frame = f;
        startLocation[0] = 10;
        startLocation[1] = 34;
        endLocation[0] = 80;
        endLocation[1] = 34;
        initGridComponents();
    }


    public void setStartLocation() {
        String stringX = JOptionPane.showInputDialog(frame, "x coordinate: ");
        String stringY = JOptionPane.showInputDialog(frame, "y coordinate: ");
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        grid[startLocation[1]][startLocation[0]].setBackground(gridColor);
        grid[y][x].setBackground(Color.GRAY);
        startLocation[0] = x;
        startLocation[1] = y;
    }

    public void setEndLocation() {
        String stringX = JOptionPane.showInputDialog(frame, "x coordinate: ");
        String stringY = JOptionPane.showInputDialog(frame, "y coordinate: ");
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        grid[endLocation[1]][endLocation[0]].setBackground(gridColor);
        grid[y][x].setBackground(Color.GREEN);
        endLocation[0] = x;
        endLocation[1] = y;
    }


    /**
     * setup the components in the grid
     */
    public static void initGridComponents() {
        grid[startLocation[1]][startLocation[0]].setBackground(Color.GRAY);
        grid[endLocation[1]][endLocation[0]].setBackground(Color.GREEN);
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 90; j++) {
                if (!grid[i][j].getBackground().equals(Color.GRAY) && !grid[i][j].getBackground().equals(Color.GREEN)) {

                    grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {

                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            JButton button = (JButton) evt.getSource();
                            if (enableButtons) {
                                if (button.getBackground().equals(gridColor)) {
                                    if (!bordersPresent) {
                                        penBorderColor = penColor;
                                    }

                                    button.setBackground(penColor);
                                    button.setBorder(new LineBorder(penBorderColor));
                                    paintedButtons.add(button);
                                    button.transferFocus();
                                    paintedButtons.add(button);
                                } else if (button.getBackground().equals(penColor)) {
                                    if (!bordersPresent) {
                                        gridBorderColor = gridColor;
                                    }
                                    button.setBackground(gridColor);
                                    button.setBorder(new LineBorder(gridBorderColor));
                                    paintedButtons.remove(button);
                                    button.transferFocus();
                                    paintedButtons.remove(button);
                                }

                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent evt) {
                            JButton button = (JButton) evt.getSource();
                            if (SwingUtilities.isLeftMouseButton(evt) && enableButtons) {
                                if (button.getBackground().equals(gridColor)) {
                                    button.setBackground(penColor);
                                    if (!bordersPresent) {
                                        penBorderColor = penColor;
                                    }

                                    button.setBorder(new LineBorder(penBorderColor));
                                    paintedButtons.add(button);
                                } else if (button.getBackground().equals(penColor)) {
                                    if (!bordersPresent) {
                                        gridBorderColor = gridColor;
                                    }

                                    button.setBackground(gridColor);
                                    button.setBorder(new LineBorder(gridBorderColor));
                                    paintedButtons.remove(button);
                                }
                            }
                        }
                    });
                }
            }
        }
    }


    /**
     * disables the buttons in the grid
     */
    public void disableButtons() {
        enableButtons = false;
    }


    /**
     * enables the buttons in the grid
     */
    public void enableButtons() {
        enableButtons = true;
    }


    /**
     * Sets the speed of the animation
     *
     * @param level the speed level
     */
    public void setSpeed(int level) {
        speed = level;
    }


    /**
     * Sets the speed of the A* path animation
     *
     * @param level the speed level
     */
    public void setPathSpeed(int level) {
        pathSpeed = level;
    }


    /**
     * Method that starts the breadth-first search animation
     */
    public void breadthFirstSearch() {
        Bfs bfs = new Bfs(grid, penColor, algorithmColor, algorithmBorderColor, speed, bordersPresent, startLocation, endLocation);
        Thread thread = new Thread(bfs::start);
        thread.start();

    }


    /**
     * Method that starts the depth-first search animation
     */
    public void depthFirstSearch() {
        Dfs dfs = new Dfs(grid, penColor, algorithmColor, algorithmBorderColor, speed, bordersPresent, startLocation, endLocation);
        Thread thread = new Thread(dfs::start);
        thread.start();

    }


    /**
     * Method that starts the A* path finding animation
     */
    public void aStar() {
        Thread thread = new Thread(() -> {
            AStar astar = new AStar(frame, grid, 90, 70, gridColor, algorithmColor, speed, startLocation, endLocation);
            ArrayList<Node> path = astar.start();
            try {
                for (int i = path.size() - 1; i > -1; i--) {
                    int row = path.get(i).getRow();
                    int col = path.get(i).getCol();
                    try {
                        Thread.sleep(pathSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!bordersPresent) {
                        algorithmBorderColor = pathColor;
                    }
                    grid[row][col].setBorder(new LineBorder(algorithmBorderColor));
                    grid[row][col].setBackground(pathColor);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        });
        thread.start();
    }


    /**
     * Method that starts the A* path finding animation
     */
    public void diagonalAStar() {
        Thread thread = new Thread(() -> {
            DiagonalAStar astar = new DiagonalAStar(frame, grid, 90, 70, gridColor, algorithmColor, speed, startLocation, endLocation);
            ArrayList<Node> path = astar.start();
            try {
                for (int i = path.size() - 1; i > -1; i--) {
                    int row = path.get(i).getRow();
                    int col = path.get(i).getCol();
                    try {
                        Thread.sleep(pathSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!bordersPresent) {
                        algorithmBorderColor = pathColor;
                    }
                    grid[row][col].setBorder(new LineBorder(algorithmBorderColor));
                    grid[row][col].setBackground(pathColor);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        });
        thread.start();
    }


    /**
     * clears the painted squares
     */
    public void clearPaintedSquares() {
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 90; j++) {
                grid[i][j].setBackground(gridColor);
                grid[i][j].setBorder(new LineBorder(gridBorderColor));
            }
        }
        paintedButtons.clear();
        grid[startLocation[1]][startLocation[0]].setBackground(Color.GRAY);
        grid[endLocation[1]][endLocation[0]].setBackground(Color.GREEN);
    }


    /**
     * changes the color of the highlighter
     */
    public void changePenColor() {
        Color prevColor = penColor;
        penColor = JColorChooser.showDialog(null, "Choose a color", penColor);
        // change buttons that are already highlighted to the new color
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (jButtons[j].getBackground().equals(prevColor)) {
                    jButtons[j].setBackground(penColor);
                    if (!bordersPresent) {
                        penBorderColor = penColor;
                    }
                    jButtons[j].setBorder(new LineBorder(penBorderColor));
                }
            }
        }
    }


    /**
     * changes the color of the grid
     */
    public void changeGridColor() {
        Color prevColor = gridColor;
        gridColor = JColorChooser.showDialog(null, "Choose a color", gridColor);
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (jButtons[j].getBackground().equals(prevColor)) {
                    jButtons[j].setBackground(gridColor);
                    if (!bordersPresent) {
                        gridBorderColor = gridColor;
                    }
                    jButtons[j].setBorder(new LineBorder(gridBorderColor));
                }
            }
        }
    }


    /**
     * changes the color of animation
     */
    public void changeAlgorithmColor() {
        Color prevColor = algorithmColor;
        algorithmColor = JColorChooser.showDialog(null, "Choose a color", algorithmColor);
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (jButtons[j].getBackground().equals(prevColor)) {
                    jButtons[j].setBackground(algorithmColor);
                    if (!bordersPresent) {
                        algorithmBorderColor = algorithmColor;
                    }
                    jButtons[j].setBorder(new LineBorder(algorithmBorderColor));
                }
            }
        }
    }


    /**
     * changes the color of animation
     */
    public void changePathColor() {
        Color prevColor = pathColor;
        pathColor = JColorChooser.showDialog(null, "Choose a color", pathColor);
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (jButtons[j].getBackground().equals(prevColor)) {
                    jButtons[j].setBackground(pathColor);
                    if (!bordersPresent) {
                        algorithmBorderColor = pathColor;
                    }
                    jButtons[j].setBorder(new LineBorder(algorithmBorderColor));
                }
            }
        }
    }


    /**
     * removes the borders of the buttons and grid
     */
    public void removeBorders() {
        bordersPresent = false;
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                Color temp = jButtons[j].getBackground();
                jButtons[j].setBorder(new LineBorder(temp));
            }
        }
        penBorderColor = penColor;
        gridBorderColor = gridColor;
        algorithmBorderColor = algorithmColor;
    }


    /**
     * adds borders to the buttons in the grid
     */
    public void addBorders() {
        bordersPresent = true;
        Color temp = Color.decode("#838383");
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setBorder(new LineBorder(temp));
            }
        }
        penBorderColor = Color.decode("#838383");
        gridBorderColor = Color.decode("#838383");
        algorithmBorderColor = Color.decode("#838383");
    }


    /**
     * adds circles to the borders
     */
    public void addCircles() {
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setFont(new Font("Futura", Font.BOLD, 10));
                jButtons[j].setText("O");
            }
        }
    }


    /**
     * adds X's to the borders
     */
    public void addXs() {
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setFont(new Font("Futura", Font.BOLD, 10));
                jButtons[j].setText("X");
            }
        }
    }


    /**
     * adds hearts to the borders
     */
    public void addHearts() {
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setFont(new Font("Futura", Font.BOLD, 10));
                jButtons[j].setText("❤");
            }
        }
    }


    /**
     * adds smileys to the borders
     */
    public void addSmileys() {
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setFont(new Font("Futura", Font.BOLD, 10));
                jButtons[j].setText("☺");
            }
        }
    }


    /**
     * removes the text from the borders
     */
    public void removeText() {
        for (JButton[] jButtons : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                jButtons[j].setFont(new Font("Futura", Font.BOLD, 10));
                jButtons[j].setText("");
            }
        }
    }


    /**
     * clears the last square painted by the user
     */
    public void undoLastSquare() {

        boolean end = false;

        if (!paintedButtons.isEmpty()) {
            for (JButton[] jButtons : grid) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (jButtons[j].equals(paintedButtons.get(paintedButtons.size() - 1))) {
                        paintedButtons.remove(paintedButtons.size() - 1);
                        jButtons[j].setBackground(gridColor);
                        jButtons[j].setBorder(new LineBorder(gridBorderColor));
                        end = true;
                        break;
                    }
                }
                if (end) {
                    break;
                }
            }
        }
    }

}