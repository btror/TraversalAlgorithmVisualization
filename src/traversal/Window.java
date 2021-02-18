package traversal;

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
 * Window class for the traversal algorithm visualizer Program
 *
 * @author brorie3
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

        setVisible(true);
    }


    /**
     * setup the game menu bar
     */
    public void initGameMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gridOptions = new JMenu("View");
        JMenu algorithmSpeed = new JMenu("Speed");
        JMenu eraseOptions = new JMenu("Erase");
        JMenu start = new JMenu("Run");
        //JMenu emojis = new JMenu("Emojis!");

        JMenuItem clearGrid = new JMenuItem("Clear grid");
        JMenuItem undoLast = new JMenuItem("Undo last square");

        JMenuItem slow = new JMenuItem("Slow");
        JMenuItem medium = new JMenuItem("Medium");
        JMenuItem fast = new JMenuItem("Fast");
        JMenuItem sfast = new JMenuItem("Super fast");

        JMenuItem breadthFirst = new JMenuItem("Breadth-first search");
        JMenuItem depthFirst = new JMenuItem("Depth-first search");

        JMenu colorSettings = new JMenu("Color settings");
        JMenu borderSettings = new JMenu("Border settings");

        JMenuItem highlightColor = new JMenuItem("Change drawing color");
        JMenuItem gridColor = new JMenuItem("Change grid color");
        JMenuItem algorithmColor = new JMenuItem("Change animation color");
        JMenuItem removeBorder = new JMenuItem("Remove borders");
        JMenuItem addBorder = new JMenuItem("Add borders");

        JMenu squareContent = new JMenu("Space settings");
        JMenuItem addCircle = new JMenuItem("Add circles");
        JMenuItem addX = new JMenuItem("Add xs");
        JMenuItem addHearts = new JMenuItem("Add hearts");
        JMenuItem addSmiley = new JMenuItem("Add smiley faces");
        JMenuItem removeText = new JMenuItem("Remove text");

        addSmiley.addActionListener(e -> grid.addSmileys());

        addHearts.addActionListener(e -> grid.addHearts());

        addX.addActionListener(e -> grid.addXs());

        addCircle.addActionListener(e -> grid.addCircles());

        removeText.addActionListener(e -> grid.removeText());

        addBorder.addActionListener(e -> grid.addBorders());

        removeBorder.addActionListener(e -> grid.removeBorders());

        algorithmColor.addActionListener(e -> grid.changeAlgorithmColor());

        gridColor.addActionListener(e -> grid.changeGridColor());

        highlightColor.addActionListener(e -> grid.changePenColor());

        undoLast.addActionListener(e -> grid.undoLastSquare());

        slow.addActionListener(e -> grid.setSpeed(100));

        medium.addActionListener(e -> grid.setSpeed(40));

        fast.addActionListener(e -> grid.setSpeed(10));

        sfast.addActionListener(e -> grid.setSpeed(1));

        algorithmSpeed.add(slow);
        algorithmSpeed.add(medium);
        algorithmSpeed.add(fast);
        algorithmSpeed.add(sfast);

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

        eraseOptions.add(clearGrid);
        eraseOptions.add(undoLast);

        start.add(breadthFirst);
        start.add(depthFirst);

        colorSettings.add(highlightColor);
        colorSettings.add(gridColor);
        colorSettings.add(algorithmColor);
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
        menuBar.add(eraseOptions);
        setJMenuBar(menuBar);

    }


    /**
     * setup the grid
     */
    public static void initGrid() {
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
        grid = new Grid(board);

    }

}