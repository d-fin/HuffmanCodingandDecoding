package Main;
import java.io.File;
import java.io.PrintWriter;
/*
originally implemented using an array of node objects with a merge sort. Pivotted to a 
java built in priority q class. 
*/
import java.util.*;
import Classes.MergeSort;
import Classes.Node;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence: ");

        String x = scanner.nextLine().toUpperCase();
        scanner.close();
        
        int n = x.length();
        Map<Character, Integer> freq = new HashMap<Character, Integer>();

        for(int i = 0; i < n; i++){
            if (freq.containsKey(x.charAt(i))){
                freq.put(x.charAt(i), freq.get(x.charAt(i)) + 1);
            } else{
                freq.put(x.charAt(i), 1);
            }
        }
        PriorityQueue<Node> nodes = new PriorityQueue<Node>(freq.size(), new MyComparator());
        //Node[] nodes = new Node[freq.size()];
        //int kount = 0; 
        for(Map.Entry<Character, Integer> e : freq.entrySet()){
            char letter = e.getKey();
            int frequency = e.getValue();
            Node temp = new Node(letter, frequency);
            nodes.add(temp);
            //nodes[kount] = new Node(letter, frequency);
            //kount++;  
        }

        /* MergeSort sortObjects = new MergeSort();
        sortObjects.sort(nodes, 0, nodes.length - 1);

        Node[] huffmanNodes = new Node[nodes.length]; */
        //int j = 0;
        //Boolean flag = true;
        Node root = null;
        while (nodes.size() > 1){
            Node temp1 = nodes.peek();
            nodes.poll();
            Node temp2 = nodes.peek();
            nodes.poll();

            StringBuilder s = new StringBuilder();
            s.append(temp1.getLetter()).append(temp2.getLetter());
            int z = temp1.getFrequency() + temp2.getFrequency();
            Node temp3 = new Node(s.toString(), z, temp1, temp2);

            root = temp3;
            nodes.add(temp3);
            /* if (nodes.length == 0){
                flag = false;
                break;
            }
            Node leftNode = nodes[0];
            Node rightNode = nodes[1];
            StringBuilder s = new StringBuilder();
            s.append(leftNode.getLetter()).append(rightNode.getLetter()).toString(); // + leftNode.getLetter().toString();
            Node temp = new Node(s.toString(), (leftNode.getFrequency() + rightNode.getFrequency()), leftNode, rightNode);

            leftNode.setHuffSymbol(0);
            rightNode.setHuffSymbol(1);

            huffmanNodes[j] = temp;
            j++;
            if (nodes.length > 2){
                nodes = removeElement(nodes, 0);
                nodes = removeElement(nodes, 0);
            }
            else{
                flag = false;
            } */
            
        }
        myGlobals codes = new myGlobals();
        
        printTree(root, "", codes);
        System.out.println();

        File myFile = new File("HuffmanCodes.txt");
        PrintWriter pw = new PrintWriter(myFile);

        for (int i = 0; i < x.length(); i++){
            char z = x.charAt(i);
            if (codes.letterCode.containsKey(z)){
                pw.write(codes.letterCode.get(z));
                pw.write(" ");
            } 
        }
        pw.close();
    }

   public static void printTree(Node root, String s, myGlobals codes){
        if(root == null) { return; }
        if (root.left == null && root.right == null && Character.isLetter(root.getLetter())) {
            codes.x.add(s);
            codes.letterCode.put(root.getLetter(), s);
            System.out.println(root.getLetter() + ":" + s);
            return;
        }
        printTree(root.left, s + "0", codes);
        printTree(root.right, s + "1", codes); 
    }

    /* Removes an element from an array without creating empty spaces and returns the array */
    public static Node[] removeElement(Node[] arr, int i){
        if (arr == null || arr.length < 0 || i > arr.length){
            return arr;
        }

        Node[] arrCopy = new Node[arr.length - 1];
        System.arraycopy(arr, 0, arrCopy, 0, i);
        System.arraycopy(arr, i + 1, arrCopy, i, arr.length - i - 1);

        return arrCopy;
    }
}
class MyComparator implements Comparator<Node> {
    public int compare(Node x, Node y)
    {
        return x.getFrequency() - y.getFrequency();
    }
}
class myGlobals {
    HashMap<Character, String> letterCode = new HashMap<Character, String>();
    ArrayList<String> x = new ArrayList<String>();
}