/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF(graph, corpus) = a poetry generator which modified by graph
    // Representation invariant:
    //   poetry is not null
    // Safety from rep exposure:
    //   graph is private and final
    
    // TODO checkRep
    public void checkRep() {
    	assert this.graph != null:"graphPoetry is not null";
    }
    
    /**
     * Convert letters in corpus from upper to lower, separation done at whitespace
     * 
     * @param corpus text file
     * @return Returns a list of words in lower, separation done at whitespace
     * @throws IOException if the corpus file cannot be found or read
     */
	private List<String> toLowerCaseFile(File corpus) throws IOException {
		List<String> wordList = new ArrayList<>();
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(corpus)))) {
            while (s.hasNext()) {
                wordList.add(s.next().toLowerCase());
            }
        }
    	return wordList;
    }
	
	/**
	 * generate a graphPoetry with the graph
	 * 
	 * @param graph to modified
	 * @param wordList basis for generation, any element in it would be added in the graph and
	 * the edge's weight represent the number of target which is followed by source
	 */
	private void generateGraph(Graph<String> graph, List<String> wordList) {
		if(wordList.isEmpty())
			return;
		if(wordList.size() == 1) {
			graph.add(wordList.get(0));
			return;
		}
		int i = 1;
		String sourceWord = wordList.get(0);
		while(i < wordList.size() && sourceWord.equals(wordList.get(i))) {
			i++;
		}
		if(i == 1) {
			int j = i + 1;
			while(j < wordList.size() && wordList.get(1).equals(wordList.get(j))) {
				j++;
			}
			graph.set(sourceWord, wordList.get(1), j - 1);
			i = j - 1;
		} else {
			graph.set(sourceWord, sourceWord, i - 1);
			i = i - 1;
		}
		for(; i < wordList.size() - 1; i++) {
			String source = wordList.get(i);
			String target = wordList.get(i + 1);
			int j = i + 2;
			while(j < wordList.size() && target.equals(wordList.get(j))) {
				j++;
			}
			graph.set(source, target, j - i - 1);
			i = j - 2;
		}
		checkRep();
	}
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
//        throw new RuntimeException("not implemented");
    	List<String> wordList = this.toLowerCaseFile(corpus);
    	generateGraph(this.graph, wordList);
    	checkRep();
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
//        throw new RuntimeException("not implemented");
    	String[] inputWords = input.split("\\s+");
        StringBuilder poem = new StringBuilder(input);
        
        for (int i = 0; i < inputWords.length - 1; i++) {
        	Integer sumWeight;
            Map<String, Integer> word1Targets = graph.targets(inputWords[i].toLowerCase());
            Map<String, Integer> word2Sources = graph.sources(inputWords[i+1].toLowerCase());
            Set<String> probableBridges1 = new HashSet<>(word1Targets.keySet());
            Set<String> probableBridges2 = new HashSet<>(word2Sources.keySet());
            probableBridges1.retainAll(probableBridges2);
            List<String> allBridges = new ArrayList<>(probableBridges1);
            
            if (!allBridges.isEmpty()) {
            	String bridge = allBridges.get(0);
            	sumWeight = word1Targets.get(bridge) + word2Sources.get(bridge);
            	for(int j = 0; j < allBridges.size(); j++) {
                	String label = allBridges.get(j);
            		if(word1Targets.get(label) + word2Sources.get(label) > sumWeight) {
            			bridge = label;
            		}
            	}
                int insertAt = poem.indexOf(inputWords[i+1], i);
                poem.insert(insertAt, bridge + " ");
            }
        }
        checkRep();
        return poem.toString();
    }
    
    // TODO toString()
    public String toString() {
    	return this.graph.toString();
    }
    
}
