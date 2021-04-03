package traversal;

import pathfinding.Node;
import pathfinding.Search;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Grid class for the Main Class
 *
 * @author brorie3
 *
 * This class builds the grid and contains the logic for the traversal algorithms
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

    /**
     * Constructor
     *
     * @param board the grid of the window
     */
    public Grid(JFrame f, JButton[][] board) {
        grid = board;
        frame = f;
        initGridComponents();
    }


    /**
     * setup the components in the grid
     */
    public static void initGridComponents() {
        grid[0][0].setBackground(Color.GRAY);
        grid[69][89].setBackground(Color.GREEN);
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
        Thread thread = new Thread(() -> {

            boolean[][] visited = new boolean[70][90];
            int[][] textGrid = new int[70][90];

            for (int i = 0; i < 70; i++) {
                for (int j = 0; j < 90; j++) {
                    if (grid[i][j].getBackground().equals(penColor)) {
                        textGrid[i][j] = 1;
                        visited[i][j] = true;
                    } else {
                        textGrid[i][j] = 0;
                    }
                }
            }

            int height = textGrid.length;
            int length = textGrid[0].length;

            Queue<String> queue = new LinkedList<>();
            queue.add(0 + "," + 0);

            System.out.println("Breadth First Search");

            while (!queue.isEmpty()) {

                if (grid[69][89].getBackground().equals(algorithmColor)) {
                    break;
                }

                String x = queue.remove();
                int row = Integer.parseInt(x.split(",")[0]);
                int col = Integer.parseInt(x.split(",")[1]);

                if (row < 0 || col < 0 || row >= height || col >= length || visited[row][col]) {
                    continue;
                }

                visited[row][col] = true;
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ignored) {}
                grid[row][col].setBackground(algorithmColor);
                if (!bordersPresent) {
                    algorithmBorderColor = algorithmColor;
                }
                grid[row][col].setBorder(new LineBorder(algorithmBorderColor));
                queue.add(row + "," + (col - 1)); //go left
                queue.add(row + "," + (col + 1)); //go right
                queue.add((row - 1) + "," + col); //go up
                queue.add((row + 1) + "," + col); //go down
            }

            // path finding
            Search search = new Search(frame, grid, 90, 70, algorithmColor);
            ArrayList<Node> path = search.start();
            System.out.println("path size: " + path.size());
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

        });
        thread.start();

    }


    /**
     * Method that starts the depth-first search animation
     */
    public void depthFirstSearch() {
        Thread thread = new Thread(() -> {
            int[][] textGrid = new int[70][90];
            boolean[][] visited = new boolean[70][90];

            for (int i = 0; i < 70; i++) {
                for (int j = 0; j < 90; j++) {
                    if (grid[i][j].getBackground().equals(penColor)) {
                        textGrid[i][j] = 1;
                        visited[i][j] = true;
                    } else {
                        textGrid[i][j] = 0;
                    }
                }
            }

            int height = textGrid.length;
            int length = textGrid[0].length;

            Stack<String> stack = new Stack<>();
            stack.push(0 + "," + 0);
            System.out.println("Depth-First Search");

            while (!stack.empty()) {
                if (grid[69][89].getBackground().equals(algorithmColor)) {
                    break;
                }
                String x = stack.pop();
                int row = Integer.parseInt(x.split(",")[0]);
                int col = Integer.parseInt(x.split(",")[1]);
                if (row < 0 || col < 0 || row >= height || col >= length || visited[row][col]) {
                    continue;
                }

                visited[row][col] = true;
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ignored) {}
                grid[row][col].setBackground(algorithmColor);
                if (!bordersPresent) {
                    algorithmBorderColor = algorithmColor;
                }
                grid[row][col].setBorder(new LineBorder(algorithmBorderColor));
                stack.push(row + "," + (col - 1)); //go left
                stack.push(row + "," + (col + 1)); //go right
                stack.push((row - 1) + "," + col); //go up
                stack.push((row + 1) + "," + col); //go down

            }
            // path finding
            Search search = new Search(frame, grid, 90, 70, algorithmColor);
            ArrayList<Node> path = search.start();
            try {

            } catch (NullPointerException np) {
                System.out.println("path size: " + path.size());
            }
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
        grid[0][0].setBackground(Color.GRAY);
        grid[69][89].setBackground(Color.GREEN);
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