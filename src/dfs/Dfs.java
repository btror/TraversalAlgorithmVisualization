package dfs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Stack;

public class Dfs {

    private final JButton[][] grid;

    private final Color penColor;
    private final Color algorithmColor;
    private Color algorithmBorderColor;
    private final int speed;
    private final boolean bordersPresent;
    private final int[] startLocation;
    private final int[] endLocation;

    public Dfs(JButton[][] g, Color pc, Color ac, Color abc, int s, boolean bp, int[] startCoordinate, int[] endCoordinate) {
        startLocation = startCoordinate;
        endLocation = endCoordinate;
        grid = g;
        penColor = pc;
        algorithmColor = ac;
        algorithmBorderColor = abc;
        speed = s;
        bordersPresent = bp;
    }

    public void start() {
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
        // stack.push(0 + "," + 0); start
        stack.push(startLocation[1] + "," + startLocation[0]);
        System.out.println("Depth-First Search");

        while (!stack.empty()) {
            if (grid[endLocation[1]][endLocation[0]].getBackground().equals(algorithmColor)) {
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

    }

}
