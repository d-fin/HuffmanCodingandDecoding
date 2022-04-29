package Classes;

public class Node {
    char letter;
    int frequency = 0;
    public Node left = null;
    public Node right = null;
    String combinedLetter = "";
    int huffSymbol;

    public Node(char letter, int frequency){
        this.letter = letter;
        this.frequency = frequency;
    }

    public Node(String letter, int frequency, Node right, Node left){
        this.combinedLetter = letter;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    public void setHuffSymbol(int huffSymbol){ this.huffSymbol = huffSymbol; }
    public int getHuffSymbol() { return this.huffSymbol; }
    public char getLetter() { return this.letter; }
    public int getFrequency() { return this.frequency; }
    public Node getLeft() { return this.left; }
    public Node getRight() { return this.right; }
    
}
