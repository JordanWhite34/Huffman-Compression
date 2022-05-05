// Jordan White
// 3/12/2021
// Take Home Assessment 8: Huffman Coding
//
// The HuffmanNode class represents each individual piece of the huffman organization system
// and how they are organized

import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public int freq;
    public int letterNum;
    public HuffmanNode zero;
    public HuffmanNode one;

	// Creates new organizational huffman piece that is essentially blank
    public HuffmanNode() {
        this(0, 0, null, null);
    }

	// Takes the frequency, ascii value, and elational organization pieces
	// that act as the paths for zeros and ones as parameters to create a new
	// organizational huffman piece
    public HuffmanNode(int freq, int letterNum, HuffmanNode zero, HuffmanNode one) {
        this.freq = freq;
        this.letterNum = letterNum;
        this.zero = zero;
        this.one = one;
    }

	// Helps maintain the order in which the huffman organizational pieces are organized
    public int compareTo(HuffmanNode h) {
        return this.freq - h.freq;
    }
}