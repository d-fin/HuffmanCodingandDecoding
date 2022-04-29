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
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
       
        String x = encodeInputPrompt();
        String xCopy = x;
        x = x.toUpperCase();

        int n = x.length();
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        PriorityQueue<Node> nodes = new PriorityQueue<Node>(freq.size(), new MyComparator());
        myGlobals codes = new myGlobals();

        for(int i = 0; i < n; i++){
            if (freq.containsKey(x.charAt(i))){
                freq.put(x.charAt(i), freq.get(x.charAt(i)) + 1);
            } else{
                freq.put(x.charAt(i), 1);
            }
        }
        
        for(Map.Entry<Character, Integer> e : freq.entrySet()){
            char letter = e.getKey();
            int frequency = e.getValue();
            Node temp = new Node(letter, frequency);
            nodes.add(temp);
        }

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
        }

        traverseTree(root, "", codes);

        File myFile = new File("HuffmanCodes.txt");
        PrintWriter pw = new PrintWriter(myFile);

        for (int i = 0; i < x.length(); i++){
            char z = x.charAt(i);
            if (codes.letterCode.containsKey(z)){
                pw.write(codes.letterCode.get(z));
               // pw.write(" ");
            } 
        }
        pw.close();

        boolean flag = false;

        File myInFile = new File("HuffmanCodes.txt");
        Scanner in = new Scanner(myInFile);

        String data = "";
        while (in.hasNextLine()){
            data = in.nextLine();
        }
        in.close();

        while (flag == false){
            String s = decodeInputPrompt();
            int ans = Integer.parseInt(s); 
            switch(ans){
            case 1:
                
                break;
            case 2: 
                System.out.println("Encoded Text: " + data);
                break;
            case 3: 
                System.out.println("Original text: " + xCopy);
                break;
            default: 
                System.out.println("Goodbye :)");
                flag = true;
                break;
            }
        }

    }

    public static String encodeInputPrompt(){
        System.out.println("\nEnter a sentence: ");
        String x = scanner.nextLine();

        return x;
    }

    public static String decodeInputPrompt(){
        System.out.println("\nPlease select an option");
        System.out.println("1. Decode the encoded text");
        System.out.println("2. Display the encoded text");
        System.out.println("3. Display original text");
        System.out.println("4. Quit");
        
        String s = scanner.nextLine();
        return s;
    }

    public static void traverseTree(Node root, String s, myGlobals codes){
        if(root == null) { return; }
        if (root.left == null && root.right == null && Character.isLetter(root.getLetter())) {
            codes.x.add(s);
            codes.letterCode.put(root.getLetter(), s);
            //System.out.println(root.getLetter() + ":" + s);
            return;
        }
        traverseTree(root.left, s + "0", codes);
        traverseTree(root.right, s + "1", codes); 
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
    public int compare(Node x, Node y) { return x.getFrequency() - y.getFrequency(); }
}
class myGlobals {
    HashMap<Character, String> letterCode = new HashMap<Character, String>();
    ArrayList<String> x = new ArrayList<String>();
}