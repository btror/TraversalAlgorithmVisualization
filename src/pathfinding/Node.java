package pathfinding;

public class Node {
    private final int row;
    private final int col;
    private int f;
    private int g;
    private int h;
    private final int type;
    private Node parent;


    public Node(int r, int c, int t){
        row = r;
        col = c;
        type = t;
        parent = null;
    }


    public void setF(){
        f = g + h;
    }

    public void setG(int value){
        g = value;
    }

    public void setH(int value){
        h = value;
    }

    public void setParent(Node n){
        parent = n;
    }

    public int getF(){
        return f;
    }

    public int getG(){
        return g;
    }

    public int getType() {
        return type;
    }

    public Node getParent(){
        return parent;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public boolean equals(Object in) {
        // typecast to Node
        Node n = (Node) in;
        return row == n.getRow() && col == n.getCol();
    }

    public String toString(){
        return "Node: " + row + "_" + col;
    }
}