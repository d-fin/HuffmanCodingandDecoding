package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/*
originally implemented using an array of node objects with a merge sort. Pivotted to a 
java built in priority q class. 
*/
import java.util.*;

import javax.lang.model.type.NullType;

import Classes.Node;
public class App {

    HashMap<Character, String> letterCode = new HashMap<Character, String>();
    ArrayList<String> x = new ArrayList<String>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        
        myGlobals codes = new myGlobals();

        String x = encodeInputPrompt();
        String xCopy = x;
        
        encodeAndWriteToFile(x, codes);

        File myInFile = new File("HuffmanCodes.txt");
        File myInFileCompare = new File("TextFile.txt");
        
        String data = readData(myInFile);

        long huffCodeFileLength = myInFile.length();
        long textFileLength = myInFileCompare.length();
        boolean flag = false;
        
        while (flag == false){
            String s = decodeInputPrompt();
            int ans = Integer.parseInt(s); 
            switch(ans){
            case 1:
                System.out.println("\nIf your sentence was saved as text to a text file the size would be: " + Long.toString(huffCodeFileLength) + " Bytes.");
                System.out.println("The size of the file when saved as the huffman codes is " + Long.toString(textFileLength) + " Bytes.");
                decode(codes, data);
                System.out.println();
                break;
            case 2: 
                System.out.println("Encoded Text: " + data);
                break;
            case 3: 
                System.out.println("Original text: " + xCopy);
                break;
            case 4:
                String newS = encodeInputPrompt();
                codes.letterCode.clear();
                codes.x.clear();
                encodeAndWriteToFile(newS, codes);
                huffCodeFileLength = myInFile.length();
                textFileLength = myInFileCompare.length();
                data = readData(myInFile);
                break;
            default: 
                System.out.println("\nGoodbye :)\n");
                flag = true;
                break;
            }
        }
        
        System.out.println();
    }

    public static String readData(File myInFile) throws FileNotFoundException{
        Scanner in = new Scanner(myInFile);
        String data = "";

        while (in.hasNextLine()){
            data = in.nextLine();
        }
        in.close();
        return data;
    }

    public static void encodeAndWriteToFile(String x, myGlobals codes){
        x = x.toUpperCase();
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
        File compareFile = new File("TextFile.txt");
        PrintWriter pw2 = null;
        PrintWriter pw = null;
        try{
            pw2 = new PrintWriter(compareFile);
            pw = new PrintWriter(myFile);
        } catch (Exception e) {
            System.out.println("Couldnt read file.... \n" + e);
        }

        for (int i = 0; i < x.length(); i++){
            char z = x.charAt(i);
            if (z == ' '){
                pw.write(" ");
            } 
            if (codes.letterCode.containsKey(z)){
                pw.write(codes.letterCode.get(z));
                pw.write(",");
            } 
            pw2.write(z);
        }
        pw2.close();
        pw.close();
    } 

    public static void decode(myGlobals codes, String data){
        boolean flag = false;
        int n = data.length() - data.replaceAll(",", "").length();
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < data.length(); i++){
            builder.append(data.charAt(i));
        }
        String sentence = builder.toString();
        String[] codesInData = sentence.split(",", n);
        int j = 0;
        while (flag == false){
            for (Map.Entry<Character, String> e : codes.letterCode.entrySet()){
                if (j >= codesInData.length){
                    flag = true;
                    break;
                }
                if (codesInData[j].contains(",")){
                    codesInData[j] = codesInData[j].replaceAll(",", "");
                }
                if (codesInData[j].contains(" ")){
                    System.out.print(" ");
                    codesInData[j] = codesInData[j].replaceAll(" ", "");
                }
                if ((e.getValue().compareTo(codesInData[j])) == 0){
                    String key = String.valueOf(e.getKey());
                    System.out.print(key.toLowerCase());
                    j++;
                }
            }
        }
    }

    public static String decodeInputPrompt(){
        System.out.println("\nPlease select an option");
        System.out.println("1. Decode the encoded text");
        System.out.println("2. Display the encoded text");
        System.out.println("3. Display original text");
        System.out.println("4. Enter a new sentence");
        System.out.println("5. Quit");
        
        String s = scanner.nextLine();
        return s;
    }

    public static String encodeInputPrompt(){
        System.out.println("\nEnter a sentence: ");
        String x = scanner.nextLine();

        return x;
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