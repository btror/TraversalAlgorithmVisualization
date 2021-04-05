package bfs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Bfs {

    private final JButton[][] grid;

    private final Color penColor;
    private final Color algorithmColor;
    private Color algorithmBorderColor;
    private final int speed;
    private final boolean bordersPresent;

    public Bfs(JButton[][] g, Color pc, Color ac, Color abc, int s, boolean bp) {
        grid = g;
        penColor = pc;
        algorithmColor = ac;
        algorithmBorderColor = abc;
        speed = s;
        bordersPresent = bp;
    }

    public void start() {
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
            } catch (InterruptedException ignored) {
            }
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

    }

}
