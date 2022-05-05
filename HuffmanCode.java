// Jordan White
// 3/12/2021
// Take Home Assessment 8: Huffman Coding
//
// The HuffmanCode class represents a huffman code for a particular message, keeping track
// of an organization system that is developed using the huffman algorithm

import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanNode tree;

    // Takes the frequencies for every character used as a parameter and uses that information
    // to create a new organizational system for the relationships of the characters using
    // the huffman algorithm
    public HuffmanCode(int[] frequencies) {
        PriorityQueue<HuffmanNode> priority = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i ++) {
            if (frequencies[i] > 0) {
                HuffmanNode freq = new HuffmanNode(frequencies[i], i, null, null);
                priority.add(freq);
            }
        }
        tree = combine(priority);
    }

    // This helper method is used to create the structure of the organizational system for the
    // character relationships, takes 'priority' as a parameter
    private HuffmanNode combine(PriorityQueue<HuffmanNode> priority) {
        HuffmanNode one;
        HuffmanNode two;
        while (priority.size() > 1) {
            one = priority.remove();
            two = priority.remove();
            HuffmanNode node = new HuffmanNode(one.freq + two.freq, 0, one, two);
            priority.add(node);
        }
        return priority.remove();
    }

    // This method takes in 'input' as a parameter to read a text file and creates a new
    // organizational system for its characters two lines at a time by reading a previously
    // made organizational system, assuming 'input' exists and always contains data
    // encoded in legal, valid standard format
    public HuffmanCode(Scanner input) {
        tree = new HuffmanNode();
        while (input.hasNextLine()) {
            String ascii = input.nextLine();
            String path = input.nextLine();
            tree = read(ascii, path, tree);
        }

    }

    // This helper method takes the ascii value (data), the binary path (path), and a piece of the
    // organizational system (node) as parameters to build and return a huffman organizational
    // system by reading a previously made organizational system
    private HuffmanNode read(String data, String path, HuffmanNode node) {
        if (node != null) {
            if (path.length() == 0) {
                node.letterNum =  Integer.parseInt(data);
            } else {        // path.size() > 0
                char front = path.charAt(0);
                path = path.substring(1);
                if (front == '0') {
                    if (node.zero == null) {
                        HuffmanNode newNode = new HuffmanNode();
                        node.zero = newNode;
                    }
                    node.zero = read(data, path, node.zero);
                } else {    // front == '1'
                    if (node.one == null) {
                        HuffmanNode newNode = new HuffmanNode();
                        node.one = newNode;
                    }
                    node.one = read(data, path, node.one);
                }
            }
        }
        return node;
    }

    // This method takes in 'output' as a parameter as a means to store information
    // and stores the current huffman organizational system to 'output' in the correct format
    // where ascii values printed before the binary path
    public void save(PrintStream output) {
        save(output, "", tree);
    }

    // This helper method takes parameters 'output', 'path', and 'curr' to have a means to
    // store information, represent the inner-organizational system path, and use a piece of
    // that system respectively. Using this, the method stores the current huffman organizational
    // system to 'output' in the correct format
    private void save(PrintStream output, String path, HuffmanNode curr) {
        if (curr != null) {
            if (curr.one == null && curr.zero == null) {
                output.println(curr.letterNum);
                output.println(path);
            } else {
                save(output, path + "0", curr.zero);
                save(output, path + "1", curr.one);
            }
        }
    }

    // This method takes 'input' and 'output' as parameters to take in information and externally
    // save information, respectively. The method will go through the information given to it and
    // translate it to an external file in a readable format, assuming 'input' is formatted
    // correctly for the current organizational structure
    public void translate(BitInputStream input, PrintStream output) {
        while (input.hasNextBit()) {
            translate(input, output, tree);
        }
    }

    // This helper method takes parameters 'input', 'output', and 'node' to take in information,
    // externally save information, and to represent a piece of the huffman organizational
    // structure, respectively. Using this, the method will translate one step of the given
    // information to a readable format
    private void translate(BitInputStream input, PrintStream output, HuffmanNode node) {
        while (node.zero != null && node.one != null) {
            int index = input.nextBit();
            if (index == 0) {
                node = node.zero;
            } else {
                node = node.one;
            }
        }
        output.write(node.letterNum);
    }
}