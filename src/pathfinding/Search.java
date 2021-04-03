package pathfinding;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Search {

    private final Node[][] grid;
    private final PriorityQueue<Node> open_list = new PriorityQueue<>(10, new NodeComparator()); // sorted by f value
    private final ArrayList<Node> closed_list = new ArrayList<>();
    private final JButton[][] tile_grid;

    private final Node start_node;
    private Node current_node;
    private final Node end_node;

    private final int width;
    private final int height;

    private final JFrame frame;
    private Color algorithmColor;

    /*
     * Default constructor
     */
    public Search(JFrame window, JButton[][] tiles, int wi, int hi, Color algoColor) {
        algorithmColor = algoColor;
        grid = new Node[hi][wi];
        width = wi;
        height = hi;
        frame = window;
        tile_grid = tiles;
        current_node = new Node(0, 0, 0);
        end_node = new Node(69, 89, 0);
        grid[0][0] = current_node;
        grid[69][89] = end_node;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tiles[i][j].getBackground().equals(algorithmColor)) {
                    Node node = new Node(i, j, 0);
                    grid[i][j] = node;
                } else {
                    Node node = new Node(i, j, 1);
                    grid[i][j] = node;
                }
            }
        }

        // debugging
        System.out.println("\n\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    System.out.print(grid[i][j].getType() + " ");
                } catch (NullPointerException e) {
                    System.out.println("wtf");
                }
            }
            System.out.println("");
        }
        System.out.println("\n\n");

        // calculate g
        int g = calculateG(current_node);
        current_node.setG(g);
        // calculate h
        int h = calculateH(current_node);
        current_node.setH(h);
        // calculate f
        current_node.setF();
        start_node = current_node;
        System.out.println("current node g: " + g + "\ncurrent node h: " + h + "\ncurrent node f: " + current_node.getF());
        open_list.add(current_node);
        System.out.println("start open list: " + open_list.peek());
    }


    /*
     * Method that starts the A* search
     */
    public ArrayList<Node> start() {
        while (!open_list.isEmpty() && !current_node.equals(end_node)) { // open list isn't empty or goal node isn't reached
            current_node = open_list.peek();
            // remove the node with lowest f score
            open_list.remove(open_list.peek());
            System.out.println("open list: " + open_list);
            // check if current node is goal node
            if (current_node.equals(end_node)) {
                System.out.println("current = end");
                // if yes, generate a path
                closed_list.add(current_node);
                ArrayList<Node> path = generatePath();
                return path;
            } else {
                // generate neighbors
                try {
                    calculateNeighborValues();
                } catch (NullPointerException np) {

                }

                try {
                    assert open_list.peek() != null;
                    System.out.println("open_list lowest f: " + open_list.peek().getF());
                } catch (NullPointerException e) {
                    System.out.println("No path could be found");
                    JOptionPane.showMessageDialog(frame, "No path could be found");
                }

                // add current node to closed list
                closed_list.add(current_node);
                System.out.println("\n-----new current node-----\n");

            }
        }
        return null;
    }


    /*
     * method that calculates distance from start
     */
    public int calculateG(Node node) {
        int row = node.getRow();
        int col = node.getCol();
        if (row == current_node.getRow() && col == current_node.getCol()) {
            return 0;
        }

        Node parent = node.getParent();
        if (parent == null) {
            int xDistance;
            if (col > current_node.getCol()) {
                xDistance = col - current_node.getCol();
            } else {
                xDistance = current_node.getCol() - col;
            }
            int yDistance;
            if (row > current_node.getRow()) {
                yDistance = row - current_node.getRow();
            } else {
                yDistance = current_node.getRow() - row;
            }
            return (xDistance * 10) + (yDistance * 10);
        }
        return 10 + parent.getG();
    }


    /*
     * method that calculates the heuristic (distance of a node from the goal)
     */
    public int calculateH(Node node) {
        int row = node.getRow();
        int col = node.getCol();
        int x = 0;
        int y = 0;
        while (col < end_node.getCol() || col > end_node.getCol()) {
            x += 10;
            if (col < end_node.getCol()) {
                col++;
            }
            if (col > end_node.getCol()) {
                col--;
            }
        }
        while (row < end_node.getRow() || row > end_node.getRow()) {
            y += 10;
            if (row < end_node.getRow()) {
                row++;
            }
            if (row > end_node.getRow()) {
                row--;
            }
        }
        return x + y;
    }


    /*
     * method that calculates neighbor data
     *
     * neighbors must be within the bounds of the world
     * neighbors must be pathable (type 0)
     * neighbors must not exist in the closed list
     *
     */
    public void calculateNeighborValues() {
        int row = current_node.getRow();
        int col = current_node.getCol();

        // north node
        if (row - 1 > -1 && grid[row - 1][col].getType() == 0 && !closed_list.contains(grid[row - 1][col])) {
            grid[row - 1][col].setParent(current_node);
            int g = calculateG(grid[row - 1][col]);
            grid[row - 1][col].setG(g);
            int h = calculateH(grid[row - 1][col]);
            grid[row - 1][col].setH(h);
            grid[row - 1][col].setF();
            System.out.println("north node g: " + g + "\nnorth node h: " + h + "\nnorth node f: " + grid[row - 1][col].getF());
            if (!open_list.contains(grid[row - 1][col])) {
                open_list.add(grid[row - 1][col]);
            }
        }

        // east node
        if (col + 1 < width && grid[row][col + 1].getType() == 0 && !closed_list.contains(grid[row][col + 1])) {
            grid[row][col + 1].setParent(current_node);
            int g = calculateG(grid[row][col + 1]);
            grid[row][col + 1].setG(g);
            int h = calculateH(grid[row][col + 1]);
            grid[row][col + 1].setH(h);
            grid[row][col + 1].setF();
            System.out.println("east node g: " + g + "\neast node h: " + h + "\neast node f: " + grid[row][col + 1].getF());
            if (!open_list.contains(grid[row][col + 1])) {
                open_list.add(grid[row][col + 1]);
            }
        }

        // south node
        if (row + 1 < height && grid[row + 1][col].getType() == 0 && !closed_list.contains(grid[row + 1][col])) {
            grid[row + 1][col].setParent(current_node);
            int g = calculateG(grid[row + 1][col]);
            grid[row + 1][col].setG(g);
            int h = calculateH(grid[row + 1][col]);
            grid[row + 1][col].setH(h);
            grid[row + 1][col].setF();
            System.out.println("south node g: " + g + "\nsouth node h: " + h + "\nsouth node f: " + grid[row + 1][col].getF());
            if (!open_list.contains(grid[row + 1][col])) {
                open_list.add(grid[row + 1][col]);
            }
        }

        // west node
        if (col - 1 > -1 && grid[row][col - 1].getType() == 0 && !closed_list.contains(grid[row][col - 1])) {
            grid[row][col - 1].setParent(current_node);
            int g = calculateG(grid[row][col - 1]);
            grid[row][col - 1].setG(g);
            int h = calculateH(grid[row][col - 1]);
            grid[row][col - 1].setH(h);
            grid[row][col - 1].setF();
            System.out.println("west node g: " + g + "\nwest node h: " + h + "\nwest node f: " + grid[row][col - 1].getF());
            if (!open_list.contains(grid[row][col - 1])) {
                open_list.add(grid[row][col - 1]);
            }
        }
    }


    /*
     * Method that creates an arraylist containing the path
     */
    public ArrayList<Node> generatePath() {
        ArrayList<Node> path = new ArrayList<>();
        // get the parent nodes
        Node temp = current_node;
        path.add(temp);
        while (temp.getParent() != null) {
            temp = temp.getParent();
            path.add(temp);
        }
        return path;
    }
}













